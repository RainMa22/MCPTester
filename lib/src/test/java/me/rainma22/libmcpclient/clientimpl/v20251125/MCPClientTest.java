package me.rainma22.libmcpclient.clientimpl.v20251125;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import me.rainma22.libmcpclient.Capabilities;
import me.rainma22.libmcpclient.McpClient;
import me.rainma22.libmcpclient.StatelessClient;
import me.rainma22.libmcpclient.StreamableHttpClient;
import me.rainma22.utils.Toolinfo;

public class MCPClientTest {

    MockMCPServer server = null;
    McpClient[] clients = new McpClient[2];

    @BeforeEach
    public void beforeEach() {
        try {
            if (server == null) {
                server = MockMCPServer.run();
            }
            if (clients[0] == null) {
                var serverUri = new URI("http", "", "localhost", server.getPort(), "/mcp", "", "");
                clients[0] = new StatelessClient(Capabilities.CAPABILITIES_MAP.get("2025-11-25"), serverUri.toURL());
                clients[1] = new StreamableHttpClient(Capabilities.CAPABILITIES_MAP.get("2025-11-25"),
                        serverUri.toURL());
            }
        } catch (URISyntaxException | IOException e) {
            fail(e);
        }
        for (List<?> l : List.of(server.getTools(), server.getPrompts(),
                server.getResources(), server.getResourceTemplates())) {
            l.clear();
        }
    }

    @Test
    public void testToolsList() {
        for (var client : clients) {
            try {
                assertTrue(client.sendListTools().getTools().isEmpty());
            } catch (IOException e) {
                fail(e);
            }
        }
        Toolinfo newTool = new Toolinfo();
        newTool.setName("test");
        newTool.setTitle("test1");
        server.getTools().add(newTool);
        for (var client : clients) {
            try {
                var newToolList = client.sendListTools().getTools();
                assertFalse(newToolList.isEmpty());
                assertTrue(newToolList.size() == 1);
                assertEquals("test", newToolList.get(0).getName());
                assertEquals("test1", newToolList.get(0).getTitle());
            } catch (IOException e) {
                fail(e);
            }
        }
    }

    void runWithClient(McpClient client) throws IOException {
        Map.of(
                "ping", client.sendPing(),
                "initialize", client.sendInitialize(),
                "Resources List", client.sendListResources())
                .forEach((k, v) -> {
                    System.out.print(k.toString() + ": ");
                    System.out.println(v);
                });

        System.out.println("Tools List: ");
        client.sendListTools().getTools().stream()
                .forEach(System.out::println);

        System.out.println("Prompts: List");
        client.sendListPrompts().getPrompts().stream()
                .forEach(System.out::println);

        System.out.println("Resources List");
        client.sendListResources().getResources().stream()
                .forEach(System.out::println);
        client.sendListResourceTemplates().getResourceTemplates().stream()
                .forEach(System.out::println);

    }

}
