package cn.lab.dao;

import cn.youyinnn.IocBean;
import cn.youyinnn.youDataBase.SqlExecuteHandler;
import cn.youyinnn.youDataBase.YouDaoIoCContainer;
import cn.youyinnn.youDataBase.annotations.Scope;
import cn.youyinnn.youDataBase.annotations.Transaction;
import cn.youyinnn.youDataBase.interfaces.YouDao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/9/13
 */
//@Scope
public class BDao implements YouDao {

    public void a() {

        String sql = "SELECT * FROM COMPANY ;";

        ResultSet resultSet = SqlExecuteHandler.executeQuery(sql);

        try {
            while (resultSet.next()) {

                System.out.println(resultSet.getObject(1)+" "+resultSet.getObject(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Transaction
    public void b() {

        String sql = "SELECT * FROM COMPANY ;";

        ResultSet resultSet = SqlExecuteHandler.executeQuery(sql);

        try {
            while (resultSet.next()) {

                System.out.println(resultSet.getObject(1)+" "+resultSet.getObject(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Transaction
    public void c () {
        String sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY)" +
                "VALUES (1,'paul',22,'California',30000) ;";


        String sql1 = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY)" +
                "VALUES (2,'Jame',21,'Norway',50000) ;";


        System.out.println(SqlExecuteHandler.executeUpdate(sql));

        System.out.println(SqlExecuteHandler.executeUpdate(sql1));
    }

    @Transaction
    public void d (){
        String sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY)" +
                "VALUES (2,'Jame',21,'Norway',50000) ;";

        System.out.println(SqlExecuteHandler.executeUpdate(sql));
    }

}
