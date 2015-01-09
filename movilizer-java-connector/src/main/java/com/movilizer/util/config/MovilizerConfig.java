package com.movilizer.util.config;

import com.movilizer.connector.IMovilizerCloudSystem;
import com.movilizer.connector.IProxyInfo;
import com.movilizer.connector.MovilizerCloudSystem;
import com.movilizer.masterdata.IMasterdataXmlSetting;
import com.movilizer.masterdata.MasterdataXmlSettings;
import com.movilizer.util.proxy.ProxyInfo;
import org.apache.commons.configuration.HierarchicalConfiguration;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

import static com.movilizer.util.collection.CollectionUtils.newLinkedSet;


/**
 * @author philippe.guillamet@gdfsuez.com
 */
public class MovilizerConfig extends XmlFileReadOnlyConfiguration implements IMovilizerConfig {
    public static final String DEFAULT_CONFIGURATION = "configuration";

    public static final Set<String> ADDITIONAL_CONFIGURATIONS = newLinkedSet("database", "cloud", "masterdata", "movelet", "proxy", "push", "pull", "keystore");

    // TODO: get rid of static instances
    private static MovilizerConfig instance;

    private final IMovilizerCloudSystem movilizerSystem;
    private final IMovilizerCloudSystem masterDataSystem;
    private IProxyInfo proxyInfo;
    private final IJdbcSettings jdbcSettings;
    private final IMoveletSettings moveletSettings;
    private final IMovilizerPullSettings pullSettings;
    private final IMovilizerPushSettings pushSettings;
    private final List<IMasterdataXmlSetting> masterdataXmlSettings;
    private final IKeyStoreInfo keyStoreInfo;


    @Deprecated
    public static IMovilizerConfig getInstance() {
        return accessInstance(MovilizerConfig.class);
    }

    public static IMovilizerConfig getInstance(Class classWithResources) {
        return accessInstance(classWithResources);
    }

    private static MovilizerConfig accessInstance(Class classWithResources) {
        return instance == null ? instance = new MovilizerConfig(classWithResources) : instance;
    }


    private MovilizerConfig(Class classWithResources) {
        super(classWithResources);
        movilizerSystem = readMovilizerCloudSystem();
        masterDataSystem = readMasterDataSystem();
        proxyInfo = loadProxyInfo();
        keyStoreInfo = loadKeyStoreInfo();
        jdbcSettings = new JdbcSettings(getString("db-connections.jdbc.url"), getString("db-connections.jdbc.user"), getString("db-connections.jdbc.password"));
        moveletSettings = new MoveletSettings(getString("movilizer.movelet.namespace"),
                getString("movilizer.movelet.environment"),
                getRootCategory(),
                getBoolean("movilizer.movelet.debug", false),
                getInt("movilizer.movelet.masterdata-system-id", -1),
                getInt("movilizer.movelet.version", 0),
                getString("movilizer.movelet.key", ""));
        pullSettings = new MovilizerPullSettings(getInt("movilizer.pull.number-of-replies", 1));
        pushSettings = new MovilizerPushSettings(isNeedToSendConfigurationMovelets(),
                getBoolean("movilizer.push.resend-all-movelets", false),
                getInt("movilizer.push.new-users-per-run", MovilizerPushSettings.DEFAULT_NUMBER_OF_NEW_USERS_PER_RUN));
        masterdataXmlSettings = readMasterdataXmlSettings();
    }

    private IKeyStoreInfo loadKeyStoreInfo() {
        if(!hasKey("key-store.location")) {
            return null;
        }
        return new KeyStoreInfo(getString("key-store.location"), getString("key-store.password"));
    }

    private MovilizerCloudSystem readMovilizerCloudSystem() {
        if(!hasKey("movilizer.system.id")) {
            return null;
        }
        return new MovilizerCloudSystem(getInt("movilizer.system.id"), getString("movilizer.system.password"), getString("movilizer.system.endpoint"), getInt("movilizer.system.timeout", 3600) * 1000);
    }

