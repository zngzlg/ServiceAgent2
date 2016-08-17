package agent.server;

import agent.service.AppService;
import agent.service.Service;
import agent.service.ServiceProvider;
import spark.Route;
import spark.Spark;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2016-08-15.
 */
public class SimpleAgentServer implements Server {
    private final static int defaultPort = 4567;
    private final static Map<String, ServiceProvider> providers = new ConcurrentHashMap<>();
    private final static Map<String, Service> services = new ConcurrentHashMap<>();

    public SimpleAgentServer(int port) {
        Spark.port(defaultPort);

    }

    public SimpleAgentServer() {
        Spark.port(defaultPort);
    }

    @Override
    public void register(ServiceProvider provider) {
        providers.put(provider.getClass().toString(), provider);

    }

    @Override
    public void registerService(String path, Service service) {
        services.put(path, service);
        switch (service.getMethod()) {
            case GET:
                Spark.get(path, service.start());
                break;
            case POST:
                Spark.post(path, service.start());
        }
    }

    @Override
    public void start() {
        for (ServiceProvider provider : providers.values()) {
            provider.registerService(this);
        }
    }

    public static class WelcomeService implements ServiceProvider {

        public void registerService(Server server) {
            server.registerService("welcome", new Service() {
                @Override
                public Method getMethod() {
                    return Method.GET;
                }

                @Override
                public Route start() {
                    return (request, response) -> "welcome to hadoop service agent";
                }
            });
        }
    }

    public static void main(String[] args) throws Exception {
        Server server = new SimpleAgentServer();
        server.register(new WelcomeService());
        server.register(new AppService());
        server.start();
    }
}


