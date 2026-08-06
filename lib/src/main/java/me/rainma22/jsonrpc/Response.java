package me.rainma22.jsonrpc;

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

}