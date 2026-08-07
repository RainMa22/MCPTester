package me.rainma22.jsonrpc;

import java.util.List;

import org.json.JSONObject;

import me.rainma22.utils.ResourceTemplateInfo;

/**
 * 
 * PromptsListResponse
 *
 * TODO: no pagination support yet
 **/
public class ResourceTemplatesListResponse extends Response {

    public ResourceTemplatesListResponse(JSONObject obj) {
        super(obj);
    }

    public ResourceTemplatesListResponse(Response res) {
        this(res.object);
    }

    public List<ResourceTemplateInfo> getResourceTemplates() {
        return getResult().getJSONArray("resourceTemplates")
                .toList()
                .stream()
                // .peek(System.out::println)
                .map((x) -> {
                    return JSONObject.wrap(x) instanceof JSONObject ? (JSONObject) JSONObject.wrap(x) : null;
                })
                .filter(x -> x != null)
                // .peek((x) -> System.out.println(x.toString(4)))
                .map((jobj) -> jobj.fromJson(ResourceTemplateInfo.class))
                .toList();
    }

}