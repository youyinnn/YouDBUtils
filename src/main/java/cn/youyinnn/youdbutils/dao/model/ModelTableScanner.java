package cn.youyinnn.youdbutils.dao.model;

import cn.youyinnn.youdbutils.druid.ThreadLocalPropContainer;
import cn.youyinnn.youdbutils.utils.ClassUtils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/10/1
 */
public class ModelTableScanner {

    private ModelTableScanner(){}

    public static void scanPackageForModel(String modelPackageNamePrefix) {

        Set<Class<?>> modelClassSet = ClassUtils.findFileClass(modelPackageNamePrefix);

        for (Class<?> aClass : modelClassSet) {
            Field[] declaredFields = aClass.getDeclaredFields();

            ArrayList<String> fieldList = new ArrayList<>();

            for (Field declaredField : declaredFields) {
                fieldList.add(declaredField.getName());
            }

            ModelTableMessage.addModelField(aClass.getSimpleName(),fieldList);
        }
    }

    public static void scanDataBaseForTable(Set<String> modelNameSet,Connection connection) {
        ResultSet tableColumn = null;
        try {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            for (String modelName : modelNameSet) {
                ArrayList<String> fieldList = new ArrayList<>();
                tableColumn = databaseMetaData.getColumns(null,
                        null,modelName,null);

                while (tableColumn.next()) {
                    String columnName = tableColumn.getString("COLUMN_NAME");
                    fieldList.add(columnName);
                }
                ModelTableMessage.addTableField(modelName,fieldList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ThreadLocalPropContainer.release(tableColumn,null,connection);
        }
    }

}
