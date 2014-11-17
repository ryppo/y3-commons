package org.y3.commons.model;

import com.almworks.sqlite4java.SQLiteStatement;

/** 
 * <p>Title: org.y3.commons.model - IModelMapper</p>
 * <p>Description: </p>
 * <p>Copyright: 2014</p>
 * <p>Organisation: IT-Happens.de</p>
 * @author Christian.Rybotycky
 */
public abstract class IModelMapper {
    
    public abstract String getModelSelectSql(IModelFilter filter);
    
    public abstract String getModelsSelectSql(IModelFilter filter);
    
    public abstract IModel mapSqliteStatementToModel(SQLiteStatement stmt);
    
}
