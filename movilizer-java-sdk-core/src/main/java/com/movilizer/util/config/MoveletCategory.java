package com.movilizer.util.config;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MoveletCategory implements IMoveletCategory {
    private final String name;
    private final int icon;

    public MoveletCategory(String name, int icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public int getIcon() {
        return icon;
    }
}
