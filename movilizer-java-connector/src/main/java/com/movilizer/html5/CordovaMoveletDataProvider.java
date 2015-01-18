package com.movilizer.html5;

import com.movilizer.projectmanagement.IMovilizerProject;
import com.movilizer.util.movelet.SimpleMoveletDataProvider;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class CordovaMoveletDataProvider extends SimpleMoveletDataProvider {
    private final CordovaProject project;

    public CordovaMoveletDataProvider(CordovaProject project) {
        this.project = project;
    }

    public IMovilizerProject getProject() {
        return project;
    }

    public String getTitle() {
        return project.getTitle();
    }

    public String getMoveletKey() {
        return project.getMoveletKey();
    }

    public int getVersion() {
        return project.getVersion();
    }

    public String getDocumentPool() {
        return project.getDocumentPool();
    }

    public String getDocumentKey() {
        return project.getName();
    }
}
