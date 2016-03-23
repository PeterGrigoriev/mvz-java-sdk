package com.movilizer.html5;

import com.movilizer.projectmanagement.IMovilizerProject;

import java.io.File;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public interface ICordovaProject extends IMovilizerProject {
    File getZipFilePath();

    String getDocumentKey();

    String getDocumentPool();

    String getTitle();

    boolean isFullScreen();
}
