package com.movilizer.connector;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MovilizerCloudSystem implements IMovilizerCloudSystem {
    private int systemId;
    private String password;
    private String endpoint;
    private int timeout;

    public MovilizerCloudSystem(int systemId, String password, String endpoint, int timeout) {
        this.systemId = systemId;
        this.password = password;
        this.endpoint = endpoint;
        this.timeout = timeout;
    }

    public MovilizerCloudSystem() {}

    @Override
    public int getSystemId() {
        return systemId;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getEndpoint() {
        return endpoint;
    }

    @Override
    public int getTimeout() {
        return timeout;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovilizerCloudSystem)) return false;

        MovilizerCloudSystem that = (MovilizerCloudSystem) o;

        if (systemId != that.systemId) return false;
        if (!endpoint.equals(that.endpoint)) return false;
        //noinspection RedundantIfStatement
        if (!password.equals(that.password)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = systemId;
        result = 31 * result + password.hashCode();
        result = 31 * result + endpoint.hashCode();
        return result;
    }

    public void setSystemId(int systemId) {
        this.systemId = systemId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
