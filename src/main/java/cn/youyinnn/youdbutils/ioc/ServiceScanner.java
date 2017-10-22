package cn.youyinnn.youdbutils.ioc;

import cn.youyinnn.youdbutils.ioc.annotations.Scope;
import cn.youyinnn.youdbutils.ioc.annotations.YouService;
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
public class ServiceScanner {

    private ServiceScanner() {}

    public static void scanPackageForService(String servicePackageNamePrefix)  {

        Set<Class<?>> serviceClassSet = ClassUtils.findFileClass(servicePackageNamePrefix);

        for (Class<?> aClass : serviceClassSet) {
            YouService annotation = aClass.getAnnotation(YouService.class);
            if (annotation != null){
                Scope scope = aClass.getAnnotation(Scope.class);

                // 单例service
                if (scope == null || scope.value().equals(ServiceIocBean.SINGLETON)){
                    YouServiceIocContainer.addSingletonYouService(new ServiceIocBean(aClass, ServiceIocBean.SINGLETON));
                } else {
                    YouServiceIocContainer.addPrototypeYouService(new ServiceIocBean(aClass, ServiceIocBean.PROTOTYPE));
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
