package cn.youyinnn.youdbutils.exceptions;

/**
 * 这个异常表示需要自动装备的类对象不被支持.
 *
 * @author youyinnn
 */
public class AutowiredException extends Exception{

    public AutowiredException(String msg) {
        super(msg);
    }

}
