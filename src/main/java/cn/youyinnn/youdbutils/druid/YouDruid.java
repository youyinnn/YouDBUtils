package cn.youyinnn.youdbutils.druid;

import cn.youyinnn.youdbutils.ioc.AnnotationScanner;
import cn.youyinnn.youdbutils.druid.exceptions.NoDataSourceInitException;
import cn.youyinnn.youdbutils.druid.filter.YouLog4j2Filter;
import cn.youyinnn.youdbutils.druid.filter.YouStatFilter;
import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

/**
 * The type My druid.
 *
 * @description:
 * @author: youyinnn
 * @date: 2017 /9/15
 */
public class YouDruid {

    private static final String             MYSQL_PROPERTIES_FILE       = "conf/mysql.properties";
    private static final String             SQLITE_PROPERTIES_FILE      = "conf/sqlite.properties";
    private static final String             MYSQL_TYPE                  = "mysql";
    private static final String             SQLITE_TYPE                 = "sqlite";

    private static DruidDataSource          currentDataSource ;

    private YouDruid() {}

    /**
     * Gets current data source.
     *
     * @return the current data source
     */
    public static DruidDataSource getCurrentDataSource() {
        return currentDataSource;
    }

    public static Connection getCurrentDataSourceConn() throws SQLException, NoDataSourceInitException {

        if (currentDataSource == null){
            throw new NoDataSourceInitException("没有初始化数据源！");
        }

        return currentDataSource.getConnection();
    }

    /**
     * Print data source.
     */
    public static void printDataSource() {
        System.out.println(currentDataSource);
    }

    /**
     * 按照默认的路径初始化mysql数据源
     */
    public static void initMySQLDataSource() {
        generateDataSource("mysql",null);
    }


    /**
     * 按照给定的配置文件路径去初始化数据源
     *
     * @param propertiesFilePath the properties file path
     */
    public static void initMySQLDataSource(String propertiesFilePath){
        generateDataSource("mysql",propertiesFilePath);
    }

    /**
     * Init sq lite data source.
     */
    public static void initSQLiteDataSource() {
        generateDataSource("sqlite",null);
    }

    /**
     * Init sq lite data source.
     *
     * @param propertiesFilePath the properties file path
     */
    public static void initSQLiteDataSource(String propertiesFilePath) {
        generateDataSource("sqlite",propertiesFilePath);
    }

    /**
     * 扫描指定包下的类 生成代理Dao 可以在cn.youyinnn.youdbutils.YouDaoIocContainer类中取出
     *
     * 同spring的Ioc容器
     *
     * @param packageName the package name
     */
    public static void scanPackage(String packageName){
        AnnotationScanner.scanPackage(packageName);
    }

    /**
     * 扫描多个包
     *
     * @param packageNames the package names
     */
    public static void scanPackage(ArrayList<String> packageNames){
        for (String packageName : packageNames) {
            AnnotationScanner.scanPackage(packageName);
        }
    }

    private static void generateDataSource(String dataSourceType,String propertiesFile)  {

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
                    currentDataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
                 } else if (SQLITE_TYPE.equals(dataSourceType)){
                    currentDataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Init.
     */
    public static void init(){
        try {
            currentDataSource.init();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Is init boolean.
     *
     * @return the boolean
     */
    public static boolean isInit(){
        return currentDataSource.isInited();
    }

    /**
     * Stat on by filter.
     */
    public static void statOnByFilter(){
        addFilters("stat");
    }

    /**
     * Log 4 j on by filter.
     */
    public static void log4jOnByFilter(){
        addFilters("log4j");
    }

    /**
     * Log 4 j 2 on by filter.
     */
    public static void log4j2OnByFilter(){
        addFilters("log4j2");
    }

    /**
     * Wall on by filter.
     */
    public static void wallOnByFilter(){
        addFilters("wall");
    }

    private static void addFilters(String filterName){
        try {
            currentDataSource.addFilters(filterName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add log 4 j 2 filter.
     */
    public static void addLog4j2Filter() {
        setProxyFilters(YouLog4j2Filter.getLog4j2Filter());
    }

    /**
     * Add stat filter.
     */
    public static void addStatFilter(){
        setProxyFilters(YouStatFilter.getStatFilter());
    }

    private static void setProxyFilters(Filter filter){
        currentDataSource.setProxyFilters(new ArrayList<>(Collections.singletonList(filter)));
    }

    /**
     * 打开监控数据输出到日志中
     *
     * 特别需要在log4j2设置这个logger来定制化你的日志输出：
     *
     *    <Logger name="com.alibaba.druid.pool.DruidDataSourceStatLoggerImpl" level="info" additivity="false">
     *      <AppenderRef ref="..."/>
     *    </Logger>
     *
     * @param logStatsMillis the log stats millis
     */
    public static void setTimeBetweenLogStatsMillis(long logStatsMillis){
        currentDataSource.setTimeBetweenLogStatsMillis(logStatsMillis);
    }

    /**
     * Show proxy filters.
     */
    public static void showProxyFilters(){
        System.out.println(currentDataSource.getProxyFilters());
    }

    /**
     * Gets conn.
     *
     * @return the conn
     * @throws SQLException the sql exceptions
     */
    public static DruidPooledConnection getConn() throws SQLException {
        return currentDataSource.getConnection();
    }
}
