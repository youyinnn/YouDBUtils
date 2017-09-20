package cn.lab.dao;

import cn.youyinnn.youDataBase.SqlExecuteHandler;
import cn.youyinnn.youDataBase.annotation.YouDao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/9/13
 */
public class BDao implements YouDao {

    public void a() throws SQLException {

        String sql = "SELECT * FROM COMPANY ;";

        ResultSet resultSet = SqlExecuteHandler.executeQuery(sql);

        while (resultSet.next()) {

            System.out.println(resultSet.getObject(1)+" "+resultSet.getObject(2));
        }
    }

    public void b() throws SQLException {

        String sql = "SELECT * FROM COMPANY ;";

        ResultSet resultSet = SqlExecuteHandler.executeQuery(sql);

        while (resultSet.next()) {

            System.out.println(resultSet.getObject(1)+" "+resultSet.getObject(2));
        }
    }

    public void c() {
        String sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY)" +
                "VALUES (1,'paul',22,'California',30000) ;";

        System.out.println(SqlExecuteHandler.executeUpdate(sql));
    }

    public void d() {
        String sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY)" +
                "VALUES (2,'Jame',21,'Norway',50000) ;";

        System.out.println(SqlExecuteHandler.executeUpdate(sql));
    }

}
