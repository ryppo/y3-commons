package org.y3.commons.database;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteStatement;
import java.io.File;
import java.sql.SQLException;
import org.y3.commons.model.IModel_filter;
import org.y3.commons.model.IModel_list;
import org.y3.commons.model.IModel_mapper;
import org.y3.commons.model.IModel_model;
import org.y3.commons.model.ISqlite4JavaModelMapper;

/** 
 * <p>Title: org.y3.brain.database - SqlLite4JavaDatabase</p>
 * <p>Description: </p>
 * <p>Copyright: 2014</p>
 * <p>Organisation: IT-Happens.de</p>
 * @author Christian.Rybotycky
 */
public class SqlLite4JavaDatabase extends IDatabaseSession {
    
    private SQLiteConnection sqlite;
    
    public SqlLite4JavaDatabase() {    
    }
    
    @Override
    public void connect(String databaseLocation) throws Exception {
        sqlite = new SQLiteConnection(new File(databaseLocation));
        sqlite.open();
    }

    @Override
    public boolean isConnected() throws SQLException {
        if (sqlite != null && sqlite.isOpen()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void disconnect() throws SQLException {
        if (sqlite != null) {
            if (sqlite.isOpen()) {
                sqlite.dispose();
            }
            sqlite = null;
        }
    }

    @Override
    public IModel_model loadModel(IModel_mapper _mapper, IModel_filter filter) throws Exception {
        IModel_model model = null;
        if (_mapper instanceof ISqlite4JavaModelMapper) {
            ISqlite4JavaModelMapper mapper = (ISqlite4JavaModelMapper) _mapper;
            String sql = mapper.getModelSelectSql(filter);
            SQLiteStatement stmt = sqlite.prepare(sql);
            stmt.bindNull(null);
            if (stmt.step()) {
                model = mapper.map(stmt);
            }
        }
        return model;
    }

    @Override
    public IModel_list loadModels(IModel_mapper _mapper, IModel_filter filter) throws Exception {
        IModel_list models = null;
        if (_mapper instanceof ISqlite4JavaModelMapper) {
            ISqlite4JavaModelMapper mapper = (ISqlite4JavaModelMapper) _mapper;
            String sql = mapper.getModelsSelectSql(filter);
            SQLiteStatement stmt = sqlite.prepare(sql);
            stmt.bindNull(null);
            while (stmt.step()) {
                if (models == null) {
                    models = mapper.createModelList();
                }
                models.add(mapper.map(stmt));
            }
        }
        return models;
    }
    

}
