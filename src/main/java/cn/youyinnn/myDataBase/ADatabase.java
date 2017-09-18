package cn.youyinnn.myDataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/9/10
 */
public class ADatabase{

    private String JDBC_URL_PREFIX;
    private Connection connection;
    private String databaseName;
    private boolean autoCommit;


    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public void setAutoCommit(boolean autoCommit) {
        this.autoCommit = autoCommit;
    }

    public ADatabase() {
    }

    public ADatabase(String databaseName){
        this.databaseName = databaseName;
        this.autoCommit = true;
    }

    public ADatabase(String databaseName, boolean autoCommit){
        this.databaseName = databaseName;
        this.autoCommit = autoCommit;
    }

    private Connection getConn(){
        try {
            connection = DriverManager.getConnection(JDBC_URL_PREFIX+databaseName);
            connection.setAutoCommit(autoCommit);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public String toString() {
        return "ADatabase{" +
                "connection=" + connection +
                ", databaseName='" + databaseName + '\'' +
                ", autoCommit=" + autoCommit +
                '}';
    }
}
