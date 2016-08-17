package agent.common;

import org.apache.log4j.Logger;

/**
 * Created by Administrator on 2016-08-15.
 */
public class LogProvider {
    final static Logger instance = Logger.getLogger(LogProvider.class.getName());

    private LogProvider() {
    }

    public static Logger getInstance() {
        return instance;
    }
}
