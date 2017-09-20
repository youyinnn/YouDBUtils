package cn.youyinnn.youDataBase;

import cn.youyinnn.youDataBase.druid.YouDruid;
import cn.youyinnn.youDataBase.druid.exception.NoLoadedDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/9/19
 */
public class SqlExecuteHandler {

    private static void checkConnection(){

        ConnectionContainer instance = ConnectionContainer.getInstance();

        if (instance.get() == null){
            try {
                System.out.println("bind conn");
                instance.bind(YouDruid.getCurrentDataSourceConn());
            } catch (SQLException | NoLoadedDataSource e) {
                e.printStackTrace();
            }
        }
    }

    public static ResultSet executeQuery(String sql) {

        checkConnection();

        ResultSet result = null;

        Connection conn = ConnectionContainer.getInstance().get();

        try {
            Statement statement = conn.createStatement();

            result = statement.executeQuery(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
