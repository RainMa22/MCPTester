package me.rainma22.libmcpclient;

import java.util.Map;

import me.rainma22.libmcpclient.clientimpl.McpClientCapabilitiesImplementation20251125;

public class Capabilities {
    public static final Map<String, McpClientCapabilities> CAPABILITIES_MAP = Map.of(
            "2025-11-25", new McpClientCapabilitiesImplementation20251125());

}
