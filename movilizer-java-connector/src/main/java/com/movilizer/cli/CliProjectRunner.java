package com.movilizer.cli;

import com.movilizer.connector.MovilizerRequestSender;
import com.movilizer.connector.MovilizerWebServiceProvider;
import com.movilizer.projectmanagement.*;
import com.movilizer.util.config.IMovilizerConfig;
import com.movilizer.util.config.MovilizerConfig;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class CliProjectRunner {
    private final MovilizerProjectBase project;
    private final IMovilizerConfig config;


    public CliProjectRunner(MovilizerProjectBase project) {
        this.project = project;
        config = MovilizerConfig.getInstance(project.getClass());
    }

    public void run(String... arguments) throws ParseException, MobileProjectException {
        CommandLine commandLine = new BasicParser().parse(getOptions(), arguments);

        IMobileProjectRunner projectRunner = new MobileProjectRunner(
                new CliMobileProjectManager(config, project, commandLine),
                new SingleMobileProjectLoader(project),
                new CliMobileAssignmentManager(commandLine, project),
                new MovilizerRequestSender(new MovilizerWebServiceProvider())
        );

        if(commandLine.hasOption(CliOptions.PULL)) {
            projectRunner.runPull();
        }
        if(commandLine.hasOption(CliOptions.INIT) || commandLine.hasOption(CliOptions.ASSIGN) || commandLine.hasOption(CliOptions.PUSH))
        projectRunner.runPush();
    }

    private static Options getOptions() {
        Options options = new Options();
        options.addOption(CliOptions.INIT, false, "Initialize the app");
        options.addOption(CliOptions.ASSIGN, true, "Send the app to a user");
        options.addOption(CliOptions.PULL, false, "Pull data from the cloud");
        options.addOption(CliOptions.PUSH, false, "Push data to the cloud");
        return options;
    }
}
