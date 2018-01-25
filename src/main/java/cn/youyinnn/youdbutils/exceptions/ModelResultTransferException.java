package cn.youyinnn.youdbutils.exceptions;

/**
 * 无法使用ModelHandler去处理结果集.
 * 如果在单独使用YouDao的时候需要同时支持ModelHandler的服务,
 * 请在实例化YouDao对象之前使用YouDbManager.scanPackageForModel方法注册所有的Model信息.
 *
 * @author youyinnn
 */
public class ModelResultTransferException extends Exception {

    public ModelResultTransferException(String msg) {
        super(msg);
    }

}
