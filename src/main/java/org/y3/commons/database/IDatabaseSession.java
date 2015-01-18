package org.y3.commons.database;

import java.sql.SQLException;
import org.y3.commons.model.IModel_model;
import org.y3.commons.model.IModel_filter;
import org.y3.commons.model.IModel_list;
import org.y3.commons.model.IModel_mapper;

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
    
    public abstract boolean isConnected() throws SQLException;
    
    public abstract void disconnect() throws SQLException;
    
    public abstract IModel_model loadModel(IModel_mapper mapper, IModel_filter filter) throws Exception;
    
    public abstract IModel_list loadModels(IModel_mapper mapper, IModel_filter filter) throws Exception;
    
}
