package com.movilizer.push;

import com.movilizer.connector.IMoveletKeyWithExtension;
import com.movilizer.moveletbuilder.IMoveletPushListener;

import java.util.List;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public interface IMoveletDeleter extends IMoveletPushListener {
    List<IMoveletKeyWithExtension> getMoveletKeysToDelete();
}
