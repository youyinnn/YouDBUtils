package cn.youyinnn.helloworld;

import java.sql.*;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/9/10
 */
public class SQLiteJDBC {

    public static void main(String[] args) {
        Connection connection;

        try {

            connection = DriverManager.getConnection("jdbc:sqlite:test.db");

            System.out.println(connection);
        }catch (Exception e){
            System.err.println(e.getClass().getName() + " : " + e.getMessage());
            System.exit(0);
        }

        System.out.println("Opened database successfully");
    }

}
