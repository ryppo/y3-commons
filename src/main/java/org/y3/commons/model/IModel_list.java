package org.y3.commons.model;

import java.util.ArrayList;

/** 
 * <p>Title: org.y3.commons.model - IModel_model</p>
 * <p>Description: List of models</p>
 * <p>Copyright: 2014</p>
 * <p>Organisation: IT-Happens.de</p>
 * @author Christian.Rybotycky
 */
public abstract class IModel_list extends ArrayList<IModel_model> {
    
    private Boolean containsTheSubmodels;

    public Boolean getContainsTheSubmodels() {
        return containsTheSubmodels;
    }

    public void setContainsTheSubmodels(Boolean containsTheSubmodels) {
        this.containsTheSubmodels = containsTheSubmodels;
    }
    
    public IModel_list() {
        super();
    }
    
    public abstract IModel_model getModel(int position);
    
}
