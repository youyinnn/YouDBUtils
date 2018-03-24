package com.github.youyinnn.youdbutils.druid;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Log4j2Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.github.youyinnn.youdbutils.druid.filter.YouLog4j2FilterConfig;
import com.github.youyinnn.youdbutils.druid.filter.YouStatFilterConfig;
import com.github.youyinnn.youdbutils.exceptions.DataSourceInitException;
import com.github.youyinnn.youwebutils.third.Log4j2Helper;
import org.apache.logging.log4j.Logger;
import org.dom4j.DocumentException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 在方法级层面上去配置Druid.
 * 暂只支持两种数据源:MySQL和SQLite.
 * 提供:
 * 1.数据源的初始化方法,按照提供路径或者默认路径读取配置文件;
 * 2.提供数据库连接的获取方法;
 * 3.一些相关的零散的信息输出;
 *
 * @author youyinnn
 */
public class YouDruid {

    static {
        try {
            Log4j2Helper.useConfig("$dbconf/$youdblog4j2.xml");
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    private static final String             MYSQL_PROPERTIES_FILE       = "conf/mysql.properties";
    private static final String             SQLITE_PROPERTIES_FILE      = "conf/sqlite.properties";
    private static final String             MYSQL_TYPE                  = "mysql";
    private static final String             SQLITE_TYPE                 = "sqlite";

    private DruidDataSource                 currentDataSource ;

    private String                          dataSourceName;

    private boolean                         embeddedLogEnable           = false;

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
        System.out.println("Url:" + currentDataSource.getUrl() + "\r\n" + "DataSourceName:" + getDataSourceName());
    }

    /**
     * 按照默认的路径初始化mysql数据源
     *
     * @param dataSourceName the data source name
     * @return the you druid
     * @throws DataSourceInitException the data source init exception
     */
    public static YouDruid initMySQLDataSource(String dataSourceName) throws DataSourceInitException {
        return generateDataSource("mysql", null, dataSourceName, false, null, null);
    }

    public static YouDruid initMySQLDataSource(String dataSourceName, boolean embeddedLogEnable) throws DataSourceInitException {
        return generateDataSource("mysql", null, dataSourceName, embeddedLogEnable, null, null);
    }

    public static YouDruid initMySQLDataSource(String dataSourceName, boolean embeddedLogEnable, YouLog4j2FilterConfig log4j2FilterConfig) throws DataSourceInitException {
        return generateDataSource("mysql", null, dataSourceName, embeddedLogEnable, log4j2FilterConfig, null);
    }

    public static YouDruid initMySQLDataSource(String dataSourceName, boolean embeddedLogEnable, YouStatFilterConfig statFilterConfig) throws DataSourceInitException {
        return generateDataSource("mysql", null, dataSourceName, embeddedLogEnable, null, statFilterConfig);
    }

    public static YouDruid initMySQLDataSource(String dataSourceName, boolean embeddedLogEnable, YouLog4j2FilterConfig log4j2FilterConfig, YouStatFilterConfig statFilterConfig) throws DataSourceInitException {
        return generateDataSource("mysql", null, dataSourceName, embeddedLogEnable, log4j2FilterConfig, statFilterConfig);
    }

    public static YouDruid initMySQLDataSource(String propFilePath, String dataSourceName) throws DataSourceInitException {
        return generateDataSource("mysql", propFilePath, dataSourceName, false, null, null);
    }

    public static YouDruid initMySQLDataSource(String propFilePath, String dataSourceName, boolean embeddedLogEnable) throws DataSourceInitException {
        return generateDataSource("mysql", propFilePath, dataSourceName, embeddedLogEnable, null, null);
    }

    public static YouDruid initMySQLDataSource(String propFilePath, String dataSourceName, boolean embeddedLogEnable, YouLog4j2FilterConfig log4j2FilterConfig) throws DataSourceInitException {
        return generateDataSource("mysql", propFilePath, dataSourceName, embeddedLogEnable, log4j2FilterConfig, null);
    }

    public static YouDruid initMySQLDataSource(String propFilePath, String dataSourceName, boolean embeddedLogEnable, YouStatFilterConfig statFilterConfig) throws DataSourceInitException {
        return generateDataSource("mysql", propFilePath, dataSourceName, embeddedLogEnable, null, statFilterConfig);
    }

    public static YouDruid initMySQLDataSource(String propFilePath, String dataSourceName, boolean embeddedLogEnable, YouLog4j2FilterConfig log4j2FilterConfig, YouStatFilterConfig statFilterConfig) throws DataSourceInitException {
        return generateDataSource("mysql", propFilePath, dataSourceName, embeddedLogEnable, log4j2FilterConfig, statFilterConfig);
    }

    public static YouDruid initSQLiteDataSource(String dataSourceName) throws DataSourceInitException {
        return generateDataSource("sqlite", null, dataSourceName, false, null, null);
    }

    public static YouDruid initSQLiteDataSource(String dataSourceName, boolean embeddedLogEnable) throws DataSourceInitException {
        return generateDataSource("sqlite", null, dataSourceName, embeddedLogEnable, null, null);
    }

    public static YouDruid initSQLiteDataSource(String dataSourceName, boolean embeddedLogEnable, YouLog4j2FilterConfig log4j2FilterConfig) throws DataSourceInitException {
        return generateDataSource("sqlite", null, dataSourceName, embeddedLogEnable, log4j2FilterConfig, null);
    }

    public static YouDruid initSQLiteDataSource(String dataSourceName, boolean embeddedLogEnable, YouStatFilterConfig statFilterConfig) throws DataSourceInitException {
        return generateDataSource("sqlite", null, dataSourceName, embeddedLogEnable, null, statFilterConfig);
    }

    public static YouDruid initSQLiteDataSource(String dataSourceName, boolean embeddedLogEnable, YouLog4j2FilterConfig log4j2FilterConfig, YouStatFilterConfig statFilterConfig) throws DataSourceInitException {
        return generateDataSource("sqlite", null, dataSourceName, embeddedLogEnable, log4j2FilterConfig, statFilterConfig);
    }

    public static YouDruid initSQLiteDataSource(String propFilePath, String dataSourceName) throws DataSourceInitException {
        return generateDataSource("sqlite", propFilePath, dataSourceName, false, null, null);
    }

    public static YouDruid initSQLiteDataSource(String propFilePath, String dataSourceName, boolean embeddedLogEnable) throws DataSourceInitException {
        return generateDataSource("sqlite", propFilePath, dataSourceName, embeddedLogEnable, null, null);
    }

    public static YouDruid initSQLiteDataSource(String propFilePath, String dataSourceName, boolean embeddedLogEnable, YouLog4j2FilterConfig log4j2FilterConfig) throws DataSourceInitException {
        return generateDataSource("sqlite", propFilePath, dataSourceName, embeddedLogEnable, log4j2FilterConfig, null);
    }

    public static YouDruid initSQLiteDataSource(String propFilePath, String dataSourceName, boolean embeddedLogEnable, YouStatFilterConfig statFilterConfig) throws DataSourceInitException {
        return generateDataSource("sqlite", propFilePath, dataSourceName, embeddedLogEnable, null, statFilterConfig);
    }

    public static YouDruid initSQLiteDataSource(String propFilePath, String dataSourceName, boolean embeddedLogEnable, YouLog4j2FilterConfig log4j2FilterConfig, YouStatFilterConfig statFilterConfig) throws DataSourceInitException {
        return generateDataSource("sqlite", propFilePath, dataSourceName, embeddedLogEnable, log4j2FilterConfig, statFilterConfig);
    }

    private static YouDruid generateDataSource
            (String dataSourceType, String propertiesFile, String dataSourceName, boolean embeddedLogEnable ,
             YouLog4j2FilterConfig log4j2FilterConfig,
             YouStatFilterConfig statFilterConfig) throws DataSourceInitException {
        Logger druidLog = Log4j2Helper.getLogger("$db_druid");
        YouDruid youDruid = new YouDruid();
        youDruid.embeddedLogEnable = embeddedLogEnable;
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
                    youDruid.currentDataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
                    if (MYSQL_TYPE.equals(dataSourceType)){
                        // 默认开启这个监控
                        youDruid.currentDataSource.addFilters("wall");
                    }
                    List<Filter> filters = new ArrayList<>();
                    if (log4j2FilterConfig != null) {
                        Log4j2Filter log4j2Filter = log4j2FilterConfig.configLog4j2Filter(new Log4j2Filter());
                        filters.add(log4j2Filter);
                    }
                    if (statFilterConfig != null) {
                        StatFilter statFilter = statFilterConfig.configStatFilter(new StatFilter());
                        Long timeBetweenLogStatusMillis = statFilterConfig.getTimeBetweenLogStatusMillis();
                        if (timeBetweenLogStatusMillis != null) {
                            youDruid.currentDataSource.setTimeBetweenLogStatsMillis(timeBetweenLogStatusMillis);
                        }
                        filters.add(statFilter);
                    }
                    youDruid.currentDataSource.setProxyFilters(filters);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    youDruid.currentDataSource.init();
                    if (youDruid.embeddedLogEnable) {
                        druidLog.info("数据源初始化成功, Url:{} , DataSourceName: {}.", youDruid.currentDataSource.getUrl().split("\\?")[0], dataSourceName);
                    }
                } catch (SQLException e) {
                    if (youDruid.embeddedLogEnable) {
                        druidLog.error("数据源初始化失败, Url:{} , DataSourceName: {}.", youDruid.currentDataSource.getUrl().split("\\?")[0], dataSourceName);
                    }
                    System.exit(0);
                }
            } else {
                try {
                    throw new DataSourceInitException("Resource路径["+propertiesFile+"]下没有数据源配置文件可加载！");
                } catch (DataSourceInitException e) {
                    e.printStackTrace();
                }
            }
        }
        return youDruid;
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
     * Is embedded log enable boolean.
     *
     * @return the boolean
     */
    public boolean isEmbeddedLogEnable() {
        return embeddedLogEnable;
    }

}
