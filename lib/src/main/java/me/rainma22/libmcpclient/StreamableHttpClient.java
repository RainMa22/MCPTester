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
import java.util.stream.Stream;

import org.json.JSONObject;

import me.rainma22.constants.Headers;
import me.rainma22.constants.MimeTypes;
import me.rainma22.jsonrpc.Request;
import me.rainma22.jsonrpc.Response;
import me.rainma22.jsonrpc.ToolListResponse;
import me.rainma22.serversentevents.SSEChunk;
import me.rainma22.utils.JSONUtils;

public class StreamableHttpClient implements McpClient {
    private McpClientCapabilities capabilities;
    private HttpClient client = HttpClient.newHttpClient();
    private URL url;
    private String sessionId = null;

    public StreamableHttpClient(McpClientCapabilities c, URL url) {
        capabilities = c;
        this.url = url;

    }

    private Response post(String s) throws IOException {
        try {
            return client.sendAsync(HttpRequest.newBuilder(url.toURI())
                    .header("Accept", "application/json")
                    .header("Accept", "text/event-stream")
                    .headers(capabilities.capabilitySpecificHttpHeaders(sessionId))
                    .POST(BodyPublishers.ofString(s))
                    .build(), BodyHandlers.ofString())
                    .thenApplyAsync((res) -> {
                        String contentType = res.headers().firstValue(Headers.CONTENT_TYPE_FIELD)
                                .orElse(null);
                        if (MimeTypes.JSON_MIMETYPE.equals(contentType)) {
                            return new Response(new JSONObject(res.body()));
                        } else if (MimeTypes.EVENT_STREAM_MIMETYPE.equals(contentType)) {
                            var jsonObj = Stream.of(res.body().split("\n\n"))
                                    .map(SSEChunk::fromString)
                                    .map(SSEChunk::getData)
                                    .filter(JSONUtils::isValidJSON)
                                    .map(JSONObject::new)
                                    .filter(obj -> !obj.optString("method", "not a method").startsWith("notification"))
                                    .findFirst()
                                    .get();
                            this.sessionId = capabilities.getSessionId(res);
                            return new Response(jsonObj);
                        } else {
                            throw new RuntimeException(String.format("Bad Content-Type field: %s.", contentType));
                        }
                    })
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
    public ToolListResponse sendListTools() throws IOException {
        return new ToolListResponse(post(capabilities.listTools()));
    }

    @Override
    public Response sendListPrompts() throws IOException {
        return post(capabilities.listPrompts());
    }

    @Override
    public Response sendListResources() throws IOException {
        return post(capabilities.listResources());
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