package org.y3.commons.model;

import java.sql.ResultSet;
import java.sql.SQLException;

/** 
 * <p>Title: org.y3.commons.model - ISqliteJdbcModelMapper</p>
 * <p>Description: </p>
 * <p>Copyright: 2014</p>
 * <p>Company: SE Bordnetze GmbH</p>
 * <p>Department: CIT SC GSD</p>
 * @author Christian.Rybotycky
 * @version $Id$
*/
public interface ISqliteJdbcModelMapper extends IModelMapper {
    
    public IModel map(ResultSet dbResult) throws SQLException;
    
    public static String sqlString(String sourceString) {
        if (sourceString == null) {
            return sourceString;
        } else {
            return "'" + sourceString + "'";
        }
    }
    
}
