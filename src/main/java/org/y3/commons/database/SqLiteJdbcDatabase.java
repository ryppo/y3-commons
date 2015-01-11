package org.y3.commons.database;

import java.sql.SQLException;

/**
 * <p>Title: org.y3.commons.database - SqLiteJdbcDatabase</p>
 * <p>Description: </p>
 * <p>Copyright: 2015</p>
 * <p>Organisation: IT-Happens.de</p>
 * @author Christian.Rybotycky
 */
public class SqLiteJdbcDatabase extends IJdbcDatabaseSession {
    
    public SqLiteJdbcDatabase(String _databaseLocation) throws ClassNotFoundException, SQLException {
        super(_databaseLocation);
    }
    
    @Override
    public String getDriverClassName() {
        return "org.sqlite.JDBC";
    }

    @Override
    public String getProtocolName() {
        return "sqlite";
    }

}
