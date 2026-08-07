package me.rainma22.jsonrpc;

import java.util.List;

import org.json.JSONObject;

import me.rainma22.utils.Toolinfo;

public class ToolsListResponse extends Response {

    public ToolsListResponse(JSONObject obj) {
        super(obj);
    }

    public ToolsListResponse(Response res) {
        this(res.object);
    }

    public List<Toolinfo> getTools() {
        return getResult().getJSONArray("tools")
                .toList()
                .stream()
                // .peek(System.out::println)
                .map((x) -> {
                    return JSONObject.wrap(x) instanceof JSONObject ? (JSONObject) JSONObject.wrap(x) : null;
                })
                .filter(x -> x != null)
                // .peek((x) -> System.out.println(x.toString(4)))
                .map((jobj) -> jobj.fromJson(Toolinfo.class))
                .toList();
    }

}