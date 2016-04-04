package com.movilizer.connector;

import com.movilitas.movilizer.v14.MovilizerWebServiceV14;
import com.movilitas.movilizer.v14.MovilizerWebServiceV14Service;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MovilizerWebServiceProvider implements IMovilizerWebServiceProvider {
    @Override
    public MovilizerWebServiceV14 get() {
        return new MovilizerWebServiceV14Service().getMovilizerWebServiceV14Soap11();
    }
}
