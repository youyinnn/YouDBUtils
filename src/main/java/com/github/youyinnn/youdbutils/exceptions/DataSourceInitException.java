package com.github.youyinnn.youdbutils.exceptions;

/**
 * 数据连接池的数据源没有初始化.
 *
 * @author youyinnn
 */
public class DataSourceInitException extends Exception {

    public DataSourceInitException(String msg) {
        super(msg);
    }

}
