package com.movilizer.usermanagement;

import com.google.common.base.Predicate;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.movilizer.util.config.IMovilizerPushSettings;
import com.movilizer.util.config.XmlFileConfiguration;
import com.movilizer.util.logger.ComponentLogger;
import com.movilizer.util.logger.ILogger;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.ConfigurationNode;

import java.util.*;

import static com.movilizer.usermanagement.MovilizerUser.*;
import static com.movilizer.usermanagement.MovilizerUserInvitationMethod.toInvitationMethod;
import static com.movilizer.usermanagement.MovilizerUserStatus.NEW;
import static com.movilizer.usermanagement.MovilizerUserStatus.toUserStatus;
import static com.movilizer.usermanagement.MovilizerUserUtils.userToLongString;
import static com.movilizer.usermanagement.XmlConfigurationNode.newNode;
import static com.movilizer.util.collection.CollectionUtils.select;
import static com.movilizer.util.xml.XMLUtils.wrapXml;
import static java.text.MessageFormat.format;
import static java.util.Collections.sort;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
@Singleton
public class MovilizerUserManager extends XmlFileConfiguration implements IMovilizerUserManager {
    private List<IMovilizerUser> users;
    private Map<Integer, IMovilizerUser> employeeNumberToUserMap;
    private Map<String, IMovilizerUser> deviceAddressToUserMap;
    private IMovilizerPushSettings pushSettings;


    @Inject
    public MovilizerUserManager(IMovilizerPushSettings pushSettings, @Named("ClassNearResources") Class classWithResources) {
        super(classWithResources);
        this.pushSettings = pushSettings;
        reload();
    }

    @Override
    public void reload() {
        super.reload();
        employeeNumberToUserMap = new HashMap<Integer, IMovilizerUser>();
        deviceAddressToUserMap = new HashMap<String, IMovilizerUser>();

        //noinspection unchecked
        List<HierarchicalConfiguration> xmlConfigurations = configuration.configurationsAt("users.user");
        users = new ArrayList<IMovilizerUser>();

        logger.info(format("Loading users"));
        for (Configuration userConfiguration : xmlConfigurations) {
            addUser(userConfiguration);
        }

        if (pushSettings.sendConfigurationMovelets()) {
            try {
                setUserStatusAndSave(users, NEW);
            } catch (CannotUpdateUserException e) {
                logger.error(e);
            }
        }
        logger.info(format("Loaded {0} users", users.size()));
    }

    @Override
    public void save() {
        try {
            super.save();
        } catch (Throwable throwable) {
            logger.error(throwable);
        }
    }

    @Override
    public void setUserStatusAndSave(Collection<IMovilizerUser> users, MovilizerUserStatus status) throws CannotUpdateUserException {
        for (IMovilizerUser user : users) {
            user.setStatus(status);
        }
        try {
            save();
        } catch (Throwable throwable) {
            throw new CannotUpdateUserException(throwable);
        }
    }

    @Override
    public boolean removeUser(IMovilizerUser newUser) {
        boolean result = users.remove(newUser);

        employeeNumberToUserMap.remove(newUser.getEmployeeNumber());
        deviceAddressToUserMap.remove(newUser.getDeviceAddress());

        return result;
    }

    @Override
    protected HierarchicalConfiguration toConfiguration() {
        XMLConfiguration xmlConfiguration = new XMLConfiguration();
        xmlConfiguration.setRootElementName("user-management");
        Collection<ConfigurationNode> nodes = toConfigurationNodes();
        xmlConfiguration.addNodes("users", nodes);
        return xmlConfiguration;
    }

    private Collection<ConfigurationNode> toConfigurationNodes() {
        List<ConfigurationNode> nodes = new ArrayList<ConfigurationNode>();
        for (IMovilizerUser user : users) {
            nodes.add(userToConfigurationNode(user));
        }
        return nodes;
    }

    public static ConfigurationNode userToConfigurationNode(IMovilizerUser user) {

        XmlConfigurationNode xmlConfigurationNode = newNode(MovilizerUser.USER);
        for (String name : user.getFieldNames()) {
            xmlConfigurationNode = xmlConfigurationNode.addProperty(name, user.get(name));

        }

        return xmlConfigurationNode;
    }

    private static ILogger logger = ComponentLogger.getInstance("UserManager");


    private void addUser(Configuration userConfiguration) {
        String name = userConfiguration.getString(NAME);
        String eMail = userConfiguration.getString(EMAIL);
        String phone = userConfiguration.getString(PHONE);
        int employeeNumber = userConfiguration.getInt(EMPLOYEE_NUMBER);
        String strInvitationMethod = userConfiguration.getString(INVITATION_METHOD);

        String strUserStatus = userConfiguration.getString(USER_STATUS);


        Map<String, String> additionalFields = readAdditionalFields(userConfiguration);

        try {
            IMovilizerUser user = createUser(eMail, phone, name, employeeNumber,
                    toInvitationMethod(strInvitationMethod), toUserStatus(strUserStatus), additionalFields);

            addUser(user);
            logger.info(format("User: {0}", userToLongString(user)));
        } catch (IllegalMovilizerUserException e) {
            logger.error(e);
        }
    }

    private Map<String, String> readAdditionalFields(Configuration userConfiguration) {

        Map<String, String> additionalFields = new HashMap<String, String>();
        Iterator keys = userConfiguration.getKeys();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            additionalFields.put(key, userConfiguration.getString(key));

        }
        return additionalFields;

    }

    @Override
    public void addUser(IMovilizerUser user) throws IllegalMovilizerUserException {
        checkCanAddUser(user);
        users.add(user);
        employeeNumberToUserMap.put(user.getEmployeeNumber(), user);
        deviceAddressToUserMap.put(user.getDeviceAddress(), user);
    }


    private void checkCanAddUser(IMovilizerUser user) throws IllegalMovilizerUserException {
        if (employeeNumberToUserMap.containsKey(user.getEmployeeNumber())) {
            throw new IllegalMovilizerUserException(user, format("user with same employee number ''{0}'' exists already", user.getEmployeeNumber()));
        }
        if (deviceAddressToUserMap.containsKey(user.getDeviceAddress()))
            throw new IllegalMovilizerUserException(user, format("user with same device address ''{0}'' exists already", user.getDeviceAddress()));

        new MovilizerUserChecker(user).run();
    }

    @Override
    protected String getConfigurationName() {
        return "users";
    }

    @Override
    public List<IMovilizerUser> getUsers() {
        return users;
    }


    @Override
    public IMovilizerUser getUser(int employeeNumber) {
        return employeeNumberToUserMap.get(employeeNumber);
    }

    @Override
    public List<IMovilizerUser> getNewUsers() {
        return getUsers(IS_USER_NEW);
    }

    @Override
    public List<IMovilizerUser> getUsers(Predicate<IMovilizerUser> predicate) {
        return select(users, predicate);
    }


    public static String toXmlString(IMovilizerUser user) {
        List<String> fieldNames = new ArrayList<String>(user.getFieldNames());
        sort(fieldNames);
        StringBuilder stringBuilder = new StringBuilder();
        for (String fieldName : fieldNames) {
            stringBuilder.append(wrapXml(fieldName, user.get(fieldName)));
        }

        return wrapXml(MovilizerUser.USER, stringBuilder.toString());
    }
}
