package com.github.youyinnn.youdbutils.dao.model;

import com.github.youyinnn.youdbutils.druid.ThreadLocalPropContainer;
import com.github.youyinnn.youwebutils.third.ClassUtils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;

/**
 * 提供Model类和其类名对应的Table的扫描方法.
 *
 * 扫描Model类,将其field构成列表,注册进ModelTableMessage类中.
 * 根据提供的Model名Set,扫描数据库,将对应的Table的列信息构成列表,注册进ModelTableMessage类中.
 *
 * @author youyinnn
 */
public class ModelTableScanner {

    private ModelTableScanner(){}

    /**
     * Scan package for model.
     *
     * @param modelPackageNamePrefix the model package name prefix
     */
    public static void scanPackageForModel(String modelPackageNamePrefix) {

        Set<Class<?>> modelClassSet = ClassUtils.findFileClass(modelPackageNamePrefix);

        for (Class<?> aClass : modelClassSet) {
            Field[] declaredFields = aClass.getDeclaredFields();

            ArrayList<String> fieldList = new ArrayList<>();

            for (Field declaredField : declaredFields) {
                fieldList.add(declaredField.getName());
            }

            ModelTableMessage.registerModelFieldMessage(aClass.getSimpleName(),fieldList);
        }
    }

    /**
     * Scan data base for table.
     *
     * @param modelNameSet the model name set
     * @param connection   the connection
     */
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
                ModelTableMessage.registerTableFieldMessage(modelName,fieldList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ThreadLocalPropContainer.release(tableColumn,null,connection);
        }
    }

}
