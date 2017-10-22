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

    private HashMap<String,String> fieldMap = new HashMap<>();

    public FieldMap(String modelName, ArrayList<String> modelFieldList, ArrayList<String> tableFieldList) {
        this.modelName = modelName;
        for (int i = 0 ; i < modelFieldList.size() ; ++i) {
            fieldMap.put(modelFieldList.get(i), tableFieldList.get(i));
        }
    }

    public String getTableField(String modelField) {
        return fieldMap.get(modelField);
    }

    @Override
    public String toString() {
        return "FieldMap{" +
                "modelName='" + modelName + '\'' +
                ", fieldMap=" + fieldMap +
                '}';
    }
}
