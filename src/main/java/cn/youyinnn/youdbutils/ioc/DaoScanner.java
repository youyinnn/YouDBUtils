package cn.youyinnn.youdbutils.ioc;

import cn.youyinnn.youdbutils.dao.annotations.Scope;
import cn.youyinnn.youdbutils.interfaces.YouDao;
import cn.youyinnn.youdbutils.utils.ClassUtils;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.Vector;

/**
 * The type Annotation scanner.
 *
 * @description:
 * @author: youyinnn
 * @date: 2017 /9/12
 */
public class DaoScanner {

    private DaoScanner() {}

    /**
     * 扫描给定包名的包下所有的类 把实现了cn.youyinnn.youdbutils.interfaces.YouDao接口的类放进YouDaoIocContainer容器中
     *
     * @param daoPackageNamePrefix the dao package name prefix
     */
    public static void scanPackageForYouDao(String daoPackageNamePrefix)  {

        Set<Class<?>> daoClassSet = ClassUtils.findFileClass(daoPackageNamePrefix);

        for (Class<?> aClass : daoClassSet) {
            Class<?>[] interfaces = aClass.getInterfaces();
            for (Class<?> anInterface : interfaces) {
                if ("cn.youyinnn.youdbutils.interfaces.YouDao".equals(anInterface.getName())) {

                    Scope scope = aClass.getAnnotation(Scope.class);

                    // 单例dao
                    if (scope == null || scope.value().equals(DaoIocBean.SINGLETON)){
                        YouDaoIocContainer.addSingletonYouDao(new DaoIocBean((Class<YouDao>) aClass, DaoIocBean.SINGLETON));
                    } else {
                        YouDaoIocContainer.addPrototypeYouDao(new DaoIocBean((Class<YouDao>) aClass, DaoIocBean.PROTOTYPE));
                    }
                }
            }
        }
    }


    /**
     * Show current project loaded class.
     */
    public static void showCurrentProjectLoadedClass(){
        try {
            Field field = ClassLoader.class.getDeclaredField("classes");

            field.setAccessible(true);

            Vector<Class> classes = (Vector<Class>) field.get(ClassLoader.getSystemClassLoader());

            for (Class aClass : classes) {
                String className = aClass.getName();
                System.out.println(className);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 按照包名过滤当前加载的类
     *
     * @param packageName the package name
     */
    public static void showCurrentProjectLoadedClass(String packageName) {
        try {
            Field field = ClassLoader.class.getDeclaredField("classes");

            field.setAccessible(true);

            Vector<Class> classes = (Vector<Class>) field.get(ClassLoader.getSystemClassLoader());

            for (Class aClass : classes) {
                String className = aClass.getName();

                if (className.contains(packageName)) {
                    System.out.println(className);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
