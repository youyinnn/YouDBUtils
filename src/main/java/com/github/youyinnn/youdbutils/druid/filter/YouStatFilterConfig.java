package com.github.youyinnn.youdbutils.druid.filter;

import com.alibaba.druid.filter.stat.StatFilter;

/**
 * 提供Druid的StatFilter控制接口.
 *
 * @author youyinnn
 */
public class YouStatFilterConfig {

    private boolean logSlowSql = false;
    private boolean mergeSql = false;
    private long slowSqlMillis = 0;
    private Long timeBetweenLogStatusMillis = null;

    public StatFilter configStatFilter(StatFilter statFilter){
        statFilter.setMergeSql(mergeSql);
        if (logSlowSql) {
            statFilter.setLogSlowSql(true);
            statFilter.setSlowSqlMillis(slowSqlMillis);
        }
        return statFilter;
    }

    public void enableMergeSql() {
        this.mergeSql = true;
    }

    public void enableLogSlowSql(long slowSqlMillis) {
        logSlowSql = true;
        this.slowSqlMillis = slowSqlMillis;
    }

    /**
     * 打开监控数据输出到日志中
     * 特别需要在log4j2设置一个logger来定制化你的日志输出
     *
     * @param logStatsMillis the log stats millis
     */
    public void setTimeBetweenLogStatsMillis(long logStatsMillis){
        timeBetweenLogStatusMillis = logStatsMillis;
    }

    public Long getTimeBetweenLogStatusMillis() {
        return timeBetweenLogStatusMillis;
    }
}
