package com.github.youyinnn.youdbutils.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 提供生成基本sql语句的相关方法.
 *
 * @author youyinnn
 */
public class SqlStringUtils {

    private static final String SELECT_ALL_FROM = "SELECT * FROM ";

    /**
     * 获取一个查询所有记录的语句 也即不带条件
     * exp：
     *  1 当queryFieldList为空的时候
     *      SELECT * FROM tableName
     *  2 当不为空的时候
     *      SELECT field1, field2, ... , fieldn
     *      FROM tableName
     *
     * @param tableName      the table name
     * @param queryFieldList the query field list
     * @return the select all sql
     */
    public static String getSelectAllSql(String tableName, ArrayList<String> queryFieldList) {
        StringBuffer sb = new StringBuffer();
        getSelectFromSubStr(sb, queryFieldList);
        sb.append(tableName);

        return sb.toString();
    }

    /**
     * 获取一个带条件查询的语句 条件全部使用选定的separate连接
     * exp：
     *  1 当separate为AND、queryFieldList为空的时候
     *      SELECT *
     *      FROM tableName
     *      WHERE conditionKey1 = ? AND conditionKey2 = ? ... AND conditionKeyn = ?
     *  2 当为OR的时候、queryFieldList为空的时候
     *      SELECT *
     *      FROM tableName
     *      WHERE conditionKey1 = ? OR conditionKey2 = ? ... OR conditionKeyn = ?
     *  3 queryFieldList不为空
     *      略
     *
     * @param tableName       the table name
     * @param conditionKeySet the key set
     * @param separateMark    the separateMark
     * @param queryFieldList  the query field list
     * @return the string
     */
    public static String getSelectFromWhereSql(String tableName, Set<String> conditionKeySet, String separateMark, ArrayList<String> queryFieldList){
        StringBuffer sb = new StringBuffer();
        getSelectFromSubStr(sb, queryFieldList);
        sb.append(tableName)
                .append(getWhereSubStr(conditionKeySet,separateMark));

        return sb.toString();
    }

    /**
     * 获取一个带模糊查询的语句 条件值全一律用%包着 条件全部使用选定的separateMark连接
     * exp：
     *  1 当separate为AND、queryFieldList为空的时候
     *      SELECT *
     *      FROM tableName
     *      WHERE key1 LIKE '%value1%' AND key2 LIKE '%value2%' ... AND keyn LIKE '%valuen%'
     *  2 separate为OR、queryFieldList不为空
     *      略
     *
     * @param tableName      the table name
     * @param conditionsMap  the conditions map
     * @param separateMark   the separateMark
     * @param queryFieldList the query field list
     * @return the string
     */
    public static String getSelectFromWhereLikeSql(String tableName, HashMap<String,Object> conditionsMap, String separateMark, ArrayList<String> queryFieldList){
        StringBuffer sb = new StringBuffer();
        getSelectFromSubStr(sb, queryFieldList);
        sb.append(tableName)
                .append(getWhereLikeSubStr(conditionsMap,separateMark));

        return sb.toString();
    }

    /**
     * 获取一个用于更新的语句 可以接收新值的key和查询条件的key 条件全部使用选定的separateMark连接
     * exp:
     *  1 UPDATE tableName
     *      SET updateField1 = ?, updateField2 = ?, ... , updateFieldn = ?
     *      WHERE conditionKey1 = ? AND conditionKey2 = ? ... AND conditionKeyn = ?
     *  2 后略
     *
     * @param tableName       the table name
     * @param updateFieldSet  the update field set
     * @param separateMark    连接条件的连词
     * @param conditionKeySet the condition key set
     * @return the update set where sql
     */
    public static String getUpdateSetWhereSql(String tableName, Set<String> updateFieldSet, String separateMark, Set<String> conditionKeySet) {
        StringBuilder sb = new StringBuilder();

        sb.append("UPDATE ")
                .append(tableName)
                .append(" SET ");

        for (String updateField : updateFieldSet) {
            sb.append(updateField)
                    .append(" = ? , ");
        }

        sb.deleteCharAt(sb.lastIndexOf(","));

        if (conditionKeySet != null) {
            sb.append(getWhereSubStr(conditionKeySet,separateMark));
        }

        return sb.toString();
    }

