package cn.lab.helloworld;

import java.sql.Connection;
import java.sql.Statement;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/9/10
 */
public class SQLiteInsert {

    public static void main(String[] args) throws Exception {
        Connection conn1 = MySQLiteUtils.getConn("test.db");

        Statement st1 = conn1.createStatement();

        String sql1 = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY)" +
                "VALUES (1,'paul',22,'California',30000) ;";

        Connection conn2 = MySQLiteUtils.getConn("test.db");

        Statement st2 = conn2.createStatement();

        String sql2 = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY)" +
                "VALUES (2,'Jame',21,'Norway',50000) ;";

        st1.executeUpdate(sql1);
        st2.executeUpdate(sql2);
    }

}
