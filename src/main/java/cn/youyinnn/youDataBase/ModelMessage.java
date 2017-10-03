package cn.youyinnn.youDataBase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/10/1
 */
public class ModelMessage {

    private static HashMap<String,ArrayList<String>> modelFieldMapping = new HashMap<>();

    public static void addModelFieldMapping(String modelClassName, ArrayList<String> fieldList) {
        modelFieldMapping.put(modelClassName,fieldList);
    }

    public static HashMap<String, ArrayList<String>> getModelFieldMapping() {
        return modelFieldMapping;
    }

    public static ArrayList<String> getFieldList(String modelClassName) {
        return modelFieldMapping.get(modelClassName);
    }
}
