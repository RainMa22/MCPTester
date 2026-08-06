package me.rainma22.libmcpclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;
import java.util.UUID;

import org.json.JSONObject;

import me.rainma22.jsonrpc.Request;
import me.rainma22.jsonrpc.Response;
import me.rainma22.jsonrpc.ToolListResponse;

public class StdioClient implements McpClient {
    private McpClientCapabilities capabilities;
    private Scanner scanner;
    private OutputStream out;

    public StdioClient(McpClientCapabilities c, InputStream i, OutputStream o) {
        capabilities = c;
        scanner = new Scanner(i);
        out = o;
    }

    private void writeAndFlush(Request s) throws IOException {
        out.write(s.getBytes());
        out.flush();
    }

    private Response readInput() throws IOException {
        while (true) {
            while (!scanner.hasNextLine()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // ignored
                }
            }
            JSONObject object = new JSONObject(scanner.nextLine());
            if (object.getString("method").startsWith("notification")) {
                continue;
                // skip notification
            } else {
                return new Response(object);
            }
        }
    }

    @Override
    public Response sendPing() throws IOException {
        writeAndFlush(capabilities.ping());
        return readInput();
    }

    @Override
    public Response sendInitialize() throws IOException {
        writeAndFlush(capabilities.initialize());
        return readInput();
    }

    @Override
    public ToolListResponse sendListTools() throws IOException {
        writeAndFlush(capabilities.listTools());
        return new ToolListResponse(readInput());
    }

    @Override
    public Response sendListPrompts() throws IOException {
        writeAndFlush(capabilities.listPrompts());
        return readInput();
    }

    @Override
    public Response sendListResources() throws IOException {
        writeAndFlush(capabilities.listResources());
        return readInput();
    }

    @Override
    public void sendNotification(String notif) throws IOException {
        writeAndFlush(new Request(UUID.randomUUID(), notif));
    }

    @Override
    public Response sendToolsCall(String tool, JSONObject params) throws IOException {
        writeAndFlush(capabilities.ToolsCall(tool, params));
        return readInput();
    }

    @Override
    public Response sendPromptsGet(String prompt, JSONObject params) throws IOException {
        writeAndFlush(capabilities.promptsGet(prompt, params));
        return readInput();
    }

    @Override
    public Response sendResourcesRead(String resource, JSONObject params) throws IOException {
        writeAndFlush(capabilities.resourcesRead(resource));
        return readInput();
    }

}
