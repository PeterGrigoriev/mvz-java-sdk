package com.movilizer.connector;

import com.google.inject.ImplementedBy;
import com.google.inject.Provider;
import com.movilitas.movilizer.v15.MovilizerWebServiceV15;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
@ImplementedBy(MovilizerWebServiceProvider.class)
public interface IMovilizerWebServiceProvider extends Provider<MovilizerWebServiceV15> {
}
