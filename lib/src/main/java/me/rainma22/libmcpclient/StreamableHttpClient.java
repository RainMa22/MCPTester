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

import org.json.JSONException;
import org.json.JSONObject;

import me.rainma22.jsonrpc.Request;
import me.rainma22.jsonrpc.Response;
import me.rainma22.serversentevents.SSEChunk;

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
                    .thenApply((res) -> {
                        System.out.println(res.body());
                        var text = Stream.of(res.body().split("\n\n"))
                                .map(SSEChunk::fromString)
                                .map(c -> c.getData())
                                .filter(d -> {
                                    try {
                                        new JSONObject(d);
                                        return true;
                                    } catch (JSONException je) {
                                        return false;
                                    }
                                })
                                .findFirst()
                                .get();

                        this.sessionId = capabilities.getSessionId(res);
                        return new Response(new JSONObject(text));
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
    public Response sendListTools() throws IOException {
        return post(capabilities.listTools());
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
}