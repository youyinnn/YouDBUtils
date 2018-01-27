package com.github.youyinnn.youdbutils.exceptions;

/**
 * 数据连接池的数据源没有初始化.
 *
 * @author youyinnn
 */
public class NoDataSourceInitException extends Exception {

    public NoDataSourceInitException(String msg) {
        super(msg);
    }

}
