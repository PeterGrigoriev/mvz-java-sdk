package com.movilizer.projectmanagement;

import com.movilizer.connector.IMovilizerCloudSystem;



/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MobileProjectSettings extends MobileProjectDescription implements IMobileProjectSettings {
    protected int id;
    private IMovilizerCloudSystem moveletCloudSystem;
    private IMovilizerCloudSystem masterDataCloudSystem;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setMoveletCloudSystem(IMovilizerCloudSystem moveletCloudSystem) {
        this.moveletCloudSystem = moveletCloudSystem;
    }

    @Override
    public IMovilizerCloudSystem getMoveletCloudSystem() {
        return moveletCloudSystem;
    }

    public void setMasterDataCloudSystem(IMovilizerCloudSystem masterDataCloudSystem) {
        this.masterDataCloudSystem = masterDataCloudSystem;
    }

    @SuppressWarnings("RedundantIfStatement")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MobileProjectDescription)) return false;

        MobileProjectDescription that = (MobileProjectDescription) o;

        if (version != that.version) return false;
        if (!name.equals(that.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + version;
        return result;
    }

    @Override
    public IMovilizerCloudSystem getMasterDataCloudSystem() {
        return masterDataCloudSystem;
    }

    public int getId() {
        return id;
    }
}
