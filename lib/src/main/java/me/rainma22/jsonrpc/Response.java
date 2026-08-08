package me.rainma22.jsonrpc;

import org.json.JSONArray;
import org.json.JSONObject;

public class Response {
    protected JSONObject object;

    public Response(JSONObject obj) {
        this.object = obj;
    }

    public JSONObject getResult() {
        return object.getJSONObject("result");
    }

    public String toString() {
        return object.toString();
    }

    public boolean isPaginated() {
        return object.has("result") && getResult().has("nextCursor");
    }

    public void composeWith(Response another) {
        for (String field : new String[] { "tools", "prompts", "resources", "resourceTemplates" }) {
            JSONArray arr = getResult().optJSONArray(field, new JSONArray())
                    .putAll(another.getResult().optJSONArray(field, new JSONArray()));
            if (arr.isEmpty())
                continue;
            getResult().put(field, arr);
        }
        getResult().put("nextCursor", another.getResult().opt("nextCursor"));
    }

    public Object nextCursor() {
        return isPaginated() ? getResult().opt("nextCursor") : null;
    }
}