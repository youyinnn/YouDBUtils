package com.github.youyinnn.youdbutils.dao.model;

import com.github.youyinnn.youdbutils.YouDbManager;
import com.github.youyinnn.youdbutils.druid.ThreadLocalPropContainer;
import com.github.youyinnn.youwebutils.third.ClassUtils;
import com.github.youyinnn.youwebutils.third.DbUtils;
import com.github.youyinnn.youwebutils.third.Log4j2Helper;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.sql.Connection;
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
     * @param dataSourceName
     */
    public static void scanPackageForModel(String modelPackageNamePrefix, String dataSourceName) {
        Set<Class<?>> modelClassSet = ClassUtils.findFileClass(modelPackageNamePrefix);
        if (YouDbManager.isYouDruidLogEnable(dataSourceName)) {
            Log4j2Helper.getLogger("$db_manager")
                    .info("数据源: \"{}\" 所管理的Model类扫描结果为: {}.", dataSourceName, modelClassSet);
        }
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
     *  @param modelNameSet the model name set
     * @param connection   the connection
     * @param dataSourceName
     */
    public static void scanDataBaseForTable(Set<String> modelNameSet, Connection connection, String dataSourceName) {
        Logger logger = Log4j2Helper.getLogger("$db_manager");
        try {
            ArrayList<String> tablesFromDB = DbUtils.getTablesFromDB(connection);
            if (tablesFromDB.size() != 0) {
                if (YouDbManager.isYouDruidLogEnable(dataSourceName)) {
                    logger.info("数据源: \"{}\" 中所包含的表有: \"{}\".", dataSourceName, tablesFromDB);
                }
            } else {
                if (YouDbManager.isYouDruidLogEnable(dataSourceName)) {
                    logger.info("扫描数据源:\"{}\", 没有任何表, 尝试索引配置好的初始化SQL文件.", dataSourceName);
                }
                checkTableAndTryToCreate(dataSourceName, connection, logger);
            }

            tablesFromDB = DbUtils.getTablesFromDB(connection);
            for (String modelName : modelNameSet) {
                if (!tablesFromDB.contains(modelName.toLowerCase())) {
                    if (YouDbManager.isYouDruidLogEnable(dataSourceName)) {
                        logger.info("数据源: \"{}\" 中没有表:{}.", dataSourceName, modelName);
                    }
                    checkTableAndTryToCreate(dataSourceName, connection, logger);
                }
                ArrayList<String> columnsFromTable = DbUtils.getColumnsFromTable(connection, modelName);
                ModelTableMessage.registerTableFieldMessage(modelName,columnsFromTable);
            }
            tablesFromDB = DbUtils.getTablesFromDB(connection);
            if (YouDbManager.isYouDruidLogEnable(dataSourceName)) {
                logger.info("数据源: \"{}\" 中所包含的表有: \"{}\".", dataSourceName, tablesFromDB);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ThreadLocalPropContainer.release(null,null,connection);
        }
    }

    private static void checkTableAndTryToCreate(String dataSourceName, Connection connection, Logger logger) throws Exception {
        String dataSourceInitSqlFilePath = YouDbManager.getDataSourceInitSqlFilePath(dataSourceName);
        if (dataSourceInitSqlFilePath != null) {
            if (YouDbManager.isYouDruidLogEnable(dataSourceName)) {
                logger.info("正在使用配置的初始化SQL文件, 并执行文件....");
            }
            DbUtils.runSqlScript(connection, dataSourceInitSqlFilePath);
        } else {
            if (YouDbManager.isYouDruidLogEnable(dataSourceName)) {
                logger.error("尝试初始化表时终止, 用户既没有指定用于初始化的SQL文件, 默认资源路径下也没有{}文件!", dataSourceName + "-init.sql");
            }
            System.exit(0);
        }

    }
}
