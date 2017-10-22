package cn.youyinnn.youdbutils.dao.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/10/1
 */
public class ModelTableMessage {

    private static HashMap<String,ArrayList<String>> modelFieldMap = new HashMap<>();

    private static HashMap<String,ArrayList<String>> tableFieldMap = new HashMap<>();

    public static void addModelFieldMap(String modelClassName, ArrayList<String> fieldList) {
        modelFieldMap.put(modelClassName,fieldList);
    }

    public static void addTableFieldMap(String tableName, ArrayList<String> fieldList) {
        tableFieldMap.put(tableName,fieldList);
    }

    public static HashMap<String, ArrayList<String>> getModelFieldMap() {
        return modelFieldMap;
    }

    public static HashMap<String, ArrayList<String>> getTableFieldMap() {
        return tableFieldMap;
    }

    public static ArrayList<String> getModelFieldList(String modelClassName) {
        return modelFieldMap.get(modelClassName);
    }

    public static Set<String> getModelNameSet() {
        return modelFieldMap.keySet();
    }

    public static ArrayList<String> getTableFieldList(String modelClassName) {
        return tableFieldMap.get(modelClassName);
    }
}
