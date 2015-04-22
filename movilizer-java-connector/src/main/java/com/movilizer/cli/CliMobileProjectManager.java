package com.movilizer.cli;

import com.movilizer.projectmanagement.*;
import com.movilizer.push.EventAcknowledgementStatus;
import com.movilizer.util.config.IMovilizerConfig;
import org.apache.commons.cli.CommandLine;

import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class CliMobileProjectManager implements IMobileProjectManager {
    private final IMobileProjectDescription project;
    private final CommandLine commandLine;
    private final IMovilizerConfig config;

    public CliMobileProjectManager(IMovilizerConfig config, IMobileProjectDescription project, CommandLine commandLine) {
        this.config = config;
        this.project = project;
        this.commandLine = commandLine;
    }

    @Override
    public List<IMobileProjectEvent> getMobileProjectEvents(String projectName, int version) throws MobileProjectException {
        if(!commandLine.hasOption(CliOptions.INIT)) {
          return asList();
        }
        MobileProjectEvent event = new MobileProjectEvent();
        event.setProject(project);
        event.setId(1);
        event.setType(MobileProjectEventType.INIT);
        return asList((IMobileProjectEvent)event);
    }



    @Override
    public IMobileProjectSettings getMobileProjectSettings(String name, int version) {
        return new ConfigProjectSettings(project, config);
    }

    @Override
    public Integer getProjectEventId(IMobileProjectDescription project, MobileProjectEventType eventType, EventAcknowledgementStatus acknowledgementStatus) {
        return null;
    }

    @Override
    public void acknowledge(Collection<Integer> eventIds, EventAcknowledgementStatus acknowledgementStatus) throws Exception {

    }
}
