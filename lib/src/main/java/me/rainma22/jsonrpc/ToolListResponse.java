package me.rainma22.jsonrpc;

import java.util.List;

import javax.lang.model.type.NullType;

import org.json.JSONArray;
import org.json.JSONObject;

import me.rainma22.utils.Toolinfo;

public class ToolListResponse extends Response {

    public ToolListResponse(JSONObject obj) {
        super(obj);
    }

    public ToolListResponse(Response res) {
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
                .peek((x) -> System.out.println(x.toString(4)))
                .map((jobj) -> jobj.fromJson(Toolinfo.class))
                .toList();
    }

}