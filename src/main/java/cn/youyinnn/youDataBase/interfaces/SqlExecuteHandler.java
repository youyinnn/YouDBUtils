package cn.youyinnn.youDataBase.interfaces;

import java.sql.ResultSet;
import java.util.List;

public interface SqlExecuteHandler<T> {

    ResultSet executeQuery(String sql);

    int executeUpdate(String sql);

    public List<T> queryList(Class modelClass);


}
