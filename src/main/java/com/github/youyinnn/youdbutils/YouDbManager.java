package com.github.youyinnn.youdbutils;

import com.github.youyinnn.youdbutils.dao.model.ModelTableMessage;
import com.github.youyinnn.youdbutils.dao.model.ModelTableScanner;
import com.github.youyinnn.youdbutils.druid.YouDruid;
import com.github.youyinnn.youdbutils.exceptions.YouDbManagerException;
import com.github.youyinnn.youdbutils.ioc.ServiceScanner;
import com.github.youyinnn.youdbutils.ioc.YouServiceIocContainer;
import com.github.youyinnn.youwebutils.third.Log4j2Helper;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
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

    private static final Logger logger = Log4j2Helper.getLogger("$db_manager");

    private static HashMap<String, YouDruid> youDruidMap = new HashMap<>(3);

    private static HashMap<String, Set<String>> dataSourceMappingModels = new HashMap<>(3);

    private static HashMap<String, String> dataSourceInitSqlFilePath = new HashMap<>(3);

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
            throw new YouDbManagerException("没有名称为:" + dataSourceName + "的数据源在YouDbManager中注册过!");
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
        ServiceScanner.scanPackageForService(packageName, dataSourceName);
    }

    private static void scanPackageForModel(String packageName, String dataSourceName) throws YouDbManagerException {
        checkDataSourceName(dataSourceName);
        ModelTableScanner.scanPackageForModel(packageName, dataSourceName);
        Set<String> modelNameSet = ModelTableMessage.getAllModelNameSet();
        dataSourceMappingModels.put(dataSourceName, modelNameSet);
        try {
            ModelTableScanner.scanDataBaseForTable(modelNameSet,youDruidMap.get(dataSourceName).getCurrentDataSourceConn(), dataSourceName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ModelTableMessage.setFieldMapping(dataSourceName);
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

    public static boolean setInitSql(String dataSourceName, String initSqlFilePath) throws YouDbManagerException {
        checkDataSourceName(dataSourceName);
        InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream(initSqlFilePath);
        if (resourceAsStream != null) {
            dataSourceInitSqlFilePath.put(dataSourceName, initSqlFilePath);
            return true;
        } else {
            return false;
        }
    }

    public static String getDataSourceInitSqlFilePath(String dataSourceName) throws YouDbManagerException, IOException {
        checkDataSourceName(dataSourceName);
        String initSql = dataSourceInitSqlFilePath.get(dataSourceName);
        if (initSql == null) {
            if (isYouDruidLogEnable(dataSourceName)) {
                logger.info("用户并无配置好的初始化文件, 尝试索引默认的初始化SQL文件:{}", dataSourceName + "-init.sql");
            }
            boolean b = setInitSql(dataSourceName, dataSourceName + "-init.sql");
            if (b) {
                if (isYouDruidLogEnable(dataSourceName)) {
                    logger.info("存在数据库对应的默认的初始化SQL文件:{}, 启用该配置.",dataSourceName + "-init.sql" );
                }
            }
        }
        return dataSourceInitSqlFilePath.get(dataSourceName);
    }

    public static boolean isYouDruidLogEnable(String dataSourceName) {
        return youDruidMap.get(dataSourceName).isEmbeddedLogEnable();
    }
}
