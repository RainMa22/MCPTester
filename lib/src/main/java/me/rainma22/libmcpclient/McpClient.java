package me.rainma22.libmcpclient;

import java.io.IOException;

import me.rainma22.jsonrpc.Response;

public interface McpClient {
    Response sendPing() throws IOException;

    Response sendInitialize() throws IOException;

    Response sendListTools() throws IOException;

    Response sendListPrompts() throws IOException;

    Response sendListResources() throws IOException;

    void sendNotification(String notif) throws IOException;
}
