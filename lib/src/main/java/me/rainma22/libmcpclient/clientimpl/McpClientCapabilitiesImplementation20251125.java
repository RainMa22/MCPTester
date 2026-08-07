package me.rainma22.libmcpclient.clientimpl;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.JSONObject;

import me.rainma22.jsonrpc.Request;
import me.rainma22.libmcpclient.McpClientCapabilities;

public class McpClientCapabilitiesImplementation20251125 implements McpClientCapabilities {
    private static final String MCP_SESSION_ID_FIELD = "MCP-Session-Id";

    @Override
    public String getVersion() {
        return "2025-11-25";
    }

    @Override
    public String[] capabilitySpecificHttpHeaders(String sessionId) {
        List<String> l = new ArrayList<>(List.of("MCP-Protocol-Version", getVersion()));
        l.addAll(sessionId != null ? List.of("MCP-Session-Id", sessionId) : List.of());
        return l.toArray(String[]::new);
    }

    @Override
    public <T> String getSessionId(HttpResponse<T> res) {
        var fields = res.headers().map().get(MCP_SESSION_ID_FIELD);
        return fields == null ? null : fields.getFirst();
    }

    @Override
    public Request serverDiscover() {
        throw new UnsupportedOperationException("Not supported for version " + this.getVersion());
    }

    @Override
    public Request ping() {
        return new Request(UUID.randomUUID(), "ping");
    }

    @Override
    public Request initialize() {
        var req = new Request(UUID.randomUUID(), "initialize");
        req.setParams(new JSONObject(Map.of(
                "protocolVersion", "2025-11-25",
                "capabilities", Map.of(),
                "clientInfo", Map.of(
                        "name", "testClient",
                        "title", "Client",
                        "version", "0.0.1",
                        "description", "an MCP client",
                        "icons", List.of()))));
        return req;
    }

    @Override
    public Request listTools() {
        return new Request(UUID.randomUUID(), "tools/list");
    }

    @Override
    public Request listPrompts() {
        return new Request(UUID.randomUUID(), "prompts/list");
    }

    @Override
    public Request listResources() {
        return new Request(UUID.randomUUID(), "resources/list");
    }

    @Override
    public Request listResourceTemplates() {
        return new Request(UUID.randomUUID(), "resources/templates/list");
    }

    @Override
    public Request ToolsCall(String tool, JSONObject args) {
        return new Request(UUID.randomUUID(), "tools/call",
                new JSONObject(Map.of(
                        "name", tool,
                        "arguments", args)));
    }

    @Override
    public Request promptsGet(String prompt, JSONObject args) {
        return new Request(UUID.randomUUID(), "prompts/get",
                new JSONObject(Map.of(
                        "name", prompt,
                        "arguments", args)));
    }

    @Override
    public Request resourcesRead(String resourceURI) {
        return new Request(UUID.randomUUID(), "resources/reads",
                new JSONObject(
                        Map.of("uri", resourceURI)));
    }

}