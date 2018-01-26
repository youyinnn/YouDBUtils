package cn.youyinnn.youdbutils;

import cn.youyinnn.youdbutils.dao.model.ModelTableScanner;
import cn.youyinnn.youdbutils.dao.model.ModelTableMessage;
import cn.youyinnn.youdbutils.druid.YouDruid;
import cn.youyinnn.youdbutils.druid.filter.YouLog4j2Filter;
import cn.youyinnn.youdbutils.druid.filter.YouStatFilter;
import cn.youyinnn.youdbutils.exceptions.NoDataSourceInitException;
import cn.youyinnn.youdbutils.ioc.ServiceScanner;
import cn.youyinnn.youdbutils.ioc.YouServiceIocContainer;

import java.sql.SQLException;
import java.util.Set;

/**
 * 整合整个YouDBUtils的Feature的类.
 * 该类持有:
 *  1.YouDruid类;
 *  2.两个Druid相关的过滤器;
 *
 * 改类提供:
 *  1.数据源相关操作;
 *  2.数据源监控相关操作;
 *  3.Model/Service包下类的扫描方法;
 *  4.一些相关的信息输出;
 *
 * @author youyinnn
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
        ServiceScanner.scanPackageForService(packageName);
    }

    public static void scanPackageForModel(String packageName) {
        ModelTableScanner.scanPackageForModel(packageName);
        Set<String> modelNameSet = ModelTableMessage.getAllModelNameSet();
        try {
            ModelTableScanner.scanDataBaseForTable(modelNameSet,youDruid.getCurrentDataSourceConn());
        } catch (SQLException | NoDataSourceInitException e) {
            e.printStackTrace();
        }
        ModelTableMessage.setFieldMapping();
    }

    public static void showService() {
        YouServiceIocContainer.showServiceMap();
    }

    public static void printAllModelField() {
        System.out.println(ModelTableMessage.getAllModelField());
    }

    public static void printAllTableField() {
        System.out.println(ModelTableMessage.getAllTableField());
    }

    public static void printAllModelTableFieldMapping() {
        System.out.println(ModelTableMessage.getAllModelTableFieldMapping());
    }
}
