package com.movilizer.connector;

import com.google.inject.ImplementedBy;
import com.google.inject.Provider;
import com.movilitas.movilizer.v12.MovilizerWebServiceV12;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
@ImplementedBy(MovilizerWebServiceProvider.class)
public interface IMovilizerWebServiceProvider extends Provider<MovilizerWebServiceV12> {
}
