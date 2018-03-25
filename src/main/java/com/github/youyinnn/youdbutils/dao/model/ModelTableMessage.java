package com.github.youyinnn.youdbutils.dao.model;

import com.github.youyinnn.youdbutils.YouDbManager;
import com.github.youyinnn.youwebutils.third.DbUtils;
import com.github.youyinnn.youwebutils.third.Log4j2Helper;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * 存储系统中所有Model字段和Table列名的映射信息,并提供获取方法.
 * 以Model类名为键,其类中所有字段组成的列表为值,存储所有Model类的该信息至一个map中.
 * 以Table表名为键,其表中所有列名组成的列表为值,存储所有在系统中有该Model名的Table表的该信息至一个map中.
 * 将这两者信息注册为FieldMapping类,其他类获取FieldMapping就从本类提供.
 *
 * @author youyinnn
 */
public class ModelTableMessage {

    /**
     * 里面保存了model名和对应table名的组成的键值对
     */
    private static HashMap<String, String> modelTableNameMapping = new HashMap<>(5);

    /**
     * 里面保存了所有model和其所有model字段组成的列表的键值对
     */
    private static HashMap<String,ArrayList<String>> allModelField = new HashMap<>(5);

    /**
     * 里面保存了所有table和其所有model字段组成的列表的键值对
     */
    private static HashMap<String,ArrayList<String>> allTableField = new HashMap<>(5);

    /**
     * 里面保存了所有的model名和其对应的FieldMapping对象组成的键值对
     */
    private static HashMap<String,FieldMapping> allModelTableFieldMapping = new HashMap<>(5);

    /**
     * 把model和其对应field组成的列表作为键值对保存到allModelField当中
     *
     * @param modelClassName the model class name
     * @param fieldList      the field list
     */
    public static void registerModelFieldMessage(String modelClassName, ArrayList<String> fieldList) {
        allModelField.put(modelClassName,fieldList);
    }

    /**
     * 把table和其对应field组成的列表作为键值对保存到allTableField当中
     *
     * @param tableName the table name
     * @param fieldList the field list
     */
    public static void registerTableFieldMessage(String tableName, ArrayList<String> fieldList) {
        allTableField.put(tableName,fieldList);
    }

    public static void registerModelTableNameMappingMessage(String modelName, String tableName) {
        modelTableNameMapping.put(modelName, tableName);
    }

    /**
     * 获取对应Model类的FieldMapping对象
     *
     * @param modelName the model name
     * @return the field map
     */
    public static FieldMapping getFieldMapping(String modelName) {
        return allModelTableFieldMapping.get(modelName);
    }

    /**
     * 初始化model对应的FieldMapping对象 并添加到allFieldMapping当中
     * @param dataSourceName
     */
    public static void setFieldMapping(String dataSourceName) {
        Logger logger = Log4j2Helper.getLogger("$db_manager");
        Set<String> modelNames = allModelField.keySet();
        for (String modelName : modelNames) {
            ArrayList<String> mFields = allModelField.get(modelName);
            ArrayList<String> tColumns = allTableField.get(modelName);
            for (String mField : mFields) {
                boolean mapping = tColumns.contains(mField) || tColumns.contains(DbUtils.turnToAlibabaDataBaseNamingRules(mField));
                if (!mapping) {
                    if (YouDbManager.isYouDruidLogEnable(dataSourceName)) {
                        logger.error("数据源: \"{}\" 的Model类和数据表的扫描结果有误, 表:{}中不存在{}或者{}列, 程序终止!",
                                dataSourceName, modelName, mField, DbUtils.turnToAlibabaDataBaseNamingRules(mField));
                    }
                    System.exit(0);
                }
            }
            allModelTableFieldMapping.put(modelName,
                    new FieldMapping(modelName,allModelField.get(modelName),allTableField.get(modelName)));
        }
        if (YouDbManager.isYouDruidLogEnable(dataSourceName)) {
            logger.info("数据源: \"{}\" Model和表的映射对应完成!", dataSourceName);
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
     * 返回一个map 里面保存了所有的model名和其对应的FieldMapping对象组成的键值对
     *
     * @return the field mapping
     */
    public static HashMap<String, FieldMapping> getAllModelTableFieldMapping() {
        return allModelTableFieldMapping;
    }

    public static String getTableName(String modelName) {
        return modelTableNameMapping.get(modelName);
    }
}
