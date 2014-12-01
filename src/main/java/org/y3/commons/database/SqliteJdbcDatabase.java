package org.y3.commons.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
public class SqliteJdbcDatabase extends IDatabaseSession {
    
    private final String driverClass = "org.sqlite.JDBC";
    private final String protocol = "jdbc:sqlite:";
    
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
                Statement stmt = connection.createStatement();
                ResultSet dbResult = stmt.executeQuery(sql);
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
                Statement stmt = connection.createStatement();
                ResultSet dbResult = stmt.executeQuery(sql);
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

}
