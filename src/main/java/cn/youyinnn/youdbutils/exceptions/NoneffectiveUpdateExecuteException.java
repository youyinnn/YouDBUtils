package cn.youyinnn.youdbutils.exceptions;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/10/31
 */
public class NoneffectiveUpdateExecuteException extends Throwable {

    // TODO: 现在的做法是每个无效执行操作都抛出异常 但这个不能灵活适应大部分场景 需要想办法解决
    public NoneffectiveUpdateExecuteException(String msg){
        super(msg);
    }

}
