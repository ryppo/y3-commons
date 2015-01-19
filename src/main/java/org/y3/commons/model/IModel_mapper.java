package org.y3.commons.model;

/** 
 * <p>Title: org.y3.commons.model - IModel_mapper</p>
 * <p>Description: </p>
 * <p>Copyright: 2014</p>
 * <p>Organisation: IT-Happens.de</p>
 * @author Christian.Rybotycky
 */
public interface IModel_mapper extends ISqliteJdbcModelMapper {
    
    public String getModelSelectSql(IModel_filter filter);
    
    public String getModelsSelectSql(IModel_filter filter);
    
    public IModel_list createModelList();
    
    public IModel_model createModel();
    
}
