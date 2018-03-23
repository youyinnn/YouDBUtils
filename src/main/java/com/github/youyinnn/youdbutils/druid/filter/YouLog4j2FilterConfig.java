package com.github.youyinnn.youdbutils.druid.filter;

import com.alibaba.druid.filter.logging.Log4j2Filter;

/**
 * 提供Druid的Log4jFilter的控制接口.
 *
 * @author youyinnn
 */
public class YouLog4j2FilterConfig {

    private boolean connectionConnectBeforeLogEnabled        = false;
    private boolean connectionConnectAfterLogEnabled         = false;
    private boolean connectionCommitAfterLogEnabled          = false;
    private boolean connectionRollbackAfterLogEnabled        = false;
    private boolean connectionCloseAfterLogEnabled           = false;
    private boolean statementCreateAfterLogEnabled           = false;
    private boolean statementPrepareAfterLogEnabled          = false;
    private boolean statementPrepareCallAfterLogEnabled      = false;
    private boolean statementExecuteAfterLogEnabled          = false;
    private boolean statementExecuteQueryAfterLogEnabled     = false;
    private boolean statementExecuteUpdateAfterLogEnabled    = false;
    private boolean statementExecuteBatchAfterLogEnabled     = false;
    private boolean statementCloseAfterLogEnabled            = false;
    private boolean statementParameterClearLogEnable         = false;
    private boolean statementParameterSetLogEnabled          = false;
    private boolean resultSetNextAfterLogEnabled             = false;
    private boolean resultSetOpenAfterLogEnabled             = false;
    private boolean resultSetCloseAfterLogEnabled            = false;
    private boolean dataSourceLogEnabled                     = false;
    private boolean connectionLogEnabled                     = false;
    private boolean connectionLogErrorEnabled                = false;
    private boolean statementLogEnabled                      = false;
    private boolean statementLogErrorEnabled                 = false;
    private boolean resultSetLogEnabled                      = false;
    private boolean resultSetLogErrorEnabled                 = false;
    private boolean allConnectionLogEnable                   = false;
    private boolean allStatementLogEnable                    = false;
    private boolean allResultSetLogEnable                    = false;

    private boolean statementExecutableSqlLogEnable          = true;

    public Log4j2Filter configLog4j2Filter(Log4j2Filter log4j2Filter) {
        // 三个次顶级
        log4j2Filter.setConnectionLogEnabled(connectionLogEnabled);
        log4j2Filter.setConnectionLogErrorEnabled(connectionLogErrorEnabled);
        log4j2Filter.setStatementLogEnabled(statementLogEnabled);
        log4j2Filter.setStatementLogErrorEnabled(statementLogErrorEnabled);
        log4j2Filter.setResultSetLogEnabled(resultSetLogEnabled);
        log4j2Filter.setResultSetLogErrorEnabled(resultSetLogErrorEnabled);

        // Connection半级
        log4j2Filter.setConnectionConnectBeforeLogEnabled(allConnectionLogEnable);
        log4j2Filter.setConnectionConnectAfterLogEnabled(allConnectionLogEnable);
        log4j2Filter.setConnectionCommitAfterLogEnabled(allConnectionLogEnable);
        log4j2Filter.setConnectionRollbackAfterLogEnabled(allConnectionLogEnable);
        log4j2Filter.setConnectionCloseAfterLogEnabled(allConnectionLogEnable);

        // Statement半级
        log4j2Filter.setStatementCreateAfterLogEnabled(allStatementLogEnable);
        log4j2Filter.setStatementPrepareAfterLogEnabled(allStatementLogEnable);
        log4j2Filter.setStatementPrepareCallAfterLogEnabled(allStatementLogEnable);
        log4j2Filter.setStatementExecuteAfterLogEnabled(allStatementLogEnable);
        log4j2Filter.setStatementExecuteQueryAfterLogEnabled(allStatementLogEnable);
        log4j2Filter.setStatementExecuteUpdateAfterLogEnabled(allStatementLogEnable);
        log4j2Filter.setStatementExecuteBatchAfterLogEnabled(allStatementLogEnable);

        log4j2Filter.setStatementExecutableSqlLogEnable(allStatementLogEnable);

        log4j2Filter.setStatementCloseAfterLogEnabled(allStatementLogEnable);
        log4j2Filter.setStatementParameterClearLogEnable(allStatementLogEnable);
        log4j2Filter.setStatementParameterSetLogEnabled(allStatementLogEnable);

        // ResultSet半级
        log4j2Filter.setResultSetNextAfterLogEnabled(allResultSetLogEnable);
        log4j2Filter.setResultSetOpenAfterLogEnabled(allResultSetLogEnable);
        log4j2Filter.setResultSetCloseAfterLogEnabled(allResultSetLogEnable);

        // 最小级
        log4j2Filter.setConnectionConnectBeforeLogEnabled(connectionConnectBeforeLogEnabled);
        log4j2Filter.setConnectionConnectAfterLogEnabled(connectionConnectAfterLogEnabled);
        log4j2Filter.setConnectionCommitAfterLogEnabled(connectionCommitAfterLogEnabled);
        log4j2Filter.setConnectionRollbackAfterLogEnabled(connectionRollbackAfterLogEnabled);
        log4j2Filter.setConnectionCloseAfterLogEnabled(connectionCloseAfterLogEnabled);
        log4j2Filter.setStatementCreateAfterLogEnabled(statementCreateAfterLogEnabled);
        log4j2Filter.setStatementPrepareCallAfterLogEnabled(statementPrepareCallAfterLogEnabled);
        log4j2Filter.setStatementExecuteAfterLogEnabled(statementExecuteAfterLogEnabled);
        log4j2Filter.setStatementExecuteQueryAfterLogEnabled(statementExecuteQueryAfterLogEnabled);
        log4j2Filter.setStatementExecuteUpdateAfterLogEnabled(statementExecuteUpdateAfterLogEnabled);
        log4j2Filter.setStatementExecuteBatchAfterLogEnabled(statementExecuteBatchAfterLogEnabled);
        log4j2Filter.setStatementCloseAfterLogEnabled(statementCloseAfterLogEnabled);
        log4j2Filter.setStatementParameterClearLogEnable(statementParameterClearLogEnable);
        log4j2Filter.setStatementParameterSetLogEnabled(statementParameterSetLogEnabled);
        log4j2Filter.setResultSetNextAfterLogEnabled(resultSetNextAfterLogEnabled);
        log4j2Filter.setResultSetCloseAfterLogEnabled(resultSetCloseAfterLogEnabled);
        log4j2Filter.setResultSetOpenAfterLogEnabled(resultSetOpenAfterLogEnabled);
        log4j2Filter.setDataSourceLogEnabled(dataSourceLogEnabled);

        log4j2Filter.setStatementPrepareAfterLogEnabled(statementPrepareAfterLogEnabled);

        log4j2Filter.setStatementExecutableSqlLogEnable(statementExecutableSqlLogEnable);

        return log4j2Filter;
    }

