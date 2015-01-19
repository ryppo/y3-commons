package org.y3.commons.model;

import java.sql.ResultSet;

/** 
 * <p>Title: org.y3.commons.model - ISqliteJdbcModelMapper</p>
 * <p>Description: </p>
 * <p>Copyright: 2014</p>
 * <p>Company: SE Bordnetze GmbH</p>
 * <p>Department: CIT SC GSD</p>
 * @author Christian.Rybotycky
 * @version $Id$
*/
public interface ISqliteJdbcModelMapper {
    
    public IModel_model map(ResultSet dbResult);

}
