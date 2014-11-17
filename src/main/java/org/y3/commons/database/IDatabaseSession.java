package org.y3.commons.database;

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
public abstract class IDatabaseSession {
    
    /**
     * Creates a connection object, for the database
     * @param databaseLocation location of the database
     * @throws java.lang.Exception
     */
    public abstract void connect(String databaseLocation) throws Exception;
    
    public abstract boolean isConnected();
    
    public abstract void disconnect();
    
    public abstract IModel loadModel(IModelMapper mapper, IModelFilter filter) throws Exception;
    
    public abstract IModelList loadModels(IModelMapper mapper, IModelFilter filter) throws Exception;
    
}
