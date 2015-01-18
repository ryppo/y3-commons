package org.y3.commons.model;

import com.almworks.sqlite4java.SQLiteStatement;

/** 
 * <p>Title: org.y3.commons.model - ISqlite4JavaModelMapper</p>
 * <p>Description: </p>
 * <p>Copyright: 2014</p>
 * <p>Company: SE Bordnetze GmbH</p>
 * <p>Department: CIT SC GSD</p>
 * @author Christian.Rybotycky
 * @version $Id$
*/
public interface ISqlite4JavaModelMapper extends IModel_mapper {

    public IModel_model map(SQLiteStatement stmt);
    
}
