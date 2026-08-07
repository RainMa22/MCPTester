package me.rainma22.libmcpclient.clientimpl;

import java.net.http.HttpResponse;
import java.util.Map;
import java.util.UUID;

import org.json.JSONObject;

import me.rainma22.jsonrpc.Request;
import me.rainma22.libmcpclient.McpClientCapabilities;
import me.rainma22.utils.ClientInfo;

public class CapabilitiesImpl20260728 implements McpClientCapabilities {
    private static final String VERSION_STRING = "2026-07-28";

    private static final class RequestWithMetadata extends Request {
        public RequestWithMetadata(Object id, String method) {
            this(id, method, null);
        }

        public RequestWithMetadata(Object id, String method, JSONObject params) {
            super(id, method, params);

            super.object.put("_meta", Map.of(
                    "io.modelcontextprotocol/protocolVersion", VERSION_STRING,
                    "io.modelcontextprotocol/clientInfo", new ClientInfo(),
                    "io.modelcontextprotocol/clientCapabilities", new JSONObject()));
        }
    }

    @Override
    public String getVersion() {
        return VERSION_STRING;
    }

    @Override
    public String[] capabilitySpecificHttpHeaders(String sessionId) {
        return new String[] {};
    }

    @Override
    public <T> String getSessionId(HttpResponse<T> res) {
        // MCP is stateless since 2026-07-28(this version)
        return null;
    }

    @Override
    @Deprecated
    public Request ping() {
        throw new UnsupportedOperationException("Unimplemented method 'ping'");
    }

    @Override
    @Deprecated
    public Request initialize() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'initialize'");
    }

    @Override
    public Request serverDiscover() {
        return new RequestWithMetadata(UUID.randomUUID(), "server/discover");
    }

    @Override
    public Request listTools() {
        return new RequestWithMetadata(UUID.randomUUID(), "tools/list");
    }

    @Override
    public Request listPrompts() {
        return new RequestWithMetadata(UUID.randomUUID(), "prompts/list");
    }

    @Override
    public Request listResources() {
        return new RequestWithMetadata(UUID.randomUUID(), "resources/list");
    }

    @Override
    public Request listResourceTemplates() {
        return new RequestWithMetadata(UUID.randomUUID(), "resources/templates/list");
    }

    @Override
    public Request ToolsCall(String tool, JSONObject args) {
        return new RequestWithMetadata(UUID.randomUUID(), "tools/call",
                new JSONObject(Map.of(
                        "name", tool,
                        "arguments", args)));
    }

    @Override
    public Request promptsGet(String prompt, JSONObject args) {
        return new RequestWithMetadata(UUID.randomUUID(), "prompts/get",
                new JSONObject(Map.of(
                        "name", prompt,
                        "arguments", args)));
    }

    @Override
    public Request resourcesRead(String resourceURI) {
        return new RequestWithMetadata(UUID.randomUUID(), "resources/reads",
                new JSONObject(
                        Map.of("uri", resourceURI)));
    }

}
