package com.lab.tranProxy;

import com.lab.dao.BDao;
import com.github.youyinnn.youdbutils.exceptions.AutowiredException;
import com.github.youyinnn.youdbutils.ioc.proxy.TransactionProxyGenerator;

/**
 *
 * @author youyinnn
 *
 */
public class Main {

    public static void main(String[] args) throws AutowiredException {

        BDao proxyObject = (BDao) TransactionProxyGenerator.getProxyObject(BDao.class);
        BDao proxyObject1 = (BDao) TransactionProxyGenerator.getProxyObject(BDao.class);

        //YouDruid.initSQLiteDataSource();

        //proxyObject.a();
        //proxyObject.b();

        //proxyObject.c();


    }

}
