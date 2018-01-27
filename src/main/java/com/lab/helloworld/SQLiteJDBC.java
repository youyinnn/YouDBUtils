package com.lab.helloworld;

import java.sql.*;

/**
 *
 * @author youyinnn
 *
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
