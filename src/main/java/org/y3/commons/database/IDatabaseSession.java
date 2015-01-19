package org.y3.commons.database;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.y3.commons.model.IModelMapper_filter;
import org.y3.commons.model.IModel_list;
import org.y3.commons.model.IModel_mapper;
import org.y3.commons.model.IModel_model;

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
    
    public abstract IModel_model loadModel(IModel_mapper mapper, IModelMapper_filter filter) throws Exception;
    
    public abstract IModel_list loadModels(IModel_mapper mapper, IModelMapper_filter filter) throws Exception;
    
    /**
     * Convert String to String for SQL usage
     * @param sourceString string to use in sql
     * @return "'[sourceString]'", if sourceString null then return null
     */
    public static String sqlString(String sourceString) {
        if (sourceString == null) {
            return null;
        } else {
            return "'" + sourceString + "'";
        }
    }
    
    /**
     * Convert BigDecimal to String for SQL usage
     * @param sourceBigDecimal big decimal to use in sql
     * @return "'[sourceBigDecimal.toString()]'", if sourceBigDecimal null then return null
     */
    public static String sqlString(BigDecimal sourceBigDecimal) {
        if (sourceBigDecimal == null) {
            return null;
        } else {
            return "'" + sourceBigDecimal.toString() + "'";
        }
    }
    
    /**
     * Convert Date to String for SQL usage
     * @param sourceDate date to use in sql
     * @return "'[sourceDate.toString()]'", if sourceDate null then return null
     */
    public static String sqlString(Date sourceDate) {
        if (sourceDate == null) {
            return null;
        } else {
            return sqlString(sourceDate.toString());
        }
    }
    
    /**
     * Convert java.util.Date to java.sql.date
     * @param utilDate date of type java.util.Date
     * @return date of type java.sql.Date
     */
    public static String convertUtilDateToSqlDate(java.util.Date utilDate) {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    String sqlDate = sdf.format(utilDate);
	    return sqlDate;
	}
    
}
