package com.movilizer.projectmanagement;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class SingleMobileProjectLoader implements IMobileProjectLoader {

    private final MovilizerProjectBase project;

    public SingleMobileProjectLoader(MovilizerProjectBase project) {
        this.project = project;
    }


    @Override
    public IMovilizerProject loadImplementation(IMobileProjectSettings settings) {
        if(!projectMatchesDescription(project, settings)) {
            return null;
        }
        project.setProjectSettings(settings);
        return project;
    }


    @Override
    public Set<IMobileProjectDescription> getAvailableProjectDescriptions() {
        return new HashSet<IMobileProjectDescription>(Arrays.asList(project)) ;
    }

    private boolean projectMatchesDescription(IMovilizerProject project, IMobileProjectDescription description) {
        String name = description.getName();
        if(null == name) {
            throw new IllegalArgumentException("description.name is null");
        }
        return name.equals(project.getName()) && description.getVersion() == project.getVersion();
    }
}
