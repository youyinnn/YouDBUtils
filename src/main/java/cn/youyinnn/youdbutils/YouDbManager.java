package cn.youyinnn.youdbutils;

import cn.youyinnn.youdbutils.dao.model.ModelScanner;
import cn.youyinnn.youdbutils.druid.YouDruid;
import cn.youyinnn.youdbutils.druid.filter.YouLog4j2Filter;
import cn.youyinnn.youdbutils.druid.filter.YouStatFilter;
import cn.youyinnn.youdbutils.ioc.ServiceScanner;
import cn.youyinnn.youdbutils.ioc.YouServiceIocContainer;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/10/14
 */
public class YouDbManager {

    public static YouDruid                          youDruid                        = new YouDruid();

    private static YouLog4j2Filter                  youLog4j2Filter ;

    private static YouStatFilter                    youStatFilter ;

    public static YouLog4j2Filter youLog4j2Filter() {
        checkLog4j2Filter();
        return youLog4j2Filter;
    }

    public static YouStatFilter youStatFilter() {
        checkStatFilter();
        return youStatFilter;
    }

    private static void checkLog4j2Filter() {
        if (youLog4j2Filter == null) {
            youLog4j2Filter = new YouLog4j2Filter();
        }
    }

    private static void checkStatFilter() {
        if (youStatFilter == null) {
            youStatFilter = new YouStatFilter();
        }
    }

    public static void signInLog4j2ProxyFilter() {
        checkLog4j2Filter();
        youDruid.setProxyFilters(youLog4j2Filter.getLog4j2Filter());
    }

    public static void signInStatProxyFilter() {
        checkStatFilter();
        youDruid.setProxyFilters(youStatFilter.getStatFilter());
    }

    /**
     * 扫描指定包下的类 生成代理Service 可以在cn.youyinnn.youdbutils.YouServiceIocContainer类中取出
     *
     * 同spring的Ioc容器
     *
     * @param packageName the package name
     */
    public static void scanPackageForService(String packageName){
        ServiceScanner.scanPackage(packageName);
    }

    public static void scanPackageForModel(String packageName) {
        ModelScanner.scanPackage(packageName);
    }

    public static void showDao() {
        YouServiceIocContainer.showServiceMap();
    }
}
