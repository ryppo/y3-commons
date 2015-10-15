package org.y3.commons.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
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

    public ResultSet sqlSelect(String sql, String whereClause, String orderClause) throws SQLException {
        if (whereClause != null && whereClause.length() > 0) {
            sql += whereClause;
        }
        if (orderClause != null && orderClause.length() > 0) {
            sql += orderClause;
        }
        Logger.getLogger(getClass()).debug("SQL Select : " + sql);
        return session.executeQuery(sql);
    }

    public String getDatabaseUsername() throws SQLException {
        if (session != null && session.getConnection() != null) {
            return session.getConnection().getMetaData().getUserName();
        }
        return null;
    }
    
    public String createWhereClause(String filter, String value, boolean exact) {
            return createWhereClause(new String[]{filter}, new String[]{value}, exact);
        }

	public String createWhereClause(String[] filters, String[] values, boolean exact) {
		String where = null;
		if (filters != null && filters.length > 0 && values != null
				&& values.length == filters.length) {
			for (int filter = 0; filter < filters.length; filter++) {
				if (values[filter] != null && values[filter].length() > 0) {
					if (where == null) {
						where = " WHERE";
					} else {
						where += " AND ";
					}
					if (exact) {
						where += " " + filters[filter] + " = '" + values[filter] + "'";
					} else {
						where += " " + filters[filter] + " LIKE '%" + values[filter] + "%'";
					}
				}
			}
		}
		return where;
	}
        
        /**
         * Proxy, to use HashMap for createWhereClause(String[],String[],boolean
         * @param filterKeyValues container with keys and values for where clause
         * @param exact if true, use =, else use LIKE
         * @return result of #createWhereClause(String[],String[],boolean)
         */
        public String createWhereClause(HashMap<String, String> filterKeyValues, boolean exact) {
            String[] filterKeys = null;
            String[] filterValues = null;
            if (filterKeyValues != null) {
                filterKeys = new String[filterKeyValues.size()];
                filterValues = new String[filterKeyValues.size()];
            }
            int filterCount = 0;
            for (Map.Entry<String, String> filterKeyValue : filterKeyValues.entrySet()) {
                String filterKey = filterKeyValue.getKey();
                String filterValue = filterKeyValue.getValue();
                filterKeys[filterCount] = filterKey;
                filterValues[filterCount] = filterValue;
                filterCount++;
            }
            return createWhereClause(filterKeys, filterValues, exact);
        }
        
        /**
         * Creates the order clause for the sql statement
         * @param column column to sort
         * @param direction direction to sort
         * @return sql order clause
         */
	public String createOderClause(String column, String direction) {
		String orderClause = null;
		if (column != null && column.length() > 0) {
			orderClause = " ORDER BY " + column;
			if (direction != null && direction.length() > 0) {
				orderClause += " " + direction;
			}
		}
		return orderClause;
	}

}
