package cn.youyinnn.myDataBase;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Vector;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/9/12
 */
public class AnnotationScanner {

    private Object proxyObject;

    public AnnotationScanner(){
        //System.out.println("scan");

        try {
            Field field = ClassLoader.class.getDeclaredField("classes");

            field.setAccessible(true);

            Vector<Class> classes = (Vector<Class>) field.get(ClassLoader.getSystemClassLoader());

            for (Class aClass : classes) {
                String className = aClass.getName();

                if (className.contains("cn.youyinnn")) {
                    System.out.println(className+"   impl:");
                    System.out.println(Arrays.toString(aClass.getInterfaces()));
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