    private IMovilizerCloudSystem readMasterDataSystem() {
        if(!hasKey("movilizer.master-data-system.id")) {
            return null;
        }
        return new MovilizerCloudSystem(getInt("movilizer.master-data-system.id"),
                                        getString("movilizer.master-data-system.password"),
                                        getString("movilizer.master-data-system.endpoint"),
                                        getInt("movilizer.master-data-system.timeout", 3600) * 1000);
    }

    private List<IMasterdataXmlSetting> readMasterdataXmlSettings() {
        List<IMasterdataXmlSetting> settings = new ArrayList<IMasterdataXmlSetting>();

        List list = configuration.configurationsAt("movilizer.masterdata.xml");

        for (Object subConfiguration : list) {
            IMasterdataXmlSetting setting = MasterdataXmlSettings.readMasterdataXmlSetting((HierarchicalConfiguration) subConfiguration);
            settings.add(setting);
        }

        return settings;
    }


    private boolean isNeedToSendConfigurationMovelets() {
        boolean flagInConfig = getBoolean("movilizer.push.send-configuration-movelets", false);
        boolean fileExists = getConfigMoveletSentFile().exists();
        return flagInConfig && !fileExists;
    }

    private static File getConfigMoveletSentFile() {
        return new File(getBaseDirectory(), "ConfigMoveletsSent.txt");
    }

    public static File getBaseDirectory() {
        URL codeSourceLocation = ConfigurationProvider.class.getProtectionDomain().getCodeSource().getLocation();
        try {
            return new File(codeSourceLocation.toURI()).getParentFile();
        } catch (URISyntaxException e) {
            return new File(".");
        }
    }


    @SuppressWarnings("UnusedDeclaration")
    public static void markConfigurationMoveletsAsSent() {
        File configMoveletSentFile = getConfigMoveletSentFile();
        if (!configMoveletSentFile.exists()) {
            try {
                //noinspection ResultOfMethodCallIgnored
                configMoveletSentFile.createNewFile();
            } catch (IOException ignored) {
            }
        }
    }

    private IProxyInfo loadProxyInfo() {
        try {
            return new ProxyInfo(getString("proxy.host"), getInt("proxy.port"));
        } catch (NoSuchElementException e) {
            return null;
        }
    }


    @Override
    protected String getConfigurationName() {
        return DEFAULT_CONFIGURATION;
    }

    @Override
    protected Set<String> getAdditionalConfigurationNames() {
        return ADDITIONAL_CONFIGURATIONS;
    }


    @Override
    public IMovilizerPushSettings getPushSettings() {
        return pushSettings;
    }

    private MoveletCategory getRootCategory() {
        return new MoveletCategory(getString("movilizer.movelet.root-category.name"), getInt("movilizer.movelet.root-category.icon", 0));
    }


    @Override
    public IJdbcSettings getJdbcSettings() {
        return jdbcSettings;
    }

    @Override
    public IMoveletSettings getMoveletSettings() {
        return moveletSettings;
    }

    @Override
    public IMovilizerPullSettings getPullSettings() {
        return pullSettings;
    }

    @Override
    public IMovilizerCloudSystem getMovilizerSystem() {
        return movilizerSystem;
    }

    @Override
    public IMovilizerCloudSystem getMasterDataSystem() {
        return masterDataSystem;
    }

    @Override
    public IProxyInfo getProxyInfo() {
        return proxyInfo;
    }

    @Override
    public IKeyStoreInfo getKeyStoreInfo() {
        return keyStoreInfo;
    }


    @Override
    public Collection<IMasterdataXmlSetting> getMasterdataXmlSettings() {
        return masterdataXmlSettings;
    }


    public void setProxyInfo(IProxyInfo proxyInfo) {
        this.proxyInfo = proxyInfo;
    }
}
