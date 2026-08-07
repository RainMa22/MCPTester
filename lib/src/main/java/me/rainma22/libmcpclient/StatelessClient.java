package me.rainma22.libmcpclient;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.json.JSONObject;

import me.rainma22.jsonrpc.PromptsListResponse;
import me.rainma22.jsonrpc.Request;
import me.rainma22.jsonrpc.ResourceTemplatesListResponse;
import me.rainma22.jsonrpc.ResourcesListResponse;
import me.rainma22.jsonrpc.Response;
import me.rainma22.jsonrpc.ToolsListResponse;

/**
 * NOT A STANDARD Client, just a simple client that expects JSON-only responses;
 */
public class StatelessClient implements McpClient {
    private McpClientCapabilities capabilities;
    private URL url;
    private HttpClient client = HttpClient.newHttpClient();

    public StatelessClient(McpClientCapabilities c, URL url) {
        capabilities = c;
        this.url = url;
    }

    private Response post(String s) throws IOException {
        try {
            return client.sendAsync(HttpRequest.newBuilder(url.toURI())
                    .header("Accept", "application/json")
                    .POST(BodyPublishers.ofString(s))
                    .build(), BodyHandlers.ofString())
                    .thenApply((res) -> new Response(new JSONObject(res.body().toString())))
                    .get();
        } catch (InterruptedException | ExecutionException | URISyntaxException e) {
            throw new IOException(e);
        }
    }

    private Response post(Request r) throws IOException {
        return post(r.toString());
    }

    @Override
    public Response sendPing() throws IOException {
        return post(capabilities.ping());
    }

    @Override
    public Response sendInitialize() throws IOException {
        var res = post(capabilities.initialize());
        sendNotification("notification/initiated");
        return res;
    }

    @Override
    public ToolsListResponse sendListTools() throws IOException {
        return new ToolsListResponse(post(capabilities.listTools()));
    }

    @Override
    public PromptsListResponse sendListPrompts() throws IOException {
        return new PromptsListResponse(post(capabilities.listPrompts()));
    }

    @Override
    public ResourcesListResponse sendListResources() throws IOException {
        return new ResourcesListResponse(post(capabilities.listResources()));
    }

    @Override
    public ResourceTemplatesListResponse sendListResourceTemplates() throws IOException {
        return new ResourceTemplatesListResponse(post(capabilities.listResourceTemplates()));
    }

    @Override
    public void sendNotification(String notif) throws IOException {
        post(new Request(UUID.randomUUID(), notif));
    }

    @Override
    public Response sendToolsCall(String tool, JSONObject params) throws IOException {
        return post(capabilities.ToolsCall(tool, params));
    }

    @Override
    public Response sendPromptsGet(String prompt, JSONObject params) throws IOException {
        return post(capabilities.promptsGet(prompt, params));
    }

    @Override
    public Response sendResourcesRead(String resource, JSONObject params) throws IOException {
        return post(capabilities.resourcesRead(resource));
    }

}
