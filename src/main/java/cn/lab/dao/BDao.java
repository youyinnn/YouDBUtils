package cn.lab.dao;

import cn.lab.model.Company;
import cn.youyinnn.youDataBase.SqlExecuteHandler;
import cn.youyinnn.youDataBase.annotations.Transaction;
import cn.youyinnn.youDataBase.interfaces.YouDao;

import java.util.ArrayList;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/9/13
 */
//@Scope
public class BDao implements YouDao {

    private SqlExecuteHandler<Company> sqlExecuteHandler = new SqlExecuteHandler<>();

    public void a() {

        String sql = "SELECT * FROM COMPANY ;";

        ArrayList<Company> arrayList = sqlExecuteHandler.executeStatementQuery(Company.class, sql);

        for (Company company : arrayList) {
            System.out.println(company);
        }
    }

    @Transaction
    public void b() {

        String sql = "SELECT * FROM COMPANY ;";

        ArrayList arrayList = sqlExecuteHandler.executeStatementQuery(Company.class, sql);
    }

    @Transaction
    public void c () {
        String sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY)" +
                "VALUES (1,'paul',22,'California',30000) ;";


        String sql1 = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY)" +
                "VALUES (2,'Jame',21,'Norway',50000) ;";


        System.out.println(sqlExecuteHandler.executeStatementUpdate(sql));

        System.out.println(sqlExecuteHandler.executeStatementUpdate(sql1));
    }

    @Transaction
    public void d (){
        String sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY)" +
                "VALUES (2,'Jame',21,'Norway',50000) ;";

        System.out.println(sqlExecuteHandler.executeStatementUpdate(sql));
    }

}
