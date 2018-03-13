package com.github.youyinnn.youdbutils.utils;

import com.github.youyinnn.youwebutils.third.Log4j2Helper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.DocumentException;

import java.io.IOException;

/**
 * @author youyinnn
 */
public class LogUtils {

    static {
        try {
            Log4j2Helper.useConfig("youdblog4j2.xml");
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    private static Logger druidLog;
    private static Logger connectionLog;

    public static Logger getDruidLog() {
        if (druidLog == null) {
            druidLog = LogManager.getLogger("$db_druid");
        }
        return druidLog;
    }

    public static Logger getConnectionLog() {
        if (connectionLog == null) {
            connectionLog = LogManager.getLogger("$db_connection");
        }
        return connectionLog;
    }
}
