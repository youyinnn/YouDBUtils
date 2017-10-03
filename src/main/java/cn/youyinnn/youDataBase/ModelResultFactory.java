package cn.youyinnn.youDataBase;

import com.mysql.cj.api.mysqla.result.Resultset;

import java.util.List;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/10/3
 */
public class ModelResultFactory {

    public static List createInstance(Resultset resultset, Class modelClass) {



        try {
            modelClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

}
