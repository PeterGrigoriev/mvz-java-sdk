package com.movilizer.projectmanagement;

import com.movilitas.movilizer.v15.MovilizerResponse;
import com.movilizer.pull.IMovilizerResponseObserver;
import com.movilizer.pull.KeepItOnTheCloudException;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MockMovilizerResponseObserver implements IMovilizerResponseObserver {
    private boolean wasCalled = false;

    @Override
    public void onResponseAvailable(MovilizerResponse response) throws KeepItOnTheCloudException {
        wasCalled = true;
    }

    public boolean wasCalled() {
        return wasCalled;
    }
}
