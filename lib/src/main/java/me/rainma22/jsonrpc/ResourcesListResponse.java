package me.rainma22.jsonrpc;

import java.util.List;

import org.json.JSONObject;

import me.rainma22.utils.ResourceInfo;

/**
 * 
 * PromptsListResponse
 *
 **/
public class ResourcesListResponse extends Response {

    public ResourcesListResponse(JSONObject obj) {
        super(obj);
    }

    public ResourcesListResponse(Response res) {
        this(res.object);
    }

    public List<ResourceInfo> getResources() {
        return getResult().getJSONArray("resources")
                .toList()
                .stream()
                // .peek(System.out::println)
                .map((x) -> {
                    return JSONObject.wrap(x) instanceof JSONObject ? (JSONObject) JSONObject.wrap(x) : null;
                })
                .filter(x -> x != null)
                // .peek((x) -> System.out.println(x.toString(4)))
                .map((jobj) -> jobj.fromJson(ResourceInfo.class))
                .toList();
    }

}