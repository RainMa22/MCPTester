package me.rainma22.libmcpclient;

import java.io.IOException;

import org.json.JSONObject;

import me.rainma22.jsonrpc.PromptsListResponse;
import me.rainma22.jsonrpc.ResourcesListResponse;
import me.rainma22.jsonrpc.Response;
import me.rainma22.jsonrpc.ToolsListResponse;

public interface McpClient {
    Response sendPing() throws IOException;

    Response sendInitialize() throws IOException;

    ToolsListResponse sendListTools() throws IOException;

    PromptsListResponse sendListPrompts() throws IOException;

    ResourcesListResponse sendListResources() throws IOException;

    void sendNotification(String notif) throws IOException;

    Response sendToolsCall(String tool, JSONObject params) throws IOException;

    Response sendPromptsGet(String prompt, JSONObject params) throws IOException;

    Response sendResourcesRead(String resource, JSONObject params) throws IOException;
}
