package com.movilizer.connector;

import com.movilitas.movilizer.v11.MovilizerWebServiceV11;
import com.movilitas.movilizer.v11.MovilizerWebServiceV11Service;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MovilizerWebServiceProvider implements IMovilizerWebServiceProvider {
    @Override
    public MovilizerWebServiceV11 get() {
        return new MovilizerWebServiceV11Service().getMovilizerWebServiceV11Soap11();
    }
}
