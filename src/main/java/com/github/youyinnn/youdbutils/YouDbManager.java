package com.github.youyinnn.youdbutils;

import com.github.youyinnn.youdbutils.dao.model.ModelTableMessage;
import com.github.youyinnn.youdbutils.dao.model.ModelTableScanner;
import com.github.youyinnn.youdbutils.druid.YouDruid;
import com.github.youyinnn.youdbutils.exceptions.YouDbManagerException;
import com.github.youyinnn.youdbutils.ioc.ServiceScanner;
import com.github.youyinnn.youdbutils.ioc.YouServiceIocContainer;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 整合整个YouDBUtils的Feature的类.
 * 该类持有:
 *  1. 按照数据源名称为key的YouDruid为value的map对象
 *  2. 以数据源名词为key, 该数据源管理的Model类集合为value的map对象
 *
 * 改类提供:
 *  1.数据源的注册
 *  2.model类和service类的扫描方法
 *  3.按照model名索引管理该model类对应的YouDruid对象
 *
 * @author youyinnn
 */
public class YouDbManager {

    private static HashMap<String, YouDruid> youDruidMap = new HashMap<>(3);

    private static HashMap<String, Set<String>> dataSourceMappingModels = new HashMap<>(3);

    public static String getModelMappingDataSourceName(String modelName) {
        for (Map.Entry<String, Set<String>> entry : dataSourceMappingModels.entrySet()) {
            for (String name : entry.getValue()) {
                if (name.equalsIgnoreCase(modelName)) {
                    return entry.getKey();
                }
            }
        }
        return null;
    }

    public static YouDruid youDruid(String dataSourceName) {
        return youDruidMap.get(dataSourceName);
    }

    public static void checkDataSourceName(String dataSourceName) throws YouDbManagerException {
        if (!youDruidMap.containsKey(dataSourceName)) {
            throw new YouDbManagerException("没有该名称的数据源在YouDbManager中注册过!");
        }
    }

    public static void signInYouDruid(YouDruid youDruid) {
        youDruidMap.put(youDruid.getDataSourceName(), youDruid);
    }

    public static void scanPackageForModelAndService(String modelPackagePath, String servicePackagePath, String dataSourceName) throws YouDbManagerException {
        scanPackageForModel(modelPackagePath, dataSourceName);
        scanPackageForService(servicePackagePath, dataSourceName);
    }

    private static void scanPackageForService(String packageName, String dataSourceName) throws YouDbManagerException {
        checkDataSourceName(dataSourceName);
        ServiceScanner.scanPackageForService(packageName);
    }

    private static void scanPackageForModel(String packageName, String dataSourceName) throws YouDbManagerException {
        checkDataSourceName(dataSourceName);
        ModelTableScanner.scanPackageForModel(packageName);
        Set<String> modelNameSet = ModelTableMessage.getAllModelNameSet();
        dataSourceMappingModels.put(dataSourceName, modelNameSet);
        try {
            ModelTableScanner.scanDataBaseForTable(modelNameSet,youDruidMap.get(dataSourceName).getCurrentDataSourceConn());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ModelTableMessage.setFieldMapping();
    }

    public static void showService() {
        YouServiceIocContainer.showServiceMap();
    }

    public static void printAllModelField() {
        System.out.println(ModelTableMessage.getAllModelField());
    }

    public static void printAllTableField() {
        System.out.println(ModelTableMessage.getAllTableField());
    }

    public static void printAllModelTableFieldMapping() {
        System.out.println(ModelTableMessage.getAllModelTableFieldMapping());
    }
}
