package com.github.youyinnn.youdbutils.druid;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.github.youyinnn.youdbutils.YouDbManager;
import com.github.youyinnn.youdbutils.exceptions.DataSourceInitException;
import com.github.youyinnn.youwebutils.third.Log4j2Helper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.DocumentException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

/**
 * 在方法级层面上去配置Druid.
 * 暂只支持两种数据源:MySQL和SQLite.
 * 提供:
 * 1.数据源的初始化方法,按照提供路径或者默认路径读取配置文件;
 * 2.提供数据库连接的获取方法;
 * 3.提供Druid持有的Log4j2Filter以及StatFilter两个过滤器的配置;
 * 4.一些相关的零散的信息输出;
 *
 * @author youyinnn
 */
public class YouDruid {

    static {
        try {
            Log4j2Helper.useConfig("youdblog4j2.xml");
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    private static final String             MYSQL_PROPERTIES_FILE       = "conf/mysql.properties";
    private static final String             SQLITE_PROPERTIES_FILE      = "conf/sqlite.properties";
    private static final String             MYSQL_TYPE                  = "mysql";
    private static final String             SQLITE_TYPE                 = "sqlite";

    private static Logger                   druidLog                    = LogManager.getLogger("$db_druid");

    private DruidDataSource                 currentDataSource ;

    private String                          dataSourceName;

    private YouDruid() {}

    /**
     * Gets current data source conn.
     *
     * @return the current data source conn
     * @throws SQLException the sql exception
     */
    public Connection getCurrentDataSourceConn() throws SQLException {
        return currentDataSource.getConnection();
    }

    /**
     * Print data source.
     */
    public void printDataSource() {
        System.out.println("Url:" + currentDataSource.getUrl());
    }

    /**
     * 按照默认的路径初始化mysql数据源
     *
     * @param dataSourceName the data source name
     * @return the you druid
     * @throws DataSourceInitException the data source init exception
     */
    public static YouDruid initMySQLDataSource(String dataSourceName) throws DataSourceInitException {
        return generateDataSource("mysql", null, dataSourceName);
    }


    /**
     * 按照给定的配置文件路径去初始化数据源
     *
     * @param propFilePath   the properties file path
     * @param dataSourceName the data source name
     * @return the you druid
     * @throws DataSourceInitException the data source init exception
     */
    public static YouDruid initMySQLDataSource(String propFilePath, String dataSourceName) throws DataSourceInitException {
        return generateDataSource("mysql", propFilePath, dataSourceName);
    }

    /**
     * Init sq lite data source.
     *
     * @param dataSourceName the data source name
     * @return the you druid
     * @throws DataSourceInitException the data source init exception
     */
    public static YouDruid initSQLiteDataSource(String dataSourceName) throws DataSourceInitException {
        return generateDataSource("sqlite", null, dataSourceName);
    }

    /**
     * Init sq lite data source.
     *
     * @param propFilePath   the properties file path
     * @param dataSourceName the data source name
     * @return the you druid
     * @throws DataSourceInitException the data source init exception
     */
    public static YouDruid initSQLiteDataSource(String propFilePath, String dataSourceName) throws DataSourceInitException {
        return generateDataSource("sqlite", propFilePath, dataSourceName);
    }


    private static YouDruid generateDataSource(String dataSourceType, String propertiesFile, String dataSourceName) throws DataSourceInitException {
        YouDruid youDruid = new YouDruid();
        if (dataSourceName == null || dataSourceName.length() == 0) {
            throw new DataSourceInitException("必须指定数据源名!");
        } else {
            youDruid.dataSourceName = dataSourceName;
            Properties properties = new Properties();
            if (propertiesFile == null){
                if (MYSQL_TYPE.equals(dataSourceType)){
                    propertiesFile = MYSQL_PROPERTIES_FILE;
                } else if (SQLITE_TYPE.equals(dataSourceType)){
                    propertiesFile = SQLITE_PROPERTIES_FILE;
                }
            }

            InputStream inputStream = YouDruid.class.getClassLoader().getResourceAsStream(propertiesFile);

            if (inputStream != null){
                try {
                    properties.load(inputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (MYSQL_TYPE.equals(dataSourceType)){
                        youDruid.currentDataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
                    } else if (SQLITE_TYPE.equals(dataSourceType)){
                        youDruid.currentDataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    youDruid.currentDataSource.init();
                    if (YouDbManager.isEmbeddedLogEnabled(dataSourceName)) {
                        druidLog.info("数据源初始化成功, Url:{}", youDruid.currentDataSource.getUrl());
                    }
                } catch (SQLException e) {
                    if (YouDbManager.isEmbeddedLogEnabled(dataSourceName)) {
                        druidLog.error("数据源初始化失败, Url:{}", youDruid.currentDataSource.getUrl());
                    }
                    System.exit(0);
                }
            } else {
                try {
                    throw new DataSourceInitException("路径["+propertiesFile+"]下没有数据源配置文件可加载！");
                } catch (DataSourceInitException e) {
                    e.printStackTrace();
                }
            }
        }
        return youDruid;
    }

    /**
     * Is init boolean.
     *
     * @return the boolean
     */
    public boolean isInit(){
        return currentDataSource.isInited();
    }

    /**
     * Stat on by filter.
     */
    public void statOnByFilter(){
        addFilters("stat");
    }

    /**
     * Log 4 j on by filter.
     */
    public void log4jOnByFilter(){
        addFilters("log4j");
    }

    /**
     * Log 4 j 2 on by filter.
     */
    public void log4j2OnByFilter(){
        addFilters("log4j2");
    }

    /**
     * Wall on by filter.
     */
    public void wallOnByFilter(){
        addFilters("wall");
    }

    private void addFilters(String filterName){
        try {
            currentDataSource.addFilters(filterName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set proxy filters.
     *
     * @param filter the filter
     */
    public void setProxyFilters(Filter filter){
        currentDataSource.setProxyFilters(new ArrayList<>(Collections.singletonList(filter)));
    }

    /**
     * 打开监控数据输出到日志中
     * 特别需要在log4j2设置一个logger来定制化你的日志输出
     *
     * @param logStatsMillis the log stats millis
     */
    public void setTimeBetweenLogStatsMillis(long logStatsMillis){
        currentDataSource.setTimeBetweenLogStatsMillis(logStatsMillis);
    }

    /**
     * Gets data source name.
     *
     * @return the data source name
     */
    public String getDataSourceName() {
        return dataSourceName;
    }

    /**
     * Show proxy filters.
     */
    public void showProxyFilters(){
        System.out.println(currentDataSource.getProxyFilters());
    }

}
