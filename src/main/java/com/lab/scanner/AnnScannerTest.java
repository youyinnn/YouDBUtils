package com.lab.scanner;

import com.github.youyinnn.youdbutils.YouDbManager;
import com.github.youyinnn.youdbutils.ioc.YouServiceIocContainer;

/**
 *
 * @author youyinnn
 *
 */
public class AnnScannerTest {

    public static void main(String[] args) {

        YouDbManager.youDruid.initSQLiteDataSource();

        YouDbManager.scanPackageForService("com.lab.dao");

        YouServiceIocContainer.showServiceMap();
    }

}
