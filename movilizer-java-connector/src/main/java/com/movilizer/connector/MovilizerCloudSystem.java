package com.movilizer.connector;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MovilizerCloudSystem implements IMovilizerCloudSystem {
    private final int systemId;
    private final String password;
    private final String endpoint;
    private final int timeout;

    public MovilizerCloudSystem(int systemId, String password, String endpoint, int timeout) {
        this.systemId = systemId;
        this.password = password;
        this.endpoint = endpoint;
        this.timeout = timeout;
    }

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
}
