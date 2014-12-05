package org.y3.commons.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;
import org.y3.commons.model.IModel;
import org.y3.commons.model.IModelFilter;
import org.y3.commons.model.IModelList;
import org.y3.commons.model.IModelMapper;
import org.y3.commons.model.ISqliteJdbcModelMapper;

/** 
 * <p>Title: org.y3.commons.database - SqliteJdbcDatabase</p>
 * <p>Description: </p>
 * <p>Copyright: 2014</p>
 * <p>Company: SE Bordnetze GmbH</p>
 * <p>Department: CIT SC GSD</p>
 * @author Christian.Rybotycky
 * @version $Id$
*/
public abstract class SqliteJdbcDatabase implements IDatabaseSession {
    
    private final String driverClass = "org.sqlite.JDBC";
    private final String protocol = "jdbc:sqlite:";
    
    public final Logger LOG = Logger.getLogger(SqliteJdbcDatabase.class);
    
    private Connection connection;

    @Override
    public void connect(String databaseLocation) throws Exception {
        if (connection == null) {
            Class.forName(driverClass);
        } else {
            connection.close();
            connection = null;
        }
        connection = DriverManager.getConnection(protocol + databaseLocation);
    }
    
    public abstract void executeAfterConnect();

    @Override
    public boolean isConnected() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            return true;
        }
        return false;
    }

    @Override
    public void disconnect() throws SQLException {
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }

    @Override
    public IModel loadModel(IModelMapper _mapper, IModelFilter filter) throws Exception {
        IModel model = null;
        if (_mapper instanceof ISqliteJdbcModelMapper) {
            ISqliteJdbcModelMapper mapper = (ISqliteJdbcModelMapper) _mapper;
            String sql = mapper.getModelSelectSql(filter);
            if (sql != null) {
                ResultSet dbResult = selectDb(sql);
                if (dbResult != null && dbResult.next()) {
                    model = mapper.map(dbResult);
                }
            }
        }
        return model;
    }

    @Override
    public IModelList loadModels(IModelMapper _mapper, IModelFilter filter) throws Exception {
        IModelList modelList = null;
        if (_mapper instanceof ISqliteJdbcModelMapper) {
            ISqliteJdbcModelMapper mapper = (ISqliteJdbcModelMapper) _mapper;
            String sql = mapper.getModelsSelectSql(filter);
            if (sql != null) {
                ResultSet dbResult = selectDb(sql);
                if (dbResult != null) {
                    while (dbResult.next()) {
                        if (modelList == null) {
                            modelList = mapper.createModelList();
                        }
                        modelList.add(mapper.map(dbResult));
                    }
                }
            }
        }
        return modelList;
    }

    @Override
    public void createMissingModelTable(IModelMapper mapper) throws Exception {
        //check table exists
        String sql = "SELECT COUNT(*) FROM sqlite_master WHERE type='table' AND name='" + mapper.getDatabaseTableName() + "'";
        ResultSet dbResult = selectDb(sql);
        if (dbResult != null && dbResult.next()) {
            int tableCount = dbResult.getInt(1);
            if (tableCount == 0) {
                //create table
                LOG.debug("createMissingModelTable: " + mapper.getDatabaseTableName());
                String createSql = mapper.getCreateDatabaseTableSql();
                int createdTableCount = updateDb(createSql);
                LOG.debug("table " + mapper.getDatabaseTableName() + "created.");
            }
        }
    }

    @Override
    public void insertModel(IModelMapper mapper, IModel model) throws Exception {
        String sql = mapper.getModelInsertSql(model);
        int updateDb = updateDb(sql);
    }

    @Override
    public void updateModel(IModelMapper mapper, IModel model) throws Exception {
        String sql = mapper.getModelUpdateSql(model);
        int updateDb = updateDb(sql);
    }
    
    private ResultSet selectDb(String sql) throws SQLException {
        LOG.debug("selectDb: " + sql);
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(sql);
    }
    
    private int updateDb(String sql) throws SQLException {
        LOG.debug("updateDb: " + sql);
        Statement stmt = connection.createStatement();
        return stmt.executeUpdate(sql);
    }

}
