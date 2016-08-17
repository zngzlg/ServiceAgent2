package agent.render;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.sun.prism.shader.Solid_TextureYV12_AlphaTest_Loader;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangzl on 2016-08-15.
 */
public class JsonRender implements Render {
    private final static JsonRender instance = new JsonRender();
    private final static Gson gson = new Gson();

    private JsonRender() {
        //prevent new instance
    }

    public static JsonRender getInstance() {
        return instance;
    }

    public String render(Map<String, Object> map) {
        return gson.toJson(map);
    }
    public String render(Object obj){
        return gson.toJson(obj);
    }

    public Map<String, String> fromJson(String json) {
        return gson.fromJson(json, HashMap.class);
    }

    public <T> T fromJson(String json, Class<T> clzz) {
        return gson.fromJson(json, clzz);
    }

    public static void main(String[] args) {
        JsonRender render = JsonRender.getInstance();
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("name", "zhangzl");
        data.put("password", "123456");
        String json = render.render(data);
        System.out.println(json);

        Map<String, String> result = render.fromJson(json);
        for (String key : result.keySet()) {
            System.out.println(key + ":" + result.get(key));
        }
    }
}
