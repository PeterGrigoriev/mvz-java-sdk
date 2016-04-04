package com.movilizer.cli;

import com.movilizer.projectmanagement.IMobileProjectDescription;
import com.movilizer.projectmanagement.MobileProjectDescription;
import com.movilizer.projectmanagement.MovilizerProjectBase;
import com.movilizer.push.IMovilizerPushCall;
import com.movilizer.push.IMovilizerPushCallListener;
import com.movilizer.usermanagement.IMovilizerUser;
import com.movilizer.util.config.IMovilizerConfig;
import com.movilizer.util.config.MovilizerConfig;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.Collection;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class CliManagerTest {
    public static final Options OPTIONS = CliProjectRunner.getOptions();
    public static final String EMAIL = "a@b.com";
    public static final String PROJECT_NAME = "testProject";
    public static final int VERSION = 10;
    public static final MovilizerProjectBase PROJECT = new MovilizerProjectBase(PROJECT_NAME, VERSION) {

        @Override
        public void onInitProject(IMovilizerPushCall call) throws Exception {

        }

        @Override
        public void onShutdownProject(IMovilizerPushCall pushCall) {

        }

        @Override
        public void onUsersAssigned(Collection<IMovilizerUser> joinedUsers, IMovilizerPushCall pushCall) {

        }

        @Override
        public void onUsersUnassigned(Collection<IMovilizerUser> unassignedUsers, IMovilizerPushCall pushCall) {

        }

        @Override
        public IMovilizerPushCallListener onPushCallAvailable(IMovilizerPushCall call) throws Exception {
            return null;
        }
    };
    public static final IMobileProjectDescription PROJECT_DESCRIPTION = new MobileProjectDescription(PROJECT_NAME, VERSION);
    public static final CommandLine COMMAND_LINE_INIT = getCommandLine("-" + CliOptions.INIT);
    public static final CommandLine COMMAND_LINE_ASSIGN = getCommandLine("-assign", EMAIL);
    protected IMovilizerConfig config = MovilizerConfig.getInstance(CliManagerTest.class);

    protected static CommandLine getCommandLine(String... args) {
        try {
            return new BasicParser().parse(CliManagerTest.OPTIONS, args);
        } catch (ParseException e) {
            return null;
        }
    }
}
