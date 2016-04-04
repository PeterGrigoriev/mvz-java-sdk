package com.movilizer.projectmanagement;

import com.google.inject.ImplementedBy;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
@ImplementedBy(MobileProjectRunner.class)
public interface IMobileProjectRunner {
    void runPull();

    void runPush();
}
