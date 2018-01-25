package cn.youyinnn.youdbutils.druid.filter;

import com.alibaba.druid.filter.stat.StatFilter;

/**
 * 提供Druid的StatFilter控制接口.
 *
 * @author youyinnn
 */
public class YouStatFilter {

    private StatFilter               statFilter                  = new StatFilter();

    /**
     * Gets stat filter.
     *
     * @return the stat filter
     */
    public StatFilter getStatFilter() { return statFilter; }

    /**
     * 慢sql记录 传入时间为毫秒
     *
     * @param slowSqlMillis the slow sql millis
     */
    public void openLogSlowSql(long slowSqlMillis) {
        statFilter.setLogSlowSql(true);
        statFilter.setSlowSqlMillis(slowSqlMillis);
    }

    /**
     * 合并没有参数化的sql
     */
    public void openMergeSql(){
        statFilter.setMergeSql(true);
    }
}
