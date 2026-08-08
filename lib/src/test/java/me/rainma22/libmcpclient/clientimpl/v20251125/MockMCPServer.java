package me.rainma22.libmcpclient.clientimpl.v20251125;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpServer;

import me.rainma22.constants.MimeTypes;
import me.rainma22.jsonrpc.Request;
import me.rainma22.jsonrpc.Response;
import me.rainma22.utils.PromptInfo;
import me.rainma22.utils.ResourceInfo;
import me.rainma22.utils.ResourceTemplateInfo;
import me.rainma22.utils.Toolinfo;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * MockMCPServer <br>
 * Mock MCP 2025-11-25 server but stateless <br>
 * (does not check session and returns json only) <br>
 * (no pagination yet)
 */
public class MockMCPServer implements AutoCloseable {
    private HttpServer server;
    private List<Toolinfo> tools = new ArrayList<>();
    private List<PromptInfo> prompts = new ArrayList<>();
    private List<ResourceInfo> resources = new ArrayList<>();
    private List<ResourceTemplateInfo> resourceTemplates = new ArrayList<>();

    public int getPort() {
        return server.getAddress().getPort();
    }

    private MockMCPServer() {

    }

    public static MockMCPServer run() throws IOException {
        var mocker = new MockMCPServer();
        var addr = new InetSocketAddress(0);
        mocker.server = HttpServer.create(addr, 0);
        mocker.server.createContext("/mcp", mocker.getHandler());
        mocker.server.start();
        return mocker;
    }

    public List<Toolinfo> getTools() {
        return tools;
    }

    public List<PromptInfo> getPrompts() {
        return prompts;
    }

    public List<ResourceInfo> getResources() {
        return resources;
    }

    public List<ResourceTemplateInfo> getResourceTemplates() {
        return resourceTemplates;
    }

    public MockStatelessMCPHandler getHandler() {
        return new MockStatelessMCPHandler();
    }

    private final class MockStatelessMCPHandler implements HttpHandler {
        private static final String VERSION = "2025-11-25";
        private static final Map<String, Object> SERVER_INFO = Map.of(
                "name", "ExampleServer",
                "title", "Example Server Display Name",
                "version", "1.0.0",
                "description", "An example MCP server providing tools and resources",
                "icons", List.of(),
                "websiteUrl", "");
        private static final Map<String, Object> CAPABILITIES = Map.of(
                "prompts", Map.of(
                        "listChanged", true),
                "resources", Map.of(
                        "subscribe", true,
                        "listChanged", true),
                "tools", Map.of(
                        "listChanged", true));

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            var req = Request.fromJSON(
                    new JSONObject(new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8)));
            exchange.getResponseHeaders()
                    .add("Content-Type", MimeTypes.JSON_MIMETYPE);
            byte[] outData = handleMethod(req).getObject().toString()
                    .getBytes();
            exchange.sendResponseHeaders(200, outData.length);
            exchange.getResponseBody()
                    .write(outData);
            exchange.close();
        }

        private MCPResponse handleMethod(Request req) {

            Function<Object, MCPResponse> makeResponse = (result) -> new MCPResponse(req.getId(), result);

            switch (req.getMethod()) {
                case "ping":
                    makeResponse.apply(null);
                case "initalize":
                    return makeResponse.apply(Map.of(
                            "protocolVersion", VERSION,
                            "capabilities", CAPABILITIES,
                            "serverInfo", SERVER_INFO));
                case "tools/list":
                    return makeResponse.apply(Map.of("tools", tools));
                case "prompts/list":
                    return makeResponse.apply(Map.of("prompts",prompts));
                case "resources/list":
                    return makeResponse.apply(Map.of("resources",resources));
                case "resources/templates/list":
                    return makeResponse.apply(Map.of("resourceTemplates",resourceTemplates));
                default:
                    var res = makeResponse.apply(null);
                    res.getObject().put("error",
                            Map.of("code", -32602,
                                    "message", "bad method"));
                    return res;
            }

        }
    }

    private final class MCPResponse extends Response {

        public MCPResponse(String id, Object result) {
            super(new JSONObject(
                    Map.of("id", id,
                            "result", result)));
        }

        public JSONObject getObject() {
            return object;
        }

    }

    @Override
    public void close() throws Exception {
        server.stop(0);
    }
}
