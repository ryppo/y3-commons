package org.y3.commons.model;

/** 
 * <p>Title: org.y3.commons.model - IModelMapper</p>
 * <p>Description: </p>
 * <p>Copyright: 2014</p>
 * <p>Organisation: IT-Happens.de</p>
 * @author Christian.Rybotycky
 */
public interface IModelMapper {
    
    public String getModelSelectSql(IModelFilter filter);
    
    public String getModelsSelectSql(IModelFilter filter);
    
    public String getModelUpdateSql(IModel model);
    
    public String getModelInsertSql(IModel model);
    
    public String getDatabaseTableName();
    
    public String getCreateDatabaseTableSql();
    
    public IModelList createModelList();
    
    public IModel createModel();
    
    public final static String FILTER_COMPARATOR_EQUALS = " = ";
    public final static String FILTER_COMPARATOR_LIKE = " LIKE ";
    public final static String FILTER_COMPARATOR_NOT = " NOT ";
    public final static String FILTER_COMPARATOR_SMALLER_THAN = " <= ";
    public final static String FILTER_COMPARATOR_GREATER_THAN = " >= ";
    public final static String FILTER_COMPARATOR_EXCLUDE = " <> ";
    
    public static  String addExcludeNotEmptyStringWhere(String where, String key) {
        if (where != null && where.length() > 0) {
            where += " AND ";
        } else {
            where = " WHERE ";
        }
        where += key + FILTER_COMPARATOR_EXCLUDE + "''";
        return where;
    }
    
    public static String addNotNullStringWhere(String where, String key, String comparator, String value) {
        if (value != null && value.length() > 0) {
            if (where != null && where.length() > 0) {
                where += " AND ";
            } else {
                where = " WHERE ";
            }
            where += key + comparator + "'" + value + "'";
        }
        return where;
    }
    
    public static String addNotNullIntegerWhere(String where, String key, String comparator, int value) {
        if (where != null && where.length() > 0) {
            where += " AND ";
        } else {
            where = " WHERE ";
        }
        where += key + comparator + "'" + value + "";
        return where;
    }
    
    public static String addNotNullStringWhere(String where, String key, String comparator, String[] values) {
        if (values != null && values.length > 0) {
            String multi = null;
            if (where != null && where.length() > 0) {
                where += " AND ";
            } else {
                where = " WHERE ";
            }
            for (String value : values) {
                if (multi == null) {
                    multi = "(";
                } else {
                    multi += " OR ";
                }
                multi += key + FILTER_COMPARATOR_EQUALS + "'" + value + "'";
            }
            if (multi != null) {
                multi += ")";
            }
            where += multi;
        }
        return where;
    }

    
    
}
