package cn.youyinnn.myDataBase.druid;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Log4j2Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * The type My druid.
 *
 * @description:
 * @author: youyinnn
 * @date: 2017 /9/15
 */
public class MyDruid {

    private static final String         MYSQL_PROPERTIES_FILE       = "conf/mysql.properties";
    private static final String         SQLITE_PROPERTIES_FILE      = "conf/sqlite.properties";

    private ArrayList<Filter>    filters                     = new ArrayList<>();
    /**
     * The constant instance.
     */
    public static MyDruid               instance                    = new MyDruid() ;

    private static DruidDataSource      mysqlDataSource ;
    private static DruidDataSource      sqliteDataSource ;

    private MyDruid() {}

    public void printDataSource() {
        if (mysqlDataSource == null){
            System.out.println(sqliteDataSource);
        }else {
            System.out.println(mysqlDataSource);
        }
    }


    /**
     * 返回一个MySQL数据源的连接池 默认使用"conf/mysql.properties"路径下的配置
     *
     * @return the instance for my sql
     */
    public static MyDruid getInstanceForMySQL() {

        generateDataSource("mysql",null);

        return instance;
    }

    /**
     * 返回一个MySQL数据源的连接池 使用你指定的路径
     *
     * @param propertiesFile the properties file
     * @return the my druid
     */
    public static MyDruid getInstanceForMySQL(String propertiesFile) {

        generateDataSource("mysql",propertiesFile);

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

        InputStream inputStream = MyDruid.class.getClassLoader().getResourceAsStream(propertiesFile);

        if (inputStream != null){
            try {
                properties.load(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (dataSourceType.equals("mysql")){
                    mysqlDataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
                } else {
                    sqliteDataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static MyDruid getInstanceForSQLite() {

        generateDataSource("sqlite",null);

        return instance;
    }

    public static MyDruid getInstanceForSQLite(String propertiesFile){

        generateDataSource("sqlite",propertiesFile);

        return instance;
    }

    /**
     * Init.
     */
    public void init(){
        try {
            if (mysqlDataSource == null){
                sqliteDataSource.init();
            }else {
                mysqlDataSource.init();
            }
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
        if (mysqlDataSource == null){
            return sqliteDataSource.isInited();
        }else {
            return mysqlDataSource.isInited();
        }
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
        if (mysqlDataSource == null) {
            try {
                sqliteDataSource.addFilters(filterName);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                mysqlDataSource.addFilters(filterName);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void setLog4j2Filter(Log4j2Filter log4j2Filter) {

        filters.add(log4j2Filter);
        setProxyFilters(filters);
    }

    private void setProxyFilters(ArrayList<Filter> filters){
        if (mysqlDataSource == null){
            sqliteDataSource.setProxyFilters(filters);
        } else {
            mysqlDataSource.setProxyFilters(filters);
        }
    }

    public void showProxyFilters(){
        if (mysqlDataSource == null) {
            System.out.println(sqliteDataSource.getProxyFilters());
        } else {
            System.out.println(mysqlDataSource.getProxyFilters());
        }
    }

    /**
     * Gets conn.
     *
     * @return the conn
     * @throws SQLException the sql exception
     */
    public DruidPooledConnection getConn() throws SQLException {
        if (mysqlDataSource == null){
            return sqliteDataSource.getConnection();
        }else {
            return mysqlDataSource.getConnection();
        }
    }
}
