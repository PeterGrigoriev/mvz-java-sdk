package com.movilizer.connector.mock;

import java.util.Enumeration;
import java.util.Properties;

/**
 * @author Peter.Grigoriev@movilizer.com.
 */
public class GetMaxSize {
    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();

        System.out.println("Max memory:" + runtime.maxMemory());

        Properties properties = System.getProperties();
        Enumeration names = properties.propertyNames();

        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            System.out.println(name + " -> " + properties.getProperty(name));
        }

    }
}
