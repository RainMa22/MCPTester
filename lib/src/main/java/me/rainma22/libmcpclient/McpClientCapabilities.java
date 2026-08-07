package me.rainma22.libmcpclient;

import java.net.http.HttpResponse;

import org.json.JSONObject;

import me.rainma22.jsonrpc.Request;

public interface McpClientCapabilities {
    String getVersion();

    String[] capabilitySpecificHttpHeaders(String sessionId);

    <T> String getSessionId(HttpResponse<T> res);

    Request ping();

    Request initialize();

    Request listTools();

    Request listPrompts();

    Request listResources();

    Request listResourceTemplates();

    Request ToolsCall(String tool, JSONObject args);

    Request promptsGet(String prompt, JSONObject args);

    Request resourcesRead(String resourceURI);

}