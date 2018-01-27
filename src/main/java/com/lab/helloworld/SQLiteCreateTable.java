package com.lab.helloworld;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author youyinnn
 *
 */
public class SQLiteCreateTable {

    public static void main(String[] args) {
        Connection connection = null;

        Statement st = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:test.db");
            connection.setAutoCommit(false);

            st = connection.createStatement();

            String sql = "CREATE TABLE COMPANY " +
                    "(ID             INT     PRIMARY KEY     NOT NULL," +
                    " NAME           TEXT                    NOT NULL, " +
                    " AGE            INT                     NOT NULL, " +
                    " ADDRESS        CHAR(50)," +
                    " SALARY         REAL)";

            st.executeUpdate(sql);
            connection.commit();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + " : " + e.getMessage());
            System.exit(0);
        } finally {
            try {
                assert st != null;
                st.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Table created successfully");
    }

}
