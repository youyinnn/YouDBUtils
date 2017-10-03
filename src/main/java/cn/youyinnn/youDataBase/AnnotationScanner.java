package cn.youyinnn.youDataBase;

import cn.youyinnn.youDataBase.annotations.Scope;
import cn.youyinnn.youDataBase.interfaces.YouDao;
import cn.youyinnn.youDataBase.utils.ClassUtils;

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
public class AnnotationScanner {

    private AnnotationScanner() {}

    /**
     * 扫描给定包名的包下所有的类 把实现了cn.youyinnn.youDataBase.interfaces.YouDao接口的类放进YouDaoIoCContainer容器中
     *
     * @param daoPackageNamePrefix the dao package name prefix
     */
    public static void scanPackage(String daoPackageNamePrefix)  {

        Set<Class<?>> daoClassSet = ClassUtils.findFileClass(daoPackageNamePrefix);

        for (Class<?> aClass : daoClassSet) {
            Class<?>[] interfaces = aClass.getInterfaces();
            for (Class<?> anInterface : interfaces) {
                if (anInterface.getName().equals("cn.youyinnn.youDataBase.interfaces.YouDao")) {

                    Scope scope = aClass.getAnnotation(Scope.class);

                    // 单例dao
                    if (scope == null || scope.value().equals(IocBean.SINGLETON)){
                        YouDaoIoCContainer.addSingletonYouDao(new IocBean((Class<YouDao>) aClass,IocBean.SINGLETON));
                    } else {
                        YouDaoIoCContainer.addPrototypeYouDao(new IocBean((Class<YouDao>) aClass,IocBean.PROTOTYPE));
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
