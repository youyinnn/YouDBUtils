package cn.youyinnn.youdbutils.druid.filter;

import com.alibaba.druid.filter.logging.Log4j2Filter;

import java.lang.reflect.Method;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/9/18
 */
public class YouLog4j2Filter {

    /**
     * 下面这些开关常量名 除了STATEMENT_EXECUTABLE_SQL_LOG_ENABLE是false以外 其它默认都是true
     * */

    public static final String      CONNECTION_CONNECT_BEFORE_LOG_ENABLE        = "connectionConnectBeforeLogEnabled";
    public static final String      CONNECTION_CONNECT_AFTER_LOG_ENABLE         = "connectionConnectAfterLogEnabled";
    public static final String      CONNECTION_COMMIT_AFTER_LOG_ENABLE          = "connectionCommitAfterLogEnabled";
    public static final String      CONNECTION_ROLLBACK_AFTER_LOG_ENABLE        = "connectionRollbackAfterLogEnabled";
    public static final String      CONNECTION_CLOSE_AFTER_LOG_ENABLE           = "connectionCloseAfterLogEnabled";

    public static final String      STATEMENT_CREATE_AFTER_LOG_ENABLE           = "statementCreateAfterLogEnabled";
    public static final String      STATEMENT_PREPARE_AFTER_LOG_ENABLE          = "statementPrepareAfterLogEnabled";
    public static final String      STATEMENT_PREPARE_CALL_AFTER_ENABLE         = "statementPrepareCallAfterLogEnabled";

    public static final String      STATEMENT_EXECUTE_AFTER_LOG_ENABLE          = "statementExecuteAfterLogEnabled";
    public static final String      STATEMENT_EXECUTE_QUERY_AFTER_LOG_ENABLE    = "statementExecuteQueryAfterLogEnabled";
    public static final String      STATEMENT_EXECUTE_UPDATE_AFTER_LOG_ENABLE   = "statementExecuteUpdateAfterLogEnabled";
    public static final String      STATEMENT_EXECUTE_BATCH_AFTER_LOG_ENABLE    = "statementExecuteBatchAfterLogEnabled";

    public static final String      STATEMENT_EXECUTABLE_SQL_LOG_ENABLE        = "statementExecutableSqlLogEnable";

    public static final String      STATEMENT_CLOSE_AFTER_LOG_ENABLE            = "statementCloseAfterLogEnabled";

    public static final String      STATEMENT_PARAMETER_CLEAR_LOG_ENABLE        = "statementParameterClearLogEnable";
    public static final String      STATEMENT_PARAMETERSET_LOG_ENABLE           = "statementParameterSetLogEnabled";

    public static final String      RESULTSET_NEXT_AFTER_LOG_ENABLE             = "resultSetNextAfterLogEnabled";
    public static final String      RESULTSET_OPEN_AFTER_LOG_ENABLE             = "resultSetOpenAfterLogEnabled";
    public static final String      RESULTSET_CLOSE_AFTER_LOG_ENABLE            = "resultSetCloseAfterLogEnabled";

    public static final String      DATASOURCE_LOG_ENABLE                       = "dataSourceLogEnabled";
    public static final String      CONNECTION_LOG_ENABLE                       = "connectionLogEnabled";
    public static final String      CONNECTION_LOG_ERROR_ENABLE                 = "connectionLogErrorEnabled";
    public static final String      STATEMENT_LOG_ENABLED                       = "statementLogEnabled";
    public static final String      STETEMENT_LOG_ERROR_ENABLE                  = "statementLogErrorEnabled";
    public static final String      RESULTSET_LOG_ENABLE                        = "resultSetLogEnabled";
    public static final String      RESULTSET_LOG_ERROR_ENABLE                  = "resultSetLogErrorEnabled";

    private Log4j2Filter            log4j2Filter                                = new Log4j2Filter();

    /**
     * 获取一个正常的Log4j2Filter 即开关值取默认值
     *
     * @return the log 4 j 2 filter
     */
    public Log4j2Filter getLog4j2Filter() {
        return log4j2Filter;
    }

    /**
     * 获取一个关闭了所有开关的Log4j2Filter
     *
     * 特别需要注意的是STATEMENT_EXECUTABLE_SQL_LOG_ENABLE开关在这个时候是开着的
     *
     * @return the log 4 j 2 filter with all off
     */
    public void setLog4j2FilterWithAllOff() {

        setAllEnableSwitch(false);

    }

    /**
     * 获取一个开启了所有开关的Log4j2Filter
     *
     * 特别需要注意的是STATEMENT_EXECUTABLE_SQL_LOG_ENABLE开关在这个时候是关着的
     *
     * @return the log 4 j 2 filter
     */
    public void setLog4j2FilterWithAllOn(){

        setAllEnableSwitch(true);

    }

