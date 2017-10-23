package cn.youyinnn.youdbutils.dao.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/10/22
 */
public class FieldMap {

    private String modelName;

    private HashMap<String,String> lowerCaseFieldMap = new HashMap<>();

    private HashMap<String,Boolean> fieldMapCheck = new HashMap<>();

    public FieldMap(String modelName, ArrayList<String> modelFieldList, ArrayList<String> tableFieldList) {
        this.modelName = modelName;
        for (int i = 0 ; i < modelFieldList.size() ; ++i) {
            String modelField = modelFieldList.get(i).toLowerCase();
            String tableField = tableFieldList.get(i).toLowerCase();
            fieldMapCheck.put(modelField,!modelField.equals(tableField));
            lowerCaseFieldMap.put(modelField, tableField);
        }
    }

    public String getTableField(String modelField) {
        return lowerCaseFieldMap.get(modelField.toLowerCase());
    }

    public boolean needToReplace(String modelField) {
        return fieldMapCheck.get(modelField.toLowerCase());
    }

    @Override
    public String toString() {
        return "FieldMap{" +
                "modelName='" + modelName + '\'' +
                ", lowerCaseFieldMap=" + lowerCaseFieldMap +
                ", fieldMapCheck=" + fieldMapCheck +
                '}';
    }
}
