package org.y3.commons.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;

/**
 * <p>Title: org.y3.commons.database - IJdbcDatabaseSession</p>
 * <p>Description: </p>
 * <p>Copyright: 2015</p>
 * <p>Organisation: IT-Happens.de</p>
 * @author Christian.Rybotycky
 */
public abstract class IJdbcDatabaseSession {
    
    private Connection connection;
    private Statement session;
    private final String databaseLocation;
    
    public IJdbcDatabaseSession(String _databaseLocation) throws ClassNotFoundException, SQLException {
        databaseLocation = _databaseLocation;
        init();
    }
    
    private void init() throws ClassNotFoundException, SQLException {
        Class.forName(getDriverClassName());
    }
    
    public abstract String getDriverClassName();
    
    public abstract String getProtocolName();

    /**
     * Creates a connection object, for the database
     * @throws java.sql.SQLException
     */
    public void connect() throws SQLException {
        connection = DriverManager.getConnection("jdbc:" + getProtocolName() + ":" + databaseLocation);
    }
    
    public boolean isConnected() {
        boolean isConnected = false;
        try {
            if (connection != null && !connection.isClosed()) {
                isConnected = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(getClass()).error(ex);
        } finally {
            return isConnected;
        }
    }
    
    public void disconnect() {
        try {
            if (connection != null) {
                connection.close();
                connection = null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(getClass()).error(ex);
        }
    }
    
    public int sqlUpdate(String sql) throws SQLException {
        Logger.getLogger(getClass()).debug("SQL Update : " + sql);
        int updateResult = session.executeUpdate(sql);
        return updateResult;
    }
    
    public ResultSet sqlSelect(String sql) throws SQLException {
        Logger.getLogger(getClass()).debug("SQL Select : " + sql);
        return session.executeQuery(sql);
    }

}
