package cn.lab.dao;

import cn.youyinnn.youDataBase.SqlExecuteHandler;
import cn.youyinnn.youDataBase.annotation.Dao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/9/13
 */
public class BDao implements Dao {

    public void a() throws SQLException {

        String sql = "SELECT * FROM COMPANY ;";

        ResultSet resultSet = SqlExecuteHandler.executeQuery(sql);

        while (resultSet.next()) {

            System.out.println(resultSet.getObject(1)+" "+resultSet.getObject(2));
        }
    }

    public void b() throws SQLException {

        String sql = "SELECT * FROM COMPANY ;";

        SqlExecuteHandler.executeQuery(sql);

        ResultSet resultSet = SqlExecuteHandler.executeQuery(sql);

        while (resultSet.next()) {

            System.out.println(resultSet.getObject(1)+" "+resultSet.getObject(2));
        }
    }

}
