package com.lab.dao;

import com.github.youyinnn.youdbutils.dao.YouDao;
import com.lab.model.Company;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author youyinnn
 *
 */
public class CDao extends YouDao<Company> {

    public void a() throws SQLException {

        String sql = "SELECT * FROM COMPANY ;";

        ResultSet resultSet = modelHandler.executeStatementQuery(sql);

        ArrayList<Company> arrayList = modelResultFactory.getResultModelList(resultSet);

        for (Company o : arrayList) {
            System.out.println(o);
        }
    }

}
