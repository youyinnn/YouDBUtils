package com.github.youyinnn.youdbutils.dao;

import com.github.youyinnn.youdbutils.dao.model.ModelHandler;
import com.github.youyinnn.youdbutils.dao.model.ModelResultFactory;

import java.lang.reflect.ParameterizedType;

/**
 * 提供给用户的Dao类继承的类,其中的泛型需要设置为Model.
 * 一旦使用了ModelTableScanner扫描了正确的Model类,注册了Model字段信息和Table列名信息之后.
 * 我们就可以正常使用YouDao提供的ModelHandler以及ModelResultFactory来在model层面上进行DAO操作.
 *
 * 用户只需要继承并且指定Model为泛型就好,前提是正确使用扫描方法.
 *
 * @author youyinnn
 */
public class YouDao<T> {

    protected ModelResultFactory<T> modelResultFactory;

    protected ModelHandler<T> modelHandler;

    protected String modelName;

    private Class<T> modelClass;

    private void setModelClass() {

        Class<?> aClass = this.getClass();

        while (!"YouDao".equals(aClass.getSuperclass().getSimpleName())) {
             aClass = aClass.getSuperclass();
        }

        ParameterizedType genericSuperclass = (ParameterizedType) aClass.getGenericSuperclass();

        modelClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }

    public YouDao() {
        setModelClass();
        modelHandler = new ModelHandler<>(modelClass);
        modelResultFactory = modelHandler.getModelResultFactory();
        modelName = modelClass.getSimpleName();
    }

}
