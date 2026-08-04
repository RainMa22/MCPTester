package me.rainma22.libmcpclient;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.JSONObject;

import me.rainma22.jsonrpc.Request;

public class Capabilities {
    public static final Map<String, McpClientCapabilities> CAPABILITIES_MAP = Map.of(
            "20251125", new McpClientCapabilities() {

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