    /**
     * 获取一个用于插入记录的语句 只接收要更新的字段名 其中insertFieldSet不能为空
     *
     * @param tableName      the table name
     * @param insertFieldSet the insert field set
     * @return the insert sql
     */
    public static String getInsertSql(String tableName, Set<String> insertFieldSet) {
        StringBuilder sb = new StringBuilder();

        sb.append("INSERT INTO ")
                .append(tableName)
                .append("(");

        for (String insertField : insertFieldSet) {
            sb.append(insertField)
                    .append(",");
        }

        int lastIndexOf = sb.lastIndexOf(",");

        sb.replace(lastIndexOf,lastIndexOf+1,")");

        sb.append(" VALUES(");

        for (int i = 0 ; i < insertFieldSet.size() ; ++i) {
            sb.append("?,");
        }

        lastIndexOf = sb.lastIndexOf(",");

        sb.replace(lastIndexOf,lastIndexOf+1,")");

        return sb.toString();
    }

    /**
     * 获取一个用于删除的语句 只接收条件set 条件使用指定的separateMark连接
     *
     * @param tableName       the table name
     * @param separateMark    连接条件的连词
     * @param conditionKeySet the condition key set
     * @return the delete sql
     */
    public static String getDeleteSql(String tableName,String separateMark, Set<String> conditionKeySet) {
        return "DELETE FROM " +
                tableName +
                getWhereSubStr(conditionKeySet, separateMark);
    }

    /**
     * 检查queryFieldList是否为空 来构造tableName之前的Sql语句
     *
     * @param sb             the sb
     * @param queryFieldList the query field list
     */
    private static void getSelectFromSubStr(StringBuffer sb, ArrayList<String> queryFieldList) {
        if (queryFieldList == null) {
            sb.append(SELECT_ALL_FROM);
        } else {
            sb.append("SELECT ");
            for (String s : queryFieldList) {
                sb.append(s)
                        .append(", ");
            }
            sb.deleteCharAt(sb.lastIndexOf(","));
            sb.append(" FROM ");
        }
    }

    /**
     * 生成条件子串 这个子串使用?占位符来配合PreparedStatement查询 条件使用指定的separateMark连接
     *
     * @param conditionKeySet the condition key set
     * @param separateMark    连接条件的连词
     * @return where sub str
     */
    public static String getWhereSubStr(Set<String> conditionKeySet, String separateMark) {

        StringBuilder sb = new StringBuilder(" WHERE ");

        for (String s : conditionKeySet) {
            sb.append(s).append(" = ? ").append(separateMark).append(" ");
        }

        sb.delete(sb.lastIndexOf(separateMark), sb.length()-1);

        return sb.toString();
    }

    /**
     * 生成模糊条件子串 条件使用指定的separateMark连接
     *
     * @param conditionsMap the conditions map
     * @param separateMark  连接条件的连词
     * @return where like sub str
     */
    private static String getWhereLikeSubStr(HashMap<String, Object> conditionsMap, String separateMark) {

        StringBuilder sb = new StringBuilder(" WHERE ");

        for (Map.Entry<String, Object> stringObjectEntry : conditionsMap.entrySet()) {
            sb.append(stringObjectEntry.getKey())
                    .append(" LIKE ")
                    .append("'%")
                    .append(stringObjectEntry.getValue())
                    .append("%' ")
                    .append(separateMark)
                    .append(" ");
        }

        sb.delete(sb.lastIndexOf(separateMark), sb.length()-1);

        return sb.toString();
    }

}
