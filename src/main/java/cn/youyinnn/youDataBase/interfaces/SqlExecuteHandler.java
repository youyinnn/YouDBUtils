package cn.youyinnn.youDataBase.interfaces;

import java.sql.ResultSet;

public interface SqlExecuteHandler {

    ResultSet executeQuery(String sql);
}