    /**
     * 一键设置所有开关
     * */
    private void setAllEnableSwitch(boolean boo) {

        setAllConnectionEnableSwitch(boo);

        setAllStatementEnableSwitch(boo);

        setAllResultSetEnableSwitch(boo);

        setDatasourceLogEnable(boo);
        setConnectionLogEnabled(boo);
        setConnectionLogErrorEnabled(boo);
        setStatementLogEnabled(boo);
        setStatementLogErrorEnabled(boo);
        setResultSetLogEnabled(boo);
        setResultSetLogErrorEnabled(boo);
    }

    /**
     * datasource的总开关 总开关关了 其下的小开关设置无效
     *
     * @param boo the boo
     */
    public void setDatasourceLogEnable(boolean boo) {
        log4j2Filter.setDataSourceLogEnabled(boo);
    }

    /**
     * connection的总开关
     *
     * @param boo the boo
     */
    public void setConnectionLogEnabled(boolean boo) {
        log4j2Filter.setConnectionLogEnabled(boo);
    }

    /**
     * connection error的总开关
     *
     * @param boo the boo
     */
    public void setConnectionLogErrorEnabled(boolean boo) {
        log4j2Filter.setConnectionLogErrorEnabled(boo);
    }

    /**
     * statement的总开关
     *
     * @param boo the boo
     */
    public void setStatementLogEnabled(boolean boo) {
        log4j2Filter.setStatementLogEnabled(boo);
    }

    /**
     * statement error的总开关
     *
     * @param boo the boo
     */
    public void setStatementLogErrorEnabled(boolean boo) {
        log4j2Filter.setStatementLogErrorEnabled(boo);
    }

    /**
     * result set的总开关
     *
     * @param boo the boo
     */
    public void setResultSetLogEnabled(boolean boo) {
        log4j2Filter.setResultSetLogEnabled(boo);
    }

    /**
     * result set error的总开关
     *
     * @param boo the boo
     */
    public void setResultSetLogErrorEnabled(boolean boo) {
        log4j2Filter.setResultSetLogErrorEnabled(boo);
    }

    /**
     * 一键开关所有connection相关的开关
     *
     * @param boo the boo
     */
    public void setAllConnectionEnableSwitch(boolean boo){
        log4j2Filter.setConnectionConnectBeforeLogEnabled(boo);
        log4j2Filter.setConnectionConnectAfterLogEnabled(boo);
        log4j2Filter.setConnectionCommitAfterLogEnabled(boo);
        log4j2Filter.setConnectionRollbackAfterLogEnabled(boo);
        log4j2Filter.setConnectionCloseAfterLogEnabled(boo);
    }

    /**
     * 一键开关statement相关的所有开关
     *
     * @param boo the boo
     */
    public void setAllStatementEnableSwitch(boolean boo){
        log4j2Filter.setStatementCreateAfterLogEnabled(boo);
        log4j2Filter.setStatementPrepareAfterLogEnabled(boo);
        log4j2Filter.setStatementPrepareCallAfterLogEnabled(boo);

        log4j2Filter.setStatementExecuteAfterLogEnabled(boo);
        log4j2Filter.setStatementExecuteQueryAfterLogEnabled(boo);
        log4j2Filter.setStatementExecuteUpdateAfterLogEnabled(boo);
        log4j2Filter.setStatementExecuteBatchAfterLogEnabled(boo);

        log4j2Filter.setStatementExecutableSqlLogEnable(!boo);

        log4j2Filter.setStatementCloseAfterLogEnabled(boo);

        log4j2Filter.setStatementParameterClearLogEnable(boo);
        log4j2Filter.setStatementParameterSetLogEnabled(boo);
    }

    /**
     * 一键关闭result set相关的所有开关
     *
     * @param boo the boo
     */
    public void setAllResultSetEnableSwitch(boolean boo){
        log4j2Filter.setResultSetNextAfterLogEnabled(boo);
        log4j2Filter.setResultSetOpenAfterLogEnabled(boo);
        log4j2Filter.setResultSetCloseAfterLogEnabled(boo);
    }

    /**
     * 设置单个开关
     *
     * 这个方法用于自定义开关组合 你可以获取全开的filter 然后单独关闭其中几个 反之亦然
     *
     * @param enableSwitchName the enable switch name
     * @param boo              the boo
     */
    public void setEnableSwitch(String enableSwitchName,boolean boo){
        Class<? extends Log4j2Filter> aClass = log4j2Filter.getClass();

        String firstLetter = String.valueOf(enableSwitchName.charAt(0));
        String substring = enableSwitchName.substring(1);

        try {
            Method enableSwitch = aClass.getMethod("set"+firstLetter.toUpperCase()+substring,Boolean.TYPE);

            enableSwitch.invoke(log4j2Filter,boo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
