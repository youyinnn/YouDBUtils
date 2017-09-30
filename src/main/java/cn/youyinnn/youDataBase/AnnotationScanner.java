package cn.youyinnn.youDataBase;

import cn.youyinnn.IocBean;
import cn.youyinnn.youDataBase.annotations.Scope;
import cn.youyinnn.youDataBase.interfaces.YouDao;
import cn.youyinnn.youDataBase.utils.ClassUtils;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.Vector;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/9/12
 */
public class AnnotationScanner {

    private AnnotationScanner() {}

    public static void scanPackage(String daoPackageNamePrefix)  {

        Set<Class<?>> fileClass = ClassUtils.findFileClass(daoPackageNamePrefix);

        for (Class<?> aClass : fileClass) {
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
