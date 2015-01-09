package com.movilizer.module;

import com.google.inject.AbstractModule;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.util.Providers;
import com.movilizer.connector.IMovilizerCloudSystem;
import com.movilizer.connector.IProxyInfo;
import com.movilizer.masterdata.IMasterdataAcknowledgementProcessor;
import com.movilizer.masterdata.IMasterdataAcknowledger;
import com.movilizer.masterdata.IMasterdataSource;
import com.movilizer.masterdata.IMasterdataXmlSetting;
import com.movilizer.pull.IMovilizerResponseObserver;
import com.movilizer.pull.IReplyMoveletProcessor;
import com.movilizer.util.template.ITemplateRepository;
import com.movilizer.util.config.IJdbcSettings;
import com.movilizer.util.config.IMovilizerConfig;
import com.movilizer.util.config.IMovilizerPushSettings;
import com.movilizer.util.config.MovilizerConfig;
import com.movilizer.util.logger.ComponentLogger;
import com.movilizer.util.logger.ILogger;

import java.util.Collection;

import static com.google.inject.multibindings.Multibinder.newSetBinder;
import static com.google.inject.name.Names.named;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public abstract class MovilizerModule extends AbstractModule {
    private Multibinder<IReplyMoveletProcessor> replyProcessors;
    private Multibinder<IMasterdataAcknowledgementProcessor> masterdataAcknowledgementProcessors;
    private Multibinder<IMovilizerResponseObserver> responseObservers;
    private Multibinder<IMasterdataXmlSetting> masterdataXmlSettings;


    private static ILogger logger = ComponentLogger.getInstance("MovilizerModule");

    @Override
    protected final void configure() {
        IMovilizerConfig config = MovilizerConfig.getInstance(getClass());
        bind(Class.class).annotatedWith(named("ClassNearResources")).toInstance(getClass());
        bind(IMovilizerConfig.class).toInstance(config);
        bind(IJdbcSettings.class).toInstance(config.getJdbcSettings());
        IMovilizerCloudSystem movilizerSystem = config.getMovilizerSystem();
        if(null == movilizerSystem) {
            logger.info("Movilizer cloud system is not configured in provided MovilizerConfig. This may however be configured in your backend.");
        } else {
            bind(IMovilizerCloudSystem.class).toInstance(movilizerSystem);
        }
        bindMasterDataCloudSystem(config.getMasterDataSystem());

        IProxyInfo proxyInfo = config.getProxyInfo();
        if(proxyInfo == null) {
            logger.info("Proxy is not configured. The cloud will be called directly.");

            bind(IProxyInfo.class).toProvider(Providers.<IProxyInfo>of(null));
        } else  {
            bind(IProxyInfo.class).toInstance(proxyInfo);
        }
        bind(IMovilizerPushSettings.class).toInstance(config.getPushSettings());

        replyProcessors = newSetBinder(binder(), IReplyMoveletProcessor.class);
        masterdataAcknowledgementProcessors = newSetBinder(binder(), IMasterdataAcknowledgementProcessor.class);
        responseObservers = newSetBinder(binder(), IMovilizerResponseObserver.class);

        masterdataXmlSettings = newSetBinder(binder(), IMasterdataXmlSetting.class);

        addMasterdataXmlSettingsFromConfig(config);

        setUp();
    }

    private void addMasterdataXmlSettingsFromConfig(IMovilizerConfig config) {
        Collection<IMasterdataXmlSetting> settings = config.getMasterdataXmlSettings();
        if(settings == null) {
            return;
        }
        for (IMasterdataXmlSetting setting : settings) {
            addMasterdataXmlSetting().toInstance(setting);
        }
    }

    private void bindMasterDataCloudSystem(IMovilizerCloudSystem masterDataSystem) {
        LinkedBindingBuilder<IMovilizerCloudSystem> binder = bind(IMovilizerCloudSystem.class).annotatedWith(named("MasterData"));

        if(masterDataSystem != null) {
            binder.toInstance(masterDataSystem);
        } else  {
            binder.toProvider(Providers.<IMovilizerCloudSystem>of(null));
        }
    }

    protected abstract void setUp();


    protected final LinkedBindingBuilder<IReplyMoveletProcessor> addReplyProcessor() {
        return replyProcessors.addBinding();
    }

    protected void addReplyProcessor(Class<? extends IReplyMoveletProcessor> aClass) {
        addReplyProcessor().to(aClass);
    }

    protected void addReplyProcessor(IReplyMoveletProcessor replyMoveletProcessor) {
        addReplyProcessor().toInstance(replyMoveletProcessor);
    }

    protected void bindTemplateRepository(Class<? extends ITemplateRepository> repository) {
        bindMoveletTemplateRepository(repository);
        bindRequestTemplateRepository(repository);
    }

    protected void bindMoveletTemplateRepository(Class<? extends ITemplateRepository> repository) {
        bind(ITemplateRepository.class).to(repository);
    }

    protected void bindRequestTemplateRepository(Class<? extends ITemplateRepository> repository) {
        bind(ITemplateRepository.class).annotatedWith(named("Requests")).to(repository);
    }

    private LinkedBindingBuilder<IMasterdataAcknowledgementProcessor> addMasterdataAcknowledgementProcessor() {
        return masterdataAcknowledgementProcessors.addBinding();
    }

    private LinkedBindingBuilder<IMovilizerResponseObserver> addResponseObserver() {
        return responseObservers.addBinding();
    }

    private LinkedBindingBuilder<IMasterdataXmlSetting> addMasterdataXmlSetting() {
        return masterdataXmlSettings.addBinding();
    }

    protected void addMasterdataSource(Class<? extends IMasterdataSource> aClass) {
        bind(IMasterdataSource.class).to(aClass);
        bind(IMasterdataAcknowledger.class).to(aClass);
    }


    protected final void addResponseObserver(Class<? extends IMovilizerResponseObserver> aClass) {
        addResponseObserver().to(aClass);
    }

    protected final void addResponseObserver(IMovilizerResponseObserver responseObserver) {
        addResponseObserver().toInstance(responseObserver);
    }

    protected final void addMasterdataAcknowledgementProcessor(IMasterdataAcknowledgementProcessor processor) {
        addMasterdataAcknowledgementProcessor().toInstance(processor);
    }

}
