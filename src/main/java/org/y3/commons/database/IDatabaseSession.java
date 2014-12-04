package org.y3.commons.database;

import java.sql.SQLException;
import org.y3.commons.model.IModel;
import org.y3.commons.model.IModelFilter;
import org.y3.commons.model.IModelList;
import org.y3.commons.model.IModelMapper;

/** 
 * <p>Title: org.y3.commons.database - IDatabaseSession</p>
 * <p>Description: </p>
 * <p>Copyright: 2014</p>
 * <p>Organisation: IT-Happens.de</p>
 * @author Christian.Rybotycky
 */
public interface IDatabaseSession {
    
    /**
     * Creates a connection object, for the database
     * @param databaseLocation location of the database
     * @throws java.lang.Exception
     */
    public void connect(String databaseLocation) throws Exception;
    
    public boolean isConnected() throws SQLException;
    
    public void disconnect() throws SQLException;
    
    public IModel loadModel(IModelMapper mapper, IModelFilter filter) throws Exception;
    
    public IModelList loadModels(IModelMapper mapper, IModelFilter filter) throws Exception;
    
    public void insertModel(IModelMapper mapper, IModel model) throws Exception;
    
    public void updateModel(IModelMapper mapper, IModel model) throws Exception;
    
    public void createMissingModelTable(IModelMapper mapper) throws Exception;
    
}
