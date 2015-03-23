package com.movilizer.connector;

import com.movilitas.movilizer.v12.MovilizerWebServiceV12;
import com.movilitas.movilizer.v12.MovilizerWebServiceV12Service;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MovilizerWebServiceProvider implements IMovilizerWebServiceProvider {
    @Override
    public MovilizerWebServiceV12 get() {
        return new MovilizerWebServiceV12Service().getMovilizerWebServiceV12Soap11();
    }
}
