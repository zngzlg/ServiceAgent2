package agent.service;

import agent.render.JsonRender;
import agent.server.Server;
import spark.Route;

/**
 * Created by Administrator on 2016-08-15.
 */
public class AppService implements ServiceProvider {
    private static class AppRequest {
        public String name;
        public String app;
        public String params;
        public String id;
        public String status;
    }

    @Override
    public void registerService(Server server) {
        server.registerService("/start/app", new Service() {
            @Override
            public Method getMethod() {
                return Method.POST;
            }

            @Override
            public Route start() {
                return (request, response) -> {
                    String body = request.body();
                    AppRequest appRequest = JsonRender.getInstance().fromJson(body, AppRequest.class);
                    appRequest.status = "success";
                    appRequest.id= "application20140201";
                    return JsonRender.getInstance().render(appRequest);
                };
            }
        });

        server.registerService("/stop/app", new Service() {
            @Override
            public Method getMethod() {
                return Method.POST;
            }

            @Override
            public Route start() {
                return null;
            }
        });
    }
}
