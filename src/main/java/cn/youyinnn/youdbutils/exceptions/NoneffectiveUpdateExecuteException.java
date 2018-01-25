package cn.youyinnn.youdbutils.exceptions;

/**
 * 无效更新的异常.
 * 应对场景:
 *  一个业务有两条以上的插入或者更新操作,
 *  若因为查询无效而导致某一条插入或更新操作无效,
 *  则实际这整个业务也无效,于是再此抛出异常以进行回滚.
 *
 * @author youyinnn
 */
public class NoneffectiveUpdateExecuteException extends Exception {

    public NoneffectiveUpdateExecuteException(String msg){
        super(msg);
    }

}
