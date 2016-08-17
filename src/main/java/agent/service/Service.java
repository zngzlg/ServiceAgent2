package agent.service;

import agent.server.SimpleAgentServer;
import spark.Route;

/**
 * Created by Administrator on 2016-08-16.
 */
public interface Service {
    enum Method {GET,POST}
    Method getMethod();
    Route start();
}
