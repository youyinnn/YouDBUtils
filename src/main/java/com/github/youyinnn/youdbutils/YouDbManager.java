package com.github.youyinnn.youdbutils;

import com.github.youyinnn.youdbutils.dao.model.ModelTableMessage;
import com.github.youyinnn.youdbutils.dao.model.ModelTableScanner;
import com.github.youyinnn.youdbutils.druid.YouDruid;
import com.github.youyinnn.youdbutils.druid.filter.YouLog4j2Filter;
import com.github.youyinnn.youdbutils.druid.filter.YouStatFilter;
import com.github.youyinnn.youdbutils.exceptions.DataSourceInitException;
import com.github.youyinnn.youdbutils.exceptions.Log4j2FilterException;
import com.github.youyinnn.youdbutils.exceptions.YouDbManagerException;
import com.github.youyinnn.youdbutils.ioc.ServiceScanner;
import com.github.youyinnn.youdbutils.ioc.YouServiceIocContainer;

import java.sql.SQLException;
import java.util.HashMap;
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

    private static HashMap<String, Boolean> modelScannedMap = new HashMap<>(3);

    private static HashMap<String, Boolean> embeddedLogEnabledMap = new HashMap<>(3);

    private static HashMap<String, YouLog4j2Filter> youLog4j2FilterMap = new HashMap<>(3);

    private static HashMap<String, YouStatFilter> youStatFilterHashMap = new HashMap<>(3);

    public static YouLog4j2Filter youLog4j2Filter(String dataSourceName) throws Log4j2FilterException, YouDbManagerException {
        checkLog4j2Filter(dataSourceName);
        return youLog4j2FilterMap.get(dataSourceName);
    }

    public static YouStatFilter youStatFilter(String dataSourceName) throws YouDbManagerException {
        checkStatFilter(dataSourceName);
        return youStatFilterHashMap.get(dataSourceName);
    }

    private static void checkStatFilter(String dataSourceName) throws YouDbManagerException {
        if (!youDruidMap.containsKey(dataSourceName)) {
            throw new YouDbManagerException("没有该名名称的数据源在YouDbManager中注册过!");
        } else {
            if (!youStatFilterHashMap.containsKey(dataSourceName)) {
                youStatFilterHashMap.put(dataSourceName, new YouStatFilter());
            }
        }
    }

    private static void checkLog4j2Filter(String dataSourceName) throws Log4j2FilterException, YouDbManagerException {
        if (!youDruidMap.containsKey(dataSourceName)){
            throw new YouDbManagerException("没有该名名称的数据源在YouDbManager中注册过!");
        } else {
            if (!modelScannedMap.get(dataSourceName)) {
                throw new Log4j2FilterException("Log4j2Filter必须在扫描model之前进行注册!");
            }
            if (!youLog4j2FilterMap.containsKey(dataSourceName)) {
                youLog4j2FilterMap.put(dataSourceName, new YouLog4j2Filter());
            }
        }
    }

    public static void signInLog4j2ProxyFilter(String dataSourceName) throws YouDbManagerException {
        checkStatFilter(dataSourceName);
        youDruidMap.get(dataSourceName).setProxyFilters(youLog4j2FilterMap.get(dataSourceName).getLog4j2Filter());
    }

    public static void signInStatFilter(String dataSourceName) throws YouDbManagerException {
        checkStatFilter(dataSourceName);
        youDruidMap.get(dataSourceName).setProxyFilters(youStatFilterHashMap.get(dataSourceName).getStatFilter());
    }

    public static void signInYouDruid(String dataSourceName, YouDruid youDruid) {
        youDruidMap.put(dataSourceName, youDruid);
    }

    /**
     * 扫描指定包下的类 生成代理Service 可以在cn.youyinnn.youdbutils.YouServiceIocContainer类中取出
     *
     * 同spring的Ioc容器
     *
     * @param packageName the package name
     */
    public static void scanPackageForService(String packageName){
        ServiceScanner.scanPackageForService(packageName);
    }

    public static void scanPackageForModel(String packageName, String dataSourceName) {
        ModelTableScanner.scanPackageForModel(packageName);
        Set<String> modelNameSet = ModelTableMessage.getAllModelNameSet();
        try {
            ModelTableScanner.scanDataBaseForTable(modelNameSet,youDruidMap.get(dataSourceName).getCurrentDataSourceConn());
        } catch (SQLException | DataSourceInitException e) {
            e.printStackTrace();
        }
        ModelTableMessage.setFieldMapping();
        modelScannedMap.put(dataSourceName, true);
    }

    public static void enableEmbeddedLog(String dataSourceName) {
        embeddedLogEnabledMap.put(dataSourceName, true);
    }

    public static Boolean isEmbeddedLogEnabled(String dataSourceName) {
        return embeddedLogEnabledMap.get(dataSourceName);
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
