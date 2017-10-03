package cn.youyinnn.youDataBase.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The type Sql string utils.
 *
 * @description:
 * @author: youyinnn
 * @date: 2017 /10/3
 */
public class SqlStringUtils {

    private static final String SELECT_ALL_FROM = "SELECT * FROM ";

    /**
     * 获取一个查询所有记录的语句 也即不带条件
     *
     * exp：
     *  1 当queryFieldList为空的时候
     *      SELECT * FROM tableName
     *
     *  2 当不为空的时候
     *      SELECT field1, field2, ... , fieldn FROM tableName
     *
     * @param tableName      the table name
     * @param queryFieldList the query field list
     * @return the select all sql
     */
    public static String getSelectAllSql(String tableName, ArrayList<String> queryFieldList) {
        StringBuilder sb = new StringBuilder();
        checkQueryField(sb, queryFieldList);
        sb.append(tableName);

        return sb.toString();
    }

    /**
     * 获取一个带条件查询的语句 条件全部使用选定的separate连接
     *
     * exp：
     *  1 当separate为AND、queryFieldList为空的时候
     *      SELECT * FROM tableName WHERE key1 = ? AND key2 = ? ... AND keyn = ?
     *
     *  2 当为OR的时候、queryFieldList为空的时候
     *      SELECT * FROM tableName WHERE key1 = ? OR key2 = ? ... OR keyn = ?
     *
     *  3 queryFieldList不为空
     *      略
     *
     * @param tableName      the table name
     * @param keySet         the key set
     * @param separateMark       the separateMark
     * @param queryFieldList the query field list
     * @return the string
     */
    public static String getWhereSql(String tableName, Set<String> keySet, String separateMark, ArrayList<String> queryFieldList){
        StringBuilder sb = new StringBuilder();
        checkQueryField(sb, queryFieldList);
        sb.append(tableName)
                .append(getWhereSubStr(keySet,separateMark));

        return sb.toString();
    }

    /**
     * 获取一个带模糊查询的语句 条件值全一律用%包着 条件全部使用选定的separateMark连接
     *
     * exp：
     *  1 当separate为AND、queryFieldList为空的时候
     *      SELECT * FROM tableName WHERE key1 LIKE '%value1%' AND key2 LIKE '%value2%' ... AND keyn LIKE '%valuen%'
     *
     *  2 separate为OR、queryFieldList不为空
     *      略
     *
     * @param tableName      the table name
     * @param conditionMap   the condition map
     * @param separateMark   the separateMark
     * @param queryFieldList the query field list
     * @return the string
     */
    public static String getWhereLikeSql(String tableName, HashMap<String,Object> conditionMap, String separateMark, ArrayList<String> queryFieldList){
        StringBuilder sb = new StringBuilder();
        checkQueryField(sb, queryFieldList);
        sb.append(tableName)
                .append(getWhereLikeSubStr(conditionMap,separateMark));

        return sb.toString();
    }

    /**
     * 检查queryFieldList是否为空 来构造tableName之前的Sql语句
     *
     * @param sb
     * @param queryFieldList
     */
    private static void checkQueryField(StringBuilder sb, ArrayList<String> queryFieldList) {
        if (queryFieldList == null) {
            sb.append(SELECT_ALL_FROM);
        } else {
            sb.append("SELECT");
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
     * @param keySet
     * @param separateMark
     * @return
     */
    private static String getWhereSubStr(Set<String> keySet, String separateMark) {

        StringBuilder sb = new StringBuilder(" WHERE ");

        for (String s : keySet) {
            sb.append(s).append(" = ? ").append(separateMark).append(" ");
        }

        sb.delete(sb.lastIndexOf(separateMark), sb.length()-1);

        return sb.toString();
    }

    /**
     * 生成模糊条件子串 条件使用指定的separateMark连接
     *
     * @param conditionMap
     * @param separateMark
     * @return
     */
    private static String getWhereLikeSubStr(HashMap<String,Object> conditionMap, String separateMark) {

        StringBuilder sb = new StringBuilder(" WHERE ");

        for (Map.Entry<String, Object> stringObjectEntry : conditionMap.entrySet()) {
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
