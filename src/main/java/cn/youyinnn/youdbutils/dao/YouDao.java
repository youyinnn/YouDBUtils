package cn.youyinnn.youdbutils.dao;

import cn.youyinnn.youdbutils.dao.model.ModelHandler;
import cn.youyinnn.youdbutils.dao.model.ModelResultFactory;

import java.lang.reflect.ParameterizedType;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/10/16
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
