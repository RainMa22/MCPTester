package me.rainma22.jsonrpc;

import java.util.List;

import org.json.JSONObject;

import me.rainma22.utils.PromptInfo;
import me.rainma22.utils.Toolinfo;

/**
 * 
 * PromptsListResponse
 *
 * TODO: no pagination support yet
 **/
public class PromptsListResponse extends Response {

    public PromptsListResponse(JSONObject obj) {
        super(obj);
    }

    public PromptsListResponse(Response res) {
        this(res.object);
    }

    public List<PromptInfo> getPrompts() {
        return getResult().getJSONArray("prompts")
                .toList()
                .stream()
                // .peek(System.out::println)
                .map((x) -> {
                    return JSONObject.wrap(x) instanceof JSONObject ? (JSONObject) JSONObject.wrap(x) : null;
                })
                .filter(x -> x != null)
                // .peek((x) -> System.out.println(x.toString(4)))
                .map((jobj) -> jobj.fromJson(PromptInfo.class))
                .toList();
    }

}