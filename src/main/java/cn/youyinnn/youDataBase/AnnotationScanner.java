package cn.youyinnn.youDataBase;

import cn.youyinnn.youDataBase.utils.ClassUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;
import java.util.Vector;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/9/12
 */
public class AnnotationScanner {

    private Object proxyObject;

    public AnnotationScanner(String daoPackageNamePrefix){

        Set<Class<?>> fileClass = ClassUtils.findFileClass(daoPackageNamePrefix);

        for (Class<?> aClass : fileClass) {
            Class<?>[] interfaces = aClass.getInterfaces();
            for (Class<?> anInterface : interfaces) {
                if (anInterface.getName().equals("cn.youyinnn.youDataBase.annotation.YouDao")) {
                    System.out.println(Arrays.toString(aClass.getDeclaredMethods()));
                }
            }
        }

    }

    public void showCurrentProjectLoadedClass(){
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

    public void showCurrentProjectLoadedClass(String packageName) {
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

    public void setProxyObject(Object proxyObject) {
        this.proxyObject = proxyObject;
    }

    public Object getProxyObject() {
        return proxyObject;
    }
}
