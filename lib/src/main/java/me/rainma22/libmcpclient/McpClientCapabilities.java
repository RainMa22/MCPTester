package me.rainma22.libmcpclient;


import me.rainma22.jsonrpc.Request;

public interface McpClientCapabilities {    
    Request ping();
    Request initialize();
    Request listTools();
    Request listPrompts();
    Request listResources();
}