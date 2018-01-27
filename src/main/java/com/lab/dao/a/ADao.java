package com.lab.dao.a;

import com.lab.model.Company;
import com.github.youyinnn.youdbutils.dao.YouDao;
import com.github.youyinnn.youdbutils.exceptions.NoneffectiveUpdateExecuteException;
import com.github.youyinnn.youdbutils.dao.SqlExecutor;

/**
 *
 * @author youyinnn
 *
 */
public class ADao extends YouDao<Company> {

    private static SqlExecutor sqlExecuteHandler = new SqlExecutor();

    public void aa() throws NoneffectiveUpdateExecuteException {
        String sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY)" +
                "VALUES (1,'paul',22,'California',30000) ;";


        String sql1 = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY)" +
                "VALUES (2,'Jame',21,'Norway',50000) ;";


        System.out.println(sqlExecuteHandler.executeStatementUpdate(sql));

        System.out.println(sqlExecuteHandler.executeStatementUpdate(sql1));
    }

    public void bb() throws NoneffectiveUpdateExecuteException {
        String sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY)" +
                "VALUES (2,'Jame',21,'Norway',50000) ;";

        System.out.println(sqlExecuteHandler.executeStatementUpdate(sql));
    }
}
