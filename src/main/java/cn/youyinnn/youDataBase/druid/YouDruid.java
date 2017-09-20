package cn.youyinnn.youDataBase.druid;

import cn.youyinnn.youDataBase.druid.exception.NoDataSourceInitException;
import cn.youyinnn.youDataBase.druid.filter.YouLog4j2Filter;
import cn.youyinnn.youDataBase.druid.filter.YouStatFilter;
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

    private static YouDruid                  instance                    = new YouDruid() ;

    private static DruidDataSource          currentDataSource ;

    private YouDruid() {}

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
    public void printDataSource() {
        System.out.println(currentDataSource);
    }


    /**
     * 返回一个MySQL数据源的连接池 默认使用"conf/mysql.properties"路径下的配置
     *
     * @return the instance for my sql
     */
    public static YouDruid getMySQLDataSource() {

        generateDataSource("mysql",null);

        return instance;
    }

    /**
     * 返回一个MySQL数据源的连接池 使用你指定的配置文件路径
     *
     * @param propertiesFile the properties file
     * @return the my druid
     */
    public static YouDruid getMySQLDataSource(String propertiesFile) {

        generateDataSource("mysql",propertiesFile);

        return instance;
    }


    /**
     * 返回一个Sqlite数据源的连接池 默认使用"conf/sqlite.properties"路径下的配置
     *
     * @return the sq lite data source
     */
    public static YouDruid getSQLiteDataSource() {

        generateDataSource("sqlite",null);

        return instance;
    }

    /**
     * 返回Sqlite数据源的连接池 使用你指定的配置文件路径
     *
     * @param propertiesFile the properties file
     * @return the you druid
     */
    public static YouDruid getSQLiteDataSource(String propertiesFile){

        generateDataSource("sqlite",propertiesFile);

        return instance;
    }

    private static void generateDataSource(String dataSourceType,String propertiesFile)  {

        Properties properties = new Properties();
        if (propertiesFile == null){
            if (dataSourceType.equals("mysql")){
                propertiesFile = MYSQL_PROPERTIES_FILE;
            } else {
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
                if (dataSourceType.equals("mysql")){
                    currentDataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
                 } else {
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
    public void init(){
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
     * Add log 4 j 2 filter.
     */
    public void addLog4j2Filter() {
        setProxyFilters(YouLog4j2Filter.getLog4j2Filter());
    }

    /**
     * Add stat filter.
     */
    public void addStatFilter(){
        setProxyFilters(YouStatFilter.getStatFilter());
    }

    private void setProxyFilters(Filter filter){
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
    public void setTimeBetweenLogStatsMillis(long logStatsMillis){
        currentDataSource.setTimeBetweenLogStatsMillis(logStatsMillis);
    }

    /**
     * Show proxy filters.
     */
    public void showProxyFilters(){
        System.out.println(currentDataSource.getProxyFilters());
    }

    /**
     * Gets conn.
     *
     * @return the conn
     * @throws SQLException the sql exception
     */
    public DruidPooledConnection getConn() throws SQLException {
        return currentDataSource.getConnection();
    }
}
