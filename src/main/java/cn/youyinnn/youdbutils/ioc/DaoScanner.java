package cn.youyinnn.youdbutils.ioc;

import cn.youyinnn.youdbutils.dao.annotations.Scope;
import cn.youyinnn.youdbutils.dao.YouDao;
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

    public static void scanPackageForYouDao(String daoPackageNamePrefix)  {

        Set<Class<?>> daoClassSet = ClassUtils.findFileClass(daoPackageNamePrefix);

        for (Class<?> aClass : daoClassSet) {
            Class<?> superclass = aClass.getSuperclass();
            if ("YouDao".equals(superclass.getSimpleName())){
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
