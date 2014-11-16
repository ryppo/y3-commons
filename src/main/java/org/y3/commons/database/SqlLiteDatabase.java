package org.y3.commons.database;

/** 
 * <p>Title: org.y3.brain.database - SqlLiteDatabase</p>
 * <p>Description: </p>
 * <p>Copyright: 2014</p>
 * <p>Organisation: IT-Happens.de</p>
 * @author Christian.Rybotycky
 * @version $Id$
*/
public class SqlLiteDatabase {
    
    /**
     SQLiteConnection db = new SQLiteConnection(new File("/tmp/database"));
    db.open(true);
    ...
    SQLiteStatement st = db.prepare("SELECT order_id FROM orders WHERE quantity >= ?");
    try {
      st.bind(1, minimumQuantity);
      while (st.step()) {
        orders.add(st.columnLong(0));
      }
    } finally {
      st.dispose();
    }
    ...
    db.dispose();
     */

}
