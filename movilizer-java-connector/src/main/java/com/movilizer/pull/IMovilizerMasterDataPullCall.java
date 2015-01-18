package com.movilizer.pull;

import com.google.inject.ImplementedBy;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
@ImplementedBy(MovilizerMasterDataPullCall.class)
public interface IMovilizerMasterDataPullCall extends IMovilizerPullCall{
}
