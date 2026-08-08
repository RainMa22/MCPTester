package me.rainma22.libmcpclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;
import java.util.UUID;

import org.json.JSONObject;

import me.rainma22.jsonrpc.PromptsListResponse;
import me.rainma22.jsonrpc.Request;
import me.rainma22.jsonrpc.ResourceTemplatesListResponse;
import me.rainma22.jsonrpc.ResourcesListResponse;
import me.rainma22.jsonrpc.Response;
import me.rainma22.jsonrpc.ToolsListResponse;

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

    private Response writeFlushThenRead(Request s) throws IOException {
        writeAndFlush(s);
        var res = readInput();
        if (res.isPaginated()) {
            res.composeWith(writeFlushThenRead(s));
        }
        return res;
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
        return writeFlushThenRead(capabilities.ping());
    }

    @Override
    public Response sendInitialize() throws IOException {
        return writeFlushThenRead(capabilities.initialize());
    }

    @Override
    public Response sendServerDiscover() throws IOException {
        return writeFlushThenRead(capabilities.serverDiscover());
    }

    @Override
    public ToolsListResponse sendListTools() throws IOException {
        return new ToolsListResponse(writeFlushThenRead(capabilities.listTools()));
    }

    @Override
    public PromptsListResponse sendListPrompts() throws IOException {
        return new PromptsListResponse(writeFlushThenRead(capabilities.listPrompts()));
    }

    @Override
    public ResourcesListResponse sendListResources() throws IOException {
        return new ResourcesListResponse(writeFlushThenRead(capabilities.listResources()));
    }

    @Override
    public ResourceTemplatesListResponse sendListResourceTemplates() throws IOException {
        return new ResourceTemplatesListResponse(writeFlushThenRead(capabilities.listResourceTemplates()));
    }

    @Override
    public void sendNotification(String notif) throws IOException {
        writeAndFlush(new Request(UUID.randomUUID(), notif));
    }

    @Override
    public Response sendToolsCall(String tool, JSONObject params) throws IOException {
        return writeFlushThenRead(capabilities.ToolsCall(tool, params));
    }

    @Override
    public Response sendPromptsGet(String prompt, JSONObject params) throws IOException {
        return writeFlushThenRead(capabilities.promptsGet(prompt, params));
    }

    @Override
    public Response sendResourcesRead(String resource, JSONObject params) throws IOException {
        return writeFlushThenRead(capabilities.resourcesRead(resource));
    }

}
