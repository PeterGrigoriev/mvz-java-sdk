package com.movilizer.projectmanagement;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MobileProjectEvent implements IMobileProjectEvent {
    private int id;
    private IMobileProjectDescription project;
    private MobileProjectEventType type;

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setProject(IMobileProjectDescription project) {
        this.project = project;
    }

    @Override
    public IMobileProjectDescription getProjectDescription() {
        return project;
    }

    public void setType(MobileProjectEventType type) {
        this.type = type;
    }

    @Override
    public MobileProjectEventType getType() {
        return type;
    }
}
