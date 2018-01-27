package com.lab.dao;

import com.lab.model.Company;
import com.github.youyinnn.youdbutils.dao.YouDao;
import com.github.youyinnn.youdbutils.exceptions.NoneffectiveUpdateExecuteException;
import com.github.youyinnn.youdbutils.utils.YouCollectionsUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author youyinnn
 *
 */
public class BDao extends YouDao<Company> {

    public void a() throws SQLException {

        System.out.println(modelName);

        String sql = "SELECT * FROM COMPANY ;";

        ResultSet resultSet = modelHandler.executeStatementQuery(sql);

        ArrayList<Company> arrayList = modelResultFactory.getResultModelList(resultSet);

        for (Company o : arrayList) {
            System.out.println(o);
        }
    }

    public void b() throws SQLException {

        String sql = "SELECT * FROM COMPANY ;";

        ResultSet resultSet = modelHandler.executeStatementQuery( sql);

        ArrayList<Company> arrayList = modelResultFactory.getResultModelList(resultSet);

        for (Company o : arrayList) {
            System.out.println(o);
        }
    }

    public void c () {
        String sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY)" +
                "VALUES (3,'paul',22,'California',30000) ;";


        String sql1 = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY)" +
                "VALUES (4,'Jame',21,'Norway',50000) ;";


        try {
            System.out.println(modelHandler.executeStatementInsert(sql));
            System.out.println(modelHandler.executeStatementInsert(sql1));
        } catch (NoneffectiveUpdateExecuteException e) {
            e.printStackTrace();
        }

    }

    public void d (){
        String sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY)" +
                "VALUES (3,'Jame',21,'Norway',50000) ;";

        try {
            System.out.println(modelHandler.executeStatementInsert(sql));
        } catch (NoneffectiveUpdateExecuteException e) {
            e.printStackTrace();
        }
    }

    public void e () {
        String sql = "UPDATE company SET name = 'aaa' WHERE id = 55";

        try {
            modelHandler.executeStatementUpdate(sql);
        } catch (NoneffectiveUpdateExecuteException e) {
            e.printStackTrace();
        }
    }

    public void f () {
        ArrayList<Company> all =
                modelHandler.getListForAll(YouCollectionsUtils.getYouArrayList("id", "name","address"));

        for (Company company : all) {
            System.out.println(company);
        }
    }

    public void g(Company company) {
        try {
            modelHandler.saveModel(company);
        } catch (NoneffectiveUpdateExecuteException e) {
            e.printStackTrace();
        }
    }

    public void h(HashMap<String, Object> conditionsMap) {
        ArrayList<Company> listWhereAAndB = modelHandler.getListWhere(conditionsMap, null,"and");

        for (Company company : listWhereAAndB) {
            System.out.println(company);
        }
    }

}
