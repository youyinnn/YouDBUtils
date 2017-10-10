package cn.youyinnn.youDBUtils.druid.filter;

import com.alibaba.druid.filter.stat.StatFilter;

/**
 * The type You stat filter.
 *
 * @description:
 * @author: youyinnn
 * @date: 2017 /9/19
 */
public class YouStatFilter {

    private static StatFilter               statFilter                  = new StatFilter();

    private YouStatFilter(){}

    /**
     * Gets stat filter.
     *
     * @return the stat filter
     */
    public static StatFilter getStatFilter() { return statFilter; }

    /**
     * 慢sql记录 传入时间为毫秒
     *
     * @param slowSqlMillis the slow sql millis
     */
    public static void openLogSlowSql(long slowSqlMillis) {
        statFilter.setLogSlowSql(true);
        statFilter.setSlowSqlMillis(slowSqlMillis);
    }

    /**
     * 合并没有参数化的sql
     */
    public static void openMergeSql(){
        statFilter.setMergeSql(true);
    }
}
