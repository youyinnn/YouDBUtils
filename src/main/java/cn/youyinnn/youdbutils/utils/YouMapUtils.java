package cn.youyinnn.youdbutils.utils;

import cn.youyinnn.youdbutils.exceptions.YouMapException;

import java.util.HashMap;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/10/22
 */
public class YouMapUtils {

    public static HashMap<String, Object> getYouMap(Object ...objects) {
        HashMap<String, Object> youMap = new HashMap<>(10);
        int length = objects.length;
        if (length % 2 != 0) {
            try {
                throw new YouMapException("传入参数数目不是偶数，无法创建完整的键值对。");
            } catch (YouMapException e) {
                e.printStackTrace();
            }
        } else {
            for (int i = 0 ; i < length ; i += 2) {
                if (objects[i] instanceof String){
                    youMap.put(String.valueOf(objects[i]),objects[i+1]);
                } else {
                    try {
                        throw new YouMapException("传入的第["+(i+1)+"]个参数不是String类型，不能作为键。");
                    } catch (YouMapException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return youMap;
    }

}
