package cn.lab.dao;

import cn.lab.model.Company;
import cn.youyinnn.youDBUtils.dao.model.ModelResultFactory;
import cn.youyinnn.youDBUtils.dao.SqlExecuteHandler;
import cn.youyinnn.youDBUtils.dao.annotations.Transaction;
import cn.youyinnn.youDBUtils.interfaces.YouDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/9/13
 */
//@Scope
public class BDao implements YouDao {

    private SqlExecuteHandler<Company> sqlExecuteHandler = new SqlExecuteHandler<>();
    private ModelResultFactory<Company> modelResultFactory = new ModelResultFactory<>();

    public void a() throws SQLException {

        String sql = "SELECT * FROM COMPANY ;";

        ResultSet resultSet = sqlExecuteHandler.executeStatementQuery(sql);

        ArrayList<Company> arrayList = modelResultFactory.getResultModelList(resultSet,Company.class);

        for (Company o : arrayList) {
            System.out.println(o);
        }
    }

    @Transaction
    public void b() throws SQLException {

        String sql = "SELECT * FROM COMPANY ;";

        ResultSet resultSet = sqlExecuteHandler.executeStatementQuery( sql);

        ArrayList<Company> arrayList = modelResultFactory.getResultModelList(resultSet,Company.class);

        for (Company o : arrayList) {
            System.out.println(o);
        }
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
