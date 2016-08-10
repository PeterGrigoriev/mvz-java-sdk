package com.movilizer.pull;

import com.movilitas.movilizer.v15.MovilizerResponse;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public interface IMovilizerResponseObserver {
    void onResponseAvailable(MovilizerResponse response) throws KeepItOnTheCloudException;
}
