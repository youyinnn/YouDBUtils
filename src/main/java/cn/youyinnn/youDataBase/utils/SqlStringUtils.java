package cn.youyinnn.youDataBase.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/10/3
 */
public class SqlStringUtils {

    private static final String SELECT_ALL_FROM = "SELECT * FROM ";

    public static String getSql(String tableName, ArrayList<String> queryFieldList) {
        StringBuilder sb = new StringBuilder();
        checkQueryField(sb, queryFieldList);
        sb.append(tableName);

        return sb.toString();
    }

    public static String getWhereSql(String tableName, Set<String> keySet, String separate, ArrayList<String> queryFieldList){
        StringBuilder sb = new StringBuilder();
        checkQueryField(sb, queryFieldList);
        sb.append(tableName)
                .append(getWhereSubStr(keySet,separate));

        return sb.toString();
    }

    public static String getWhereLikeSql(String tableName, HashMap<String,Object> conditionMap, String separate, ArrayList<String> queryFieldList){
        StringBuilder sb = new StringBuilder();
        checkQueryField(sb, queryFieldList);
        sb.append(tableName)
                .append(getWhereLikeSubStr(conditionMap,separate));

        return sb.toString();
    }

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

    private static String getWhereSubStr(Set<String> keySet, String separate) {

        StringBuilder sb = new StringBuilder(" WHERE ");

        for (String s : keySet) {
            sb.append(s).append(" = ? ").append(separate).append(" ");
        }

        sb.delete(sb.lastIndexOf(separate), sb.length()-1);

        return sb.toString();
    }

    private static String getWhereLikeSubStr(HashMap<String,Object> conditionMap, String separate) {

        StringBuilder sb = new StringBuilder(" WHERE ");

        for (Map.Entry<String, Object> stringObjectEntry : conditionMap.entrySet()) {
            sb.append(stringObjectEntry.getKey())
                    .append(" LIKE ")
                    .append("'%")
                    .append(stringObjectEntry.getValue())
                    .append("%' ")
                    .append(separate)
                    .append(" ");
        }

        sb.delete(sb.lastIndexOf(separate), sb.length()-1);

        return sb.toString();
    }

}
