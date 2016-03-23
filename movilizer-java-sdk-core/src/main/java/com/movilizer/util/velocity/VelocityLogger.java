package com.movilizer.util.velocity;

import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.log.LogChute;

import java.util.Arrays;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class VelocityLogger implements LogChute {

    public VelocityLogger() {
    }

    public void init(RuntimeServices runtimeServices) throws Exception {
    }

    public void log(int i, String s) {
        if (i < WARN_ID) {
            return;
        }

        if (i == WARN_ID) {
            warning(s);
        } else {
            error(s);
        }
    }

    private void error(String s) {
        System.err.println(s);
    }

    private void warning(String s) {
        System.err.println(s);
    }

    private void logException(Throwable throwable) {
        System.err.println(throwable.getMessage());
        System.err.println(Arrays.toString(throwable.getStackTrace()));
    }

    public void log(int i, String s, Throwable throwable) {
        log(i, s);
        logException(throwable);
    }

    public boolean isLevelEnabled(int i) {
        return true;
    }
}