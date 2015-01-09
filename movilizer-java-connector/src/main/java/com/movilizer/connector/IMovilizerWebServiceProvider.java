package com.movilizer.connector;

import com.google.inject.ImplementedBy;
import com.google.inject.Provider;
import com.movilitas.movilizer.v11.MovilizerWebServiceV11;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
@ImplementedBy(MovilizerWebServiceProvider.class)
public interface IMovilizerWebServiceProvider extends Provider<MovilizerWebServiceV11> {
}
