package cn.youyinnn.youDataBase;

import cn.youyinnn.youDataBase.utils.ClassUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Set;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/10/1
 */
public class ModelScanner {

    private ModelScanner(){}

    public static void scanPackage(String modelPackageNamePrefix) {

        Set<Class<?>> modelClassSet = ClassUtils.findFileClass(modelPackageNamePrefix);

        for (Class<?> aClass : modelClassSet) {
            Field[] declaredFields = aClass.getDeclaredFields();

            ArrayList<String> fieldList = new ArrayList<>();

            for (Field declaredField : declaredFields) {
                fieldList.add(declaredField.getName());
            }

            ModelMessage.addModelFieldMapping(aClass.getSimpleName(),fieldList);
        }
    }

}
