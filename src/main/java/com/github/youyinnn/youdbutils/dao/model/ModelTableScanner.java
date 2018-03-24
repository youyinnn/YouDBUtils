package com.github.youyinnn.youdbutils.dao.model;

import com.github.youyinnn.youdbutils.YouDbManager;
import com.github.youyinnn.youdbutils.druid.ThreadLocalPropContainer;
import com.github.youyinnn.youwebutils.third.ClassUtils;
import com.github.youyinnn.youwebutils.third.DbMetaDataUtils;
import com.github.youyinnn.youwebutils.third.Log4j2Helper;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
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
            Log4j2Helper.getLogger("$db_scanner")
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
        Logger logger = Log4j2Helper.getLogger("$db_scanner");
        ResultSet tableColumn = null;
        try {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            for (String modelName : modelNameSet) {
                checkTableAndTryToCreate(modelName, dataSourceName, connection, logger);
                ArrayList<String> fieldList = new ArrayList<>();
                tableColumn = databaseMetaData.getColumns(null,
                        null,modelName,null);

                while (tableColumn.next()) {
                    String columnName = tableColumn.getString("COLUMN_NAME");
                    fieldList.add(columnName);
                }
                ModelTableMessage.registerTableFieldMessage(modelName,fieldList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ThreadLocalPropContainer.release(tableColumn,null,connection);
        }
    }

    private static void checkTableAndTryToCreate(String tableName, String dataSourceName, Connection connection, Logger logger) throws Exception {
        if (!DbMetaDataUtils.isTableExist(connection, tableName)) {
            if (YouDbManager.isYouDruidLogEnable(dataSourceName)) {
                logger.info("扫描数据源:\"{}\", 没有表:\"{}\", 尝试索引配置好的初始化SQL文件.", dataSourceName, tableName);
            }
            String dataSourceMappingInitSql = YouDbManager.getDataSourceMappingInitSql(dataSourceName);
            if (dataSourceMappingInitSql != null) {
                if (YouDbManager.isYouDruidLogEnable(dataSourceName)) {
                    logger.info("正在使用配置的初始化SQL文件, 并执行文件....");
                }
                connection.createStatement().execute(dataSourceMappingInitSql);
                if (DbMetaDataUtils.isTableExist(connection, tableName)) {
                    if (YouDbManager.isYouDruidLogEnable(dataSourceName)) {
                        logger.info("执行初始化SQL文件结果正确, 数据源中已存在表:{}.", tableName);
                    }
                } else {
                    if (YouDbManager.isYouDruidLogEnable(dataSourceName)) {
                        logger.error("执行始化SQL文件结果错误, 请检查用于初始化的SQL文件, 程序终止.");
                    }
                    System.exit(0);
                }
            } else {
                if (YouDbManager.isYouDruidLogEnable(dataSourceName)) {
                    logger.error("扫描表时终止,数据源:\"{}\"中并没有表:\"{}\",也没有设置默认的初始化SQL文件!", dataSourceName, tableName);
                }
                System.exit(0);
            }
        } else {
            if (YouDbManager.isYouDruidLogEnable(dataSourceName)) {
                logger.info("数据源: \"{}\" 库中所包含的表有: \"{}\"", dataSourceName, tableName);
            }
        }
    }
}
