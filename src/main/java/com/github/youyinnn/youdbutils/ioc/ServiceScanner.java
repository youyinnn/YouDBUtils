package com.github.youyinnn.youdbutils.ioc;

import com.github.youyinnn.youdbutils.YouDbManager;
import com.github.youyinnn.youdbutils.ioc.annotations.YouService;
import com.github.youyinnn.youwebutils.third.ClassUtils;
import com.github.youyinnn.youwebutils.third.Log4j2Helper;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.Vector;

/**
 * The type Annotation scanner.
 *
 *
 * @author youyinnn
 *
 */
public class ServiceScanner {

    private ServiceScanner() {}

    public static void scanPackageForService(String servicePackageNamePrefix, String dataSourceName)  {

        Set<Class<?>> serviceClassSet = ClassUtils.findFileClass(servicePackageNamePrefix);
        if (YouDbManager.isYouDruidLogEnable(dataSourceName)) {
            Log4j2Helper.getLogger("$db_scanner").
                    info("数据源: \"{}\" 所持有的Service类扫描结果为: {}.", dataSourceName, serviceClassSet);
        }
        for (Class<?> aClass : serviceClassSet) {
            YouService annotation = aClass.getAnnotation(YouService.class);
            if (annotation != null){
                YouServiceIocContainer.registerYouService(aClass);
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
