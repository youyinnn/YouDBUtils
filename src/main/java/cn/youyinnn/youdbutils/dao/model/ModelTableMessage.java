package cn.youyinnn.youdbutils.dao.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * The type Model table message.
 *
 * @description:
 * @author: youyinnn
 * @date: 2017 /10/1
 */
public class ModelTableMessage {

    /**
     * 里面保存了所有model和其所有model字段组成的列表的键值对
     */
    private static HashMap<String,ArrayList<String>> allModelField = new HashMap<>();

    /**
     * 里面保存了所有table和其所有model字段组成的列表的键值对
     */
    private static HashMap<String,ArrayList<String>> allTableField = new HashMap<>();

    /**
     * 里面保存了所有的model名和其对应的FieldMap对象组成的键值对
     */
    private static HashMap<String,FieldMap> allModelTableFieldMapping = new HashMap<>();

    /**
     * 把model和其对应field组成的列表作为键值对保存到allModelField当中
     *
     * @param modelClassName the model class name
     * @param fieldList      the field list
     */
    public static void addModelField(String modelClassName, ArrayList<String> fieldList) {
        allModelField.put(modelClassName,fieldList);
    }

    /**
     * 把table和其对应field组成的列表作为键值对保存到allTableField当中
     *
     * @param tableName the table name
     * @param fieldList the field list
     */
    public static void addTableField(String tableName, ArrayList<String> fieldList) {
        allTableField.put(tableName,fieldList);
    }

    /**
     * 获取对应Model类的FieldMap对象
     *
     * @param modelName the model name
     * @return the field map
     */
    public static FieldMap getFieldMap(String modelName) {
        return allModelTableFieldMapping.get(modelName);
    }

    /**
     * 初始化model对应的FieldMap对象 并添加到allFieldMapping当中
     */
    public static void setFieldMapping() {
        for (String modelName : allModelField.keySet()) {
            allModelTableFieldMapping.put(modelName,
                    new FieldMap(modelName,allModelField.get(modelName),allTableField.get(modelName)));
        }
    }

    /**
     * 返回一个map 里面保存了所有model和其所有model字段组成的列表的键值对
     *
     * @return the model field
     */
    public static HashMap<String, ArrayList<String>> getAllModelField() {
        return allModelField;
    }

    /**
     * 返回一个map 里面保存了所有table和其所有model字段组成的列表的键值对
     *
     * @return the table field
     */
    public static HashMap<String, ArrayList<String>> getAllTableField() {
        return allTableField;
    }

    /**
     * 获取某个model对应的字段list
     *
     * @param modelClassName the model class name
     * @return the model field list
     */
    public static ArrayList<String> getModelFieldList(String modelClassName) {
        return allModelField.get(modelClassName);
    }

    /**
     * 返回所有的Model名字组成的set集合
     *
     * @return the model field set
     */
    public static Set<String> getAllModelNameSet() {
        return allModelField.keySet();
    }

    /**
     * 返回一个map 里面保存了所有的model名和其对应的FieldMap对象组成的键值对
     *
     * @return the field mapping
     */
    public static HashMap<String, FieldMap> getAllModelTableFieldMapping() {
        return allModelTableFieldMapping;
    }

    public static boolean isModelTableMapping(Class modelClass) {
        return allModelTableFieldMapping.containsKey(modelClass.getSimpleName());
    }
}
