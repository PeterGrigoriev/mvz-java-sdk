package com.movilizer.usermanagement;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MobileUserEvent implements IMobileUserEvent {
    private int id;
    private MobileUserEventType type;
    private IMovilizerUser user;

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setType(MobileUserEventType type) {
        this.type = type;
    }

    @Override
    public MobileUserEventType getType() {
        return type;
    }

    public void setUser(IMovilizerUser user) {
        this.user = user;
    }

    @Override
    public IMovilizerUser getUser() {
        return user;
    }
}
