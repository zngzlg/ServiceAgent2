package agent.service;

import agent.server.Server;

/**
 * Created by Administrator on 2016-08-16.
 */
public interface ServiceProvider {
    void registerService(Server server);
}
