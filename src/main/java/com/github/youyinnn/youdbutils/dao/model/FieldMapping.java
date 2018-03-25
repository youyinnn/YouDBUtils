package com.github.youyinnn.youdbutils.dao.model;

import com.github.youyinnn.youwebutils.third.DbUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 存储将model的field信息和Table的column信息之间的映射信息.
 * 并且提供:
 *  一个获取映射值的方法.
 *  一个检查modelField是否需要映射的方法.
 *
 * @author youyinnn
 */
public class FieldMapping {

    private String modelName;

    /**
     * 存储字段Mapping,键是modelField,值是tableField
     */
    private HashMap<String,String> fieldMapping = new HashMap<>();

    /**
     * 存储modelField和一个布尔值,布尔值决定我们使用modelField的时候,是否需要映射成tableField
     * 即如果modelField和tableField的小写化是一样的值的时候,则不需要映射,否则映射成tableField
     */
    private HashMap<String,Boolean> fieldMappingCheck = new HashMap<>();

    /**
     * 传入model名,modelField的list,和tableField的list
     *
     * @param modelName      the model name
     * @param modelFieldList the model field list
     * @param tableFieldList the table field list
     */
    FieldMapping(String modelName, ArrayList<String> modelFieldList, ArrayList<String> tableFieldList) {
        this.modelName = modelName;
        for (String modelField : modelFieldList) {
            int index = tableFieldList.indexOf(modelField);
            if (index == -1) {
                index = tableFieldList.indexOf(DbUtils.turnToAlibabaDataBaseNamingRules(modelField));
            }
            String tableField = tableFieldList.get(index);
            fieldMappingCheck.put(modelField, !modelField.equals(tableField));
            fieldMapping.put(modelField, tableField);
        }
    }

    /**
     * 返回modelField映射的tableField
     *
     * @param modelField the model field
     * @return the table field
     */
    public String getTableField(String modelField) {
        return fieldMapping.get(modelField);
    }

    /**
     * 检查传入的modelField是否需要映射成tableField
     *
     * @param modelField the model field
     * @return the boolean
     * @throws NoSuchFieldException the no such field exception
     */
    public boolean needToReplace(String modelField) throws NoSuchFieldException {
        Boolean check = fieldMappingCheck.get(modelField);
        if (check != null) {
            return check;
        } else {
            throw new NoSuchFieldException("No such field "+modelField+" in ["+modelName+"]");
        }
    }

    @Override
    public String toString() {
        return "FieldMapping{" +
                "modelName='" + modelName + '\'' +
                ", fieldMapping=" + fieldMapping +
                ", fieldMappingCheck=" + fieldMappingCheck +
                '}';
    }
}
