package org.y3.commons.database;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteStatement;
import java.io.File;
import org.y3.commons.model.IModel;
import org.y3.commons.model.IModelFilter;
import org.y3.commons.model.IModelList;
import org.y3.commons.model.IModelMapper;

/** 
 * <p>Title: org.y3.brain.database - SqlLiteDatabase</p>
 * <p>Description: </p>
 * <p>Copyright: 2014</p>
 * <p>Organisation: IT-Happens.de</p>
 * @author Christian.Rybotycky
 */
public class SqlLiteDatabase extends IDatabaseSession {
    
    private SQLiteConnection sqlite;
    
    public SqlLiteDatabase() {    
    }
    
    @Override
    public void connect(String databaseLocation) throws Exception {
        sqlite = new SQLiteConnection(new File(databaseLocation));
        sqlite.open();
    }

    @Override
    public boolean isConnected() {
        if (sqlite != null && sqlite.isOpen()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void disconnect() {
        if (sqlite != null) {
            if (sqlite.isOpen()) {
                sqlite.dispose();
            }
            sqlite = null;
        }
    }

    @Override
    public IModel loadModel(IModelMapper mapper, IModelFilter filter) throws Exception {
        IModel model = null;
        String sql = mapper.getModelSelectSql(filter);
        SQLiteStatement stmt = sqlite.prepare(sql);
        stmt.bindNull(null);
        if (stmt.step()) {
            model = mapper.mapSqliteStatementToModel(stmt);
        }
        return model;
    }

    @Override
    public IModelList loadModels(IModelMapper mapper, IModelFilter filter) throws Exception {
        IModelList models = null;
        String sql = mapper.getModelsSelectSql(filter);
        SQLiteStatement stmt = sqlite.prepare(sql);
        stmt.bindNull(null);
        while (stmt.step()) {
            if (models == null) {
                models = new IModelList();
            }
            models.add(mapper.mapSqliteStatementToModel(stmt));
        }
        return models;
    }
    

}
