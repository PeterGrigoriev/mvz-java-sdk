package com.movilizer.projectmanagement;

import com.movilizer.connector.IMoveletKeyWithExtension;
import com.movilizer.masterdata.EmptyMasterDataSource;
import com.movilizer.masterdata.IMasterdataSource;
import com.movilizer.pull.IMovilizerResponseObserver;
import com.movilizer.pull.IReplyMoveletProcessor;
import com.movilizer.util.template.ITemplateRepository;
import com.movilizer.util.template.ResourceXmlTemplateRepository;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public abstract class MovilizerProjectBase implements IMovilizerProject {
    private final String name;
    private final int version;

    private final Set<IReplyMoveletProcessor> replyMoveletProcessors;
    private final Set<IMovilizerResponseObserver> responseObservers;
    private final IMasterdataSource masterdataSource;

    public MovilizerProjectBase(String name, int version) {
        this.name = name;
        this.version = version;
        this.replyMoveletProcessors = new HashSet<IReplyMoveletProcessor>();
        this.responseObservers = createDefaultResponseObservers();
        this.masterdataSource = new EmptyMasterDataSource();
    }

    private HashSet<IMovilizerResponseObserver> createDefaultResponseObservers() {
        HashSet<IMovilizerResponseObserver> observers = new HashSet<IMovilizerResponseObserver>();

        return observers;
    }

    public String getName() {
        return name;
    }

    public int getVersion() {
        return version;
    }

    @Override
    public Set<IReplyMoveletProcessor> getReplyProcessors() {
        return replyMoveletProcessors;
    }

    @Override
    public IMasterdataSource getMasterdataSource() {
        return masterdataSource;
    }

    @Override
    public Set<IMovilizerResponseObserver> getMovilizerResponseObservers() {
        return responseObservers;
    }


    private ITemplateRepository templateRepository = null;

    @Override
    public ITemplateRepository getTemplateRepository() {
        if(templateRepository == null) {
            templateRepository = new ResourceXmlTemplateRepository(getName(), getClass());
        }
        return templateRepository;
    }

    protected void addResponseObserver(IMovilizerResponseObserver responseObserver) {
        responseObservers.add(responseObserver);
    }

    protected void addReplyProcessor(IReplyMoveletProcessor replyMoveletProcessor) {
        replyMoveletProcessors.add(replyMoveletProcessor);
    }

    private IMobileProjectSettings description;

    public void setProjectSettings(IMobileProjectSettings description) {
        this.description = description;
    }

    @Override
    public IMobileProjectSettings getSettings() throws ProjectSettingsNotAvailableException {
        if(description == null) {
            throw new ProjectSettingsNotAvailableException(getName(), getVersion());
        }
        return description;
    }

    @Override
    public IMoveletKeyWithExtension getConfigurationMoveletKey() {
        return null;
    }

}


