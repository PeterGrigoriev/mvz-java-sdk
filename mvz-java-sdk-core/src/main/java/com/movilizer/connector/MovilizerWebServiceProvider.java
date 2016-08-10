package com.movilizer.connector;

import com.movilitas.movilizer.v15.MovilizerWebServiceV15;
import com.movilitas.movilizer.v15.MovilizerWebServiceV15Service;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MovilizerWebServiceProvider implements IMovilizerWebServiceProvider {
    @Override
    public MovilizerWebServiceV15 get() {
        return new MovilizerWebServiceV15Service().getMovilizerWebServiceV15Soap11();
    }
}
