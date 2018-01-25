package cn.youyinnn.youdbutils.druid;

import cn.youyinnn.youdbutils.exceptions.NoDataSourceInitException;
import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

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
 *  1.数据源的初始化方法,按照提供路径或者默认路径读取配置文件;
 *  2.提供数据库连接的获取方法;
 *  3.提供Druid持有的Log4j2Filter以及StatFilter两个过滤器的配置;
 *  4.一些相关的零散的信息输出;
 *
 * @author youyinnn
 */
public class YouDruid {

    private static final String             MYSQL_PROPERTIES_FILE       = "conf/mysql.properties";
    private static final String             SQLITE_PROPERTIES_FILE      = "conf/sqlite.properties";
    private static final String             MYSQL_TYPE                  = "mysql";
    private static final String             SQLITE_TYPE                 = "sqlite";

    private static DruidDataSource          currentDataSource ;

    public Connection getCurrentDataSourceConn() throws SQLException, NoDataSourceInitException {

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
     * 按照默认的路径初始化mysql数据源
     */
    public void initMySQLDataSource() {
        generateDataSource("mysql",null);
    }


    /**
     * 按照给定的配置文件路径去初始化数据源
     *
     * @param propFilePath the properties file path
     */
    public void initMySQLDataSource(String propFilePath){
        generateDataSource("mysql",propFilePath);
    }

    /**
     * Init sq lite data source.
     */
    public void initSQLiteDataSource() {
        generateDataSource("sqlite",null);
    }

    /**
     * Init sq lite data source.
     *
     * @param propFilePath the properties file path
     */
    public void initSQLiteDataSource(String propFilePath) {
        generateDataSource("sqlite",propFilePath);
    }


    private void generateDataSource(String dataSourceType,String propertiesFile)  {

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
        } else {
            try {
                throw new NoDataSourceInitException("路径["+propertiesFile+"]下没有数据源配置文件可加载！");
            } catch (NoDataSourceInitException e) {
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

    public void setProxyFilters(Filter filter){
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

}
