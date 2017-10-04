package cn.youyinnn.youDataBase;


import cn.youyinnn.youDataBase.utils.ReflectionUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/10/3
 */
public class ModelResultFactory {

    public static ArrayList getResultModelList(ResultSet result, Class modelClass) {

        ArrayList resultModelList = new ArrayList();
        ArrayList<String> fieldList = ModelMessage.getFieldList(modelClass.getSimpleName());

        try {
            while (result.next()) {
                Object instance = modelClass.newInstance();
                for (String field : fieldList) {
                    boolean hasColumn = true;
                    try {
                        result.findColumn(field);
                    } catch (SQLException ignore){
                        hasColumn = false;
                    }
                    if (hasColumn){
                        ReflectionUtils.setFieldValue(instance,field,result.getObject(field));
                    }
                }
                resultModelList.add(instance);
            }
        } catch (SQLException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        return resultModelList;
    }

}
