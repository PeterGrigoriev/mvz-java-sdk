package com.movilizer.projectmanagement;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MobileProjectDescription implements IMobileProjectDescription {
    protected String name;
    protected int version;


    public MobileProjectDescription() {
    }

    public MobileProjectDescription(String name, int version) {
        this.name = name;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public int getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IMobileProjectDescription)) return false;

        IMobileProjectDescription that = (IMobileProjectDescription) o;

        return version == that.getVersion() && name.equals(that.getName());

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + version;
        return result;
    }

    @Override
    public String toString() {
        return "MobileProjectDescription{" +
                "name='" + name + '\'' +
                ", version=" + version +
                '}';
    }
}
