package org.y3.commons.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.y3.commons.database.SqLiteJdbcDatabase;

/** 
 * <p>Title: org.y3.commons.model - IModel_mapper</p>
 * <p>Description: </p>
 * <p>Copyright: 2014</p>
 * <p>Organisation: IT-Happens.de</p>
 * @author Christian.Rybotycky
 */
public abstract class IModel_mapper implements ISqliteJdbcModelMapper {
    
    private SqLiteJdbcDatabase database = null;
    
    /**
     * Constructor which expects the database as parameter
     * @param _database the database to be used
     */
    public IModel_mapper(SqLiteJdbcDatabase _database) {
        database = _database;
    }
    
    /**
     * Sets the database object
     * @param _database the database to be used
     */
    public void setDatabase(SqLiteJdbcDatabase _database) {
        database = _database;
    }
    
    /**
     * Gets the database object
     * @return the database object
     */
    public SqLiteJdbcDatabase getDatabase() {
        return database;
    }
    
    public int getLastCreatedModelId() throws SQLException {
        int createdModelId = -1;
        String sql = "SELECT IDENT_CURRENT('" + getTableName() + "')";
        ResultSet dbResult = getDatabase().sqlSelect(sql, null, null);
        if (dbResult.next()) {
            createdModelId = dbResult.getInt(1);
        }
        return createdModelId;
    }
    
    public IModel_list loadModels(IModelMapper_filter filter) throws SQLException {
        IModel_list modelList = null;
        String sql = "SELECT * FROM " + getTableName();
        ResultSet dbResult = getDatabase().sqlSelect(sql, createModelWhereClause(filter), null);
        if (dbResult != null) {
            modelList = createModelList();
            while (dbResult.next()) {
                IModel_model model = map(dbResult);
                modelList.add(model);
            }
        }
        return modelList;
    }
    
    /**
     * Loads a single model from database matching the given filter values. If multiple models match the filter, then the first model is returned.
     * @param filter filter for the model
     * @return the model
     * @throws SQLException 
     */
    public IModel_model loadModel(IModelMapper_filter filter) throws SQLException {
        IModel_model model = null;
        IModel_list loadedModels = loadModels(filter);
        if (loadedModels != null && loadedModels.size() > 0 && loadedModels.get(0) instanceof IModel_model) {
            model = (IModel_model) loadedModels.get(0);
        }
        return model;
    }
    
    /**
     * Loads a list of entities matching the given filter values
     * @param filter filter for the entities
     * @return the list of entities
     * @throws SQLException 
     */
    public ArrayList<IModel_entity> loadEntities(IModelMapper_filter filter) throws SQLException {
        ArrayList<IModel_entity> entityList = null;
        String sql = "SELECT * FROM " + getTableName();
        ResultSet dbResult = getDatabase().sqlSelect(sql, createEntityWhereClause(filter), null);
        if (dbResult != null) {
            entityList = new ArrayList<>();
            while (dbResult.next()) {
                IModel_entity entity = mapCurrentEntity(dbResult);
                entityList.add(entity);
            }
        }
        return entityList;
    }
    
    /**
     * Creates the sql where clause to load models with filter values
     * @param filter contains values to respect in the where clause
     * @return the where clause
     */
    public abstract String createModelWhereClause(IModelMapper_filter filter);
    
    /**
     * Creates the sql where clause to load entities with filter values
     * @param filter contains values to respect in the where clause
     * @return the where clause
     */
    public abstract String createEntityWhereClause(IModelMapper_filter filter);
    
    /**
     * Creates a new entity from given database record
     * @param modelResultSet database record with cursor position at entity
     * @return new entity with database record values
     * @throws SQLException 
     */
    public abstract IModel_entity mapCurrentEntity(ResultSet modelResultSet) throws SQLException;
    
    /**
     * Creates a new model list
     * @return the new model list
     */
    public abstract IModel_list createModelList();
    
    /**
     * Creates a new model in the database
     * @param filter contains initial values to set for the new model
     * @return the created model
     * @throws SQLException 
     */
    public abstract IModel_model createModel(IModelMapper_filter filter) throws SQLException;
    
    /**
     * Gives the name of the database table for the model
     * @return database table name
     */
    public abstract String getTableName();
    
    /**
     * Updates the model in the database
     * @param model model to update
     * @return count of updated models
     * @throws SQLException 
     */
    public abstract Integer updateModel(IModel_model model) throws SQLException;
    
    /**
     * Deletes models from the database
     * @param filter filter for the models
     * @return count of deleted models
     * @throws SQLException 
     */
    public Integer deleteModels(IModelMapper_filter filter) throws SQLException {
        String sql = "DELETE FROM " + getTableName() + createModelWhereClause(filter);
        return getDatabase().sqlUpdate(sql);
    }
    
}
