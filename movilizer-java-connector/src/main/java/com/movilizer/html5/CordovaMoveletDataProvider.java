package com.movilizer.html5;

import com.movilizer.projectmanagement.IMovilizerProject;
import com.movilizer.util.movelet.SimpleMoveletDataProvider;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class CordovaMoveletDataProvider extends SimpleMoveletDataProvider {
    private final IMovilizerProject project;

    public CordovaMoveletDataProvider(IMovilizerProject project) {
        this.project = project;
    }

    public IMovilizerProject getProject() {
        return project;
    }

    public String getTitle() {
        return project.getName();
    }

    public String getMoveletKey() {
        return project.getName();
    }

    public int getVersion() {
        return project.getVersion();
    }

    public String getDocumentPool() {
        return "HTML5_APPS";
    }
}