    public void enableConnectionConnectBeforeLog() {
        this.connectionConnectBeforeLogEnabled = true;
    }

    public void enableConnectionConnectAfterLog() {
        this.connectionConnectAfterLogEnabled = true;
    }

    public void enableConnectionCommitAfterLog() {
        this.connectionCommitAfterLogEnabled = true;
    }

    public void enableConnectionRollbackAfterLog() {
        this.connectionRollbackAfterLogEnabled = true;
    }

    public void enableConnectionCloseAfterLog() {
        this.connectionCloseAfterLogEnabled = true;
    }

    public void enableStatementCreateAfterLog() {
        this.statementCreateAfterLogEnabled = true;
    }

    public void enableStatementPrepareAfterLog() {
        this.statementPrepareAfterLogEnabled = true;
    }

    public void enableStatementPrepareCallAfterLog() {
        this.statementPrepareCallAfterLogEnabled = true;
    }

    public void enableStatementExecuteAfterLog() {
        this.statementExecuteAfterLogEnabled = true;
    }

    public void enableStatementExecuteQueryAfterLog() {
        this.statementExecuteQueryAfterLogEnabled = true;
    }

    public void enableStatementExecuteUpdateAfterLog() {
        this.statementExecuteUpdateAfterLogEnabled = true;
    }

    public void enableStatementExecuteBatchAfterLog() {
        this.statementExecuteBatchAfterLogEnabled = true;
    }

    public void enableStatementExecutableSqlLog() {
        this.statementExecutableSqlLogEnable = true;
    }

    public void enableStatementCloseAfterLog() {
        this.statementCloseAfterLogEnabled = true;
    }

    public void enableStatementParameterClearLog() {
        this.statementParameterClearLogEnable = true;
    }

    public void enableStatementParameterSetLog() {
        this.statementParameterSetLogEnabled = true;
    }

    public void enableResultSetNextAfterLog() {
        this.resultSetNextAfterLogEnabled = true;
    }

    public void enableResultSetOpenAfterLog() {
        this.resultSetOpenAfterLogEnabled = true;
    }

    public void enableResultSetCloseAfterLog() {
        this.resultSetCloseAfterLogEnabled = true;
    }

    public void enableDataSourceLog() {
        this.dataSourceLogEnabled = true;
    }

    public void enableConnectionLog() {
        this.connectionLogEnabled = true;
    }

    public void enableConnectionLogError() {
        this.connectionLogErrorEnabled = true;
    }

    public void enableStatementLog() {
        this.statementLogEnabled = true;
    }

    public void enableStatementLogError() {
        this.statementLogErrorEnabled = true;
    }

    public void enableResultSetLog() {
        this.resultSetLogEnabled = true;
    }

    public void enableResultSetLogError() {
        this.resultSetLogErrorEnabled = true;
    }

    public void enableAllConnectionLog() {
        this.allConnectionLogEnable = true;
    }

    public void enableAllStatementLog() {
        this.allStatementLogEnable = true;
    }

    public void enableAllResultSetLog() {
        this.allResultSetLogEnable = true;
    }

}
