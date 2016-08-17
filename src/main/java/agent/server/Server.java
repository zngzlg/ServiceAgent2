package agent.server;

import agent.service.Service;
import agent.service.ServiceProvider;


/**
 * Created by Administrator on 2016-08-17.
 */
public interface Server {
    void start();
    void register(ServiceProvider provider);
    void registerService(String path,Service service);
}
