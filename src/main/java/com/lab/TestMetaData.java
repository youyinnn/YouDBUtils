package com.lab;

import com.github.youyinnn.youdbutils.YouDbManager;
import com.github.youyinnn.youdbutils.exceptions.NoDataSourceInitException;

import java.sql.*;

/**
 *
 * @author youyinnn
 *
 */
public class TestMetaData {

    public static void main(String[] args) throws NoDataSourceInitException, SQLException {
        YouDbManager.youDruid.initSQLiteDataSource();
        Connection currentDataSourceConn = YouDbManager.youDruid.getCurrentDataSourceConn();

        DatabaseMetaData databaseMetaData = currentDataSourceConn.getMetaData();

        ResultSet company = databaseMetaData.getColumns(null, null, "company", null);

        while (company.next()) {
            System.out.println(company.getObject("COLUMN_NAME"));
        }

    }

}
