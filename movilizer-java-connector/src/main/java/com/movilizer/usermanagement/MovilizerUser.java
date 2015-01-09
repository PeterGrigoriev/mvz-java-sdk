package com.movilizer.usermanagement;

import com.google.common.base.Predicate;
import com.movilitas.movilizer.v11.MovilizerParticipant;
import com.movilizer.util.string.StringUtils;
import com.movilizer.util.logger.ComponentLogger;
import com.movilizer.util.logger.ILogger;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.movilizer.usermanagement.MovilizerUserStatus.EXISTING;
import static com.movilizer.usermanagement.MovilizerUserStatus.NEW;
import static com.movilizer.usermanagement.MovilizerUserStatus.toUserStatus;
import static com.movilizer.util.string.StringUtils.isNullOrEmpty;
import static java.text.MessageFormat.format;

/**
 * @author Peter.Grigoriev@movilizer.com
 * @author philippe.guillamet@altran.com
 */
public class MovilizerUser implements IMovilizerUser {
    public static final String USER = "user";
    public static final String NAME = "name";
    public static final String EMPLOYEE_NUMBER = "employee-number";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";
    public static final String INVITATION_METHOD = "invitation-method";
    public static final String USER_STATUS = "status";
    public static final Predicate<IMovilizerUser> IS_USER_NEW = new IsUserStatus(NEW);
    private final int employeeNumber;
    private String name;
    private MovilizerUserInvitationMethod invitationMethod;
    private String email;
    private String phone;
    private MovilizerUserStatus status;
    private Map<String, String> fields;

    public MovilizerUser(int employeeNumber, String name, MovilizerUserInvitationMethod invitationMethod, String email, String phone, MovilizerUserStatus status, Map<String, String> additionalFields) {
        this.fields = new HashMap<String, String>(additionalFields);

        this.email = email;
        fields.put(EMAIL, email);

        this.phone = phone;
        fields.put(PHONE, phone);

        this.employeeNumber = employeeNumber;
        fields.put(EMPLOYEE_NUMBER, String.valueOf(employeeNumber));

        this.name = name;
        fields.put(NAME, name);

        this.invitationMethod = invitationMethod;
        fields.put(INVITATION_METHOD, invitationMethod.name());

        this.status = status;
        fields.put(USER_STATUS, status.name());
    }

    public static IMovilizerUser createUser(String email, String phone, String name, int employeeNumber, MovilizerUserInvitationMethod invitationMethod, MovilizerUserStatus status, Map<String, String> fields) throws IllegalMovilizerUserException {
        if (fields == null) {
            fields = new HashMap<String, String>();
        }
        MovilizerUser user = new MovilizerUser(employeeNumber, name, invitationMethod, email, phone, status, fields);
        checkUser(user);
        return user;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public int getEmployeeNumber() {
        return employeeNumber;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public MovilizerUserInvitationMethod getInvitationMethod() {
        return invitationMethod;
    }

    @Override
    public String getDeviceAddress() {
        return invitationMethod == MovilizerUserInvitationMethod.EMAIL ?
                StringUtils.emailToDeviceAddress(getEmail()) : StringUtils.phoneToDeviceAddress(getPhone());
    }

    @Override
    public MovilizerUserStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(MovilizerUserStatus status) {
        this.status = status;
        fields.put(USER_STATUS, status.name());
    }

    public void set(String fieldName, String value) {
        this.fields.put(fieldName, value);
    }

    @Override
    public String get(String fieldName) {
        return fields == null ? null : fields.get(fieldName);
    }

    @Override
    public Set<String> getFieldNames() {
        return fields.keySet();
    }


    public static MovilizerParticipant toParticipant(IMovilizerUser user) {
        MovilizerParticipant participant = new MovilizerParticipant();
        participant.setDeviceAddress(user.getDeviceAddress());
        participant.setParticipantKey(String.valueOf(user.getEmployeeNumber()));
        participant.setName(user.getName());
        return participant;
    }

    public static void checkUser(IMovilizerUser user) throws IllegalMovilizerUserException {
        new MovilizerUserChecker(user).run();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovilizerUser user = (MovilizerUser) o;

        return fields.equals(user.fields);

    }

    @Override
    public int hashCode() {
        return fields.hashCode();
    }

    public static String deviceAddressToPhoneNumber(String deviceAddress) {
        return deviceAddressToInvitationMethod(deviceAddress) == MovilizerUserInvitationMethod.SMS ? deviceAddress : null;
    }

    public static String deviceAddressToEmail(String deviceAddress) {
        return deviceAddressToInvitationMethod(deviceAddress) == MovilizerUserInvitationMethod.EMAIL ? deviceAddress : null;
    }

    public static MovilizerUserInvitationMethod deviceAddressToInvitationMethod(String deviceAddress) {
        if (isNullOrEmpty(deviceAddress)) {
            return MovilizerUserInvitationMethod.SMS;
        }
        return deviceAddress.contains("@") ? MovilizerUserInvitationMethod.EMAIL : MovilizerUserInvitationMethod.SMS;
    }

    private static final ILogger logger = ComponentLogger.getInstance("MovilizerUser");
    public static IMovilizerUser createUserFromDeviceAddress(String deviceAddress) {
        try {
            return createUser(deviceAddressToEmail(deviceAddress), deviceAddressToPhoneNumber(deviceAddress), deviceAddress, 0, deviceAddressToInvitationMethod(deviceAddress), EXISTING, new HashMap<String, String>());
        } catch (IllegalMovilizerUserException e) {
            logger.error(e);
            return null;
        }
    }

    public static MovilizerUserStatus convertToUserStatus(String userStatus) throws CannotConvertToUserStatusException {
        if (isNullOrEmpty(userStatus)) throw new CannotConvertToUserStatusException("User status is null or empty");
        final MovilizerUserStatus movilizerUserStatus = toUserStatus(userStatus);
        if (movilizerUserStatus == null)
            throw new CannotConvertToUserStatusException(format("User status is: {0}", userStatus));
        return movilizerUserStatus;
    }
}
