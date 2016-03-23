package com.movilizer.projectmanagement;

import java.util.Set;

public interface IMobileProjectLoader {

    /**
     * @param settings - settings of the mobile project. id is allowed to be 0
     * @return project implementation, if found, null otherwise
     */
    IMovilizerProject loadImplementation(IMobileProjectSettings settings);

    Set<IMobileProjectDescription> getAvailableProjectDescriptions();

}
