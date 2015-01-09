package com.movilizer.push;

import com.google.inject.ImplementedBy;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
@ImplementedBy(MovilizerMasterDataPushCall.class)
public interface IMovilizerMasterDataPushCall extends IMovilizerPushCall {
}
