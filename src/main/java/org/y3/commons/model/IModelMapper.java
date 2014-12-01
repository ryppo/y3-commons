package org.y3.commons.model;

/** 
 * <p>Title: org.y3.commons.model - IModelMapper</p>
 * <p>Description: </p>
 * <p>Copyright: 2014</p>
 * <p>Organisation: IT-Happens.de</p>
 * @author Christian.Rybotycky
 */
public interface IModelMapper {
    
    public String getModelSelectSql(IModelFilter filter);
    
    public String getModelsSelectSql(IModelFilter filter);
    
    public IModelList createModelList();
    
    public IModel createModel();
    
}
