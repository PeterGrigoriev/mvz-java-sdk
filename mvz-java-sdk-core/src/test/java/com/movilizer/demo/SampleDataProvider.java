package com.movilizer.demo;

import com.movilitas.movilizer.v15.MovilizerMovelet;
import com.movilizer.SimpleMoveletDataProviderTest;
import com.movilizer.moveletbuilder.IMoveletDataProvider;
import com.movilizer.util.movelet.SimpleMoveletDataProvider;

import java.util.Collection;

/**
 * @author Peter.Grigoriev@gmail.com
 */
public class SampleDataProvider extends SimpleMoveletDataProvider {

    private final String userName;

    public SampleDataProvider(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
