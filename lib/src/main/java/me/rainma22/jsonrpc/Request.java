package me.rainma22.jsonrpc;

import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

public class Request {
    protected JSONObject object = new JSONObject();

    public Request(Object id) {
        object.put("jsonrpc", "2.0");
        object.put("id", id.toString());
    }

    public Request(Object id, String method) {
        this(id);
        setMethod(method);
    }

    public Request(Object id, String method, JSONObject params) {
        this(id, method);
        setParams(params);
    }

    public void setMethod(String method) {
        object.put("method", method);
    }

    public String getMethod() {
        return object.getString("method");
    }

    public void setParams(JSONObject params) {
        object.put("params", params);
    }

    public JSONObject getParams() {
        return object.getJSONObject("params");
    }

    public String toString() {
        return object.toString();
    }

    public byte[] getBytes() {
        return toString().getBytes(StandardCharsets.UTF_8);
    }
}
