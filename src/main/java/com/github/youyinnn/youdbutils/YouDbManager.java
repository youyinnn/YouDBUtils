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
 *  1.YouDruid类;
 *  2.两个Druid相关的过滤器;
 *
 * 改类提供:
 *  1.数据源相关操作;
 *  2.数据源监控相关操作;
 *  3.Model/Service包下类的扫描方法;
 *  4.一些相关的信息输出;
 *
 * @author youyinnn
 */
public class YouDbManager {

    private static HashMap<String, YouDruid> youDruidMap = new HashMap<>(3);

    private static HashMap<String, Boolean> embeddedLogEnabledMap = new HashMap<>(3);

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

    /**
     * 扫描指定包下的类 生成代理Service 可以在cn.youyinnn.youdbutils.YouServiceIocContainer类中取出
     *
     * 同spring的Ioc容器
     *
     * @param packageName the package name
     */
    public static void scanPackageForService(String packageName, String dataSourceName) throws YouDbManagerException {
        checkDataSourceName(dataSourceName);
        ServiceScanner.scanPackageForService(packageName);
    }

    public static void scanPackageForModel(String packageName, String dataSourceName) throws YouDbManagerException {
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

    public static void enableEmbeddedLog(String dataSourceName) {
        embeddedLogEnabledMap.put(dataSourceName, true);
    }

    public static Boolean isEmbeddedLogEnabled(String dataSourceName) {
        return embeddedLogEnabledMap.get(dataSourceName) == null ? false : embeddedLogEnabledMap.get(dataSourceName);
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

    public static YouDruid getYouDruid(String dataSourceName) {
        return youDruidMap.get(dataSourceName);
    }
}
