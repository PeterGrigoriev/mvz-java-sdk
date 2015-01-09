package com.movilizer.assignmentmanagement;

import com.movilizer.projectmanagement.IMobileProjectDescription;
import com.movilizer.usermanagement.IMovilizerUser;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MobileAssignmentEvent implements IMobileAssignmentEvent {
    private int id;
    private MobileAssignmentEventType type;
    private IMovilizerUser user;
    private IMobileProjectDescription projectDescription;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public MobileAssignmentEventType getType() {
        return type;
    }

    @Override
    public IMovilizerUser getUser() {
        return user;
    }

    @Override
    public IMobileProjectDescription getProjectDescription() {
        return projectDescription;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(MobileAssignmentEventType type) {
        this.type = type;
    }

    public void setUser(IMovilizerUser user) {
        this.user = user;
    }

    public void setProjectDescription(IMobileProjectDescription description) {
        this.projectDescription = description;
    }
}
