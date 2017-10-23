package cn.youyinnn.youdbutils.utils;

import cn.youyinnn.youdbutils.dao.model.FieldMap;
import cn.youyinnn.youdbutils.dao.model.ModelTableMessage;
import cn.youyinnn.youdbutils.exceptions.YouMapException;

import java.util.*;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/10/22
 */
public class YouCollectionsUtils {

    public static HashMap<String, Object> getYouHashMap(Object ... objects) {
        HashMap<String, Object> youMap = new HashMap<>(10);
        int length = objects.length;
        if (length % 2 != 0) {
            try {
                throw new YouMapException("传入参数数目不是偶数，无法创建完整的键值对。");
            } catch (YouMapException e) {
                e.printStackTrace();
            }
        } else {
            for (int i = 0 ; i < length ; i += 2) {
                if (objects[i] instanceof String){
                    youMap.put(String.valueOf(objects[i]),objects[i+1]);
                } else {
                    try {
                        throw new YouMapException("传入的第["+(i+1)+"]个参数不是String类型，不能作为键。");
                    } catch (YouMapException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return youMap;
    }

    public static ArrayList<String> getYouArrayList(String ... queryFieldList){

        return new ArrayList<>(Arrays.asList(queryFieldList));
    }

    public static ArrayList<String> mappingHandle(String modelName, ArrayList<String> modelField) {

        if (modelField == null) {
            return null;
        }

        FieldMap fieldMap = ModelTableMessage.getFieldMap(modelName);

        if (fieldMap != null) {
            ArrayList<String> result = new ArrayList<>();

            for (String mField : modelField) {
                if (fieldMap.needToReplace(mField)) {
                    result.add(fieldMap.getTableField(mField));
                } else {
                    result.add(mField);
                }
            }

            return result;
        } else {
            return modelField;
        }
    }

    public static ArrayList<String> mappingHandle(String modelName, Set<String> modelField) {
        return mappingHandle(modelName,new ArrayList<>(modelField));
    }

    public static HashMap<String, Object> mappingHandle(String modelName, HashMap<String, Object> modelField){

        if (modelField == null) {
            return null;
        }

        HashMap<String, Object> tableField = new HashMap<>();

        FieldMap fieldMap = ModelTableMessage.getFieldMap(modelName);

        Set<String> mFieldSet = modelField.keySet();

        for (String mField : mFieldSet) {
            Object value = modelField.get(mField);
            if (fieldMap.needToReplace(mField)) {
                tableField.put(fieldMap.getTableField(mField), value);
            } else {
                tableField.put(mField,value);
            }

        }

        return tableField;
    }

}
