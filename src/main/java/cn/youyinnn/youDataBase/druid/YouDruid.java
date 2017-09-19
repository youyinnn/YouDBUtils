package cn.youyinnn.youDataBase.druid;

import cn.youyinnn.youDataBase.druid.filter.YouLog4j2Filter;
import cn.youyinnn.youDataBase.druid.filter.YouStatFilter;
import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;

import java.io.IOException;
import java.io.InputStream;
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

    /**
     * The constant instance.
     */
    public static YouDruid                  instance                    = new YouDruid() ;

    private static DruidDataSource          currentDataSource ;
    private static String                   currentDataSourceType ;

    private YouDruid() {}

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
     * 返回一个MySQL数据源的连接池 使用你指定的路径
     *
     * @param propertiesFile the properties file
     * @return the my druid
     */
    public static YouDruid getMySQLDataSource(String propertiesFile) {

        generateDataSource("mysql",propertiesFile);

        return instance;
    }


    public static YouDruid getSQLiteDataSource() {

        generateDataSource("sqlite",null);

        return instance;
    }

    public static YouDruid getSQLiteDataSource(String propertiesFile){

        generateDataSource("sqlite",propertiesFile);

        return instance;
    }

    private static void generateDataSource(String dataSourceType,String propertiesFile)  {

        currentDataSourceType = dataSourceType;

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

    public void statOnByFilter(){
        addFilters("stat");
    }

    public void log4jOnByFilter(){
        addFilters("log4j");
    }

    public void log4j2OnByFilter(){
        addFilters("log4j2");
    }

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

    public void addLog4j2Filter() {
        setProxyFilters(YouLog4j2Filter.getLog4j2Filter());
    }

    public void addStatFilter(){
        setProxyFilters(YouStatFilter.getStatFilter());
    }

    private void setProxyFilters(Filter filter){

        currentDataSource.setProxyFilters(new ArrayList<>(Collections.singletonList(filter)));
    }

    public static void setTimeBetweenLogStatsMillis(long logStatsMillis){
        currentDataSource.setTimeBetweenLogStatsMillis(logStatsMillis);
    }

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
