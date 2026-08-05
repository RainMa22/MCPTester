package me.rainma22.libmcpclient;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.JSONObject;

import me.rainma22.jsonrpc.Request;

public class Capabilities {
    public static final Map<String, McpClientCapabilities> CAPABILITIES_MAP = Map.of(
            "2025-11-25", new McpClientCapabilities() {
                private static final String MCP_SESSION_ID_FIELD = "MCP-Session-Id";

                @Override
                public String getVersion() {
                    return "2025-11-25";
                }

                @Override
                public String[] capabilitySpecificHttpHeaders(String sessionId){
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

            });

}
