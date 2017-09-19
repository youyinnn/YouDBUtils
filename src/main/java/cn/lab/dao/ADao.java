package cn.lab.dao;

import cn.youyinnn.youDataBase.AnnotationScanner;
import cn.youyinnn.youDataBase.annotation.Dao;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/9/12
 */
public class ADao implements Dao{

    public void dao(){

        ADATABASE.setDatabaseName("test.db");

        System.out.println("aDAO");

        AnnotationScanner annotationScanner = new AnnotationScanner();
    }

}
