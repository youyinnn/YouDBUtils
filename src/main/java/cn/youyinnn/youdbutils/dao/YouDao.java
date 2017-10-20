package cn.youyinnn.youdbutils.dao;

import cn.youyinnn.youdbutils.dao.model.ModelResultFactory;

import java.lang.reflect.ParameterizedType;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/10/16
 */
public class YouDao<T> {

    protected SqlExecutor sqlExecutor = new SqlExecutor();

    protected ModelResultFactory<T> modelResultFactory;

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
        modelResultFactory = new ModelResultFactory<>(modelClass);
    }

}
