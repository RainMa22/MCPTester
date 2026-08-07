package me.rainma22.libmcpclient;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 
 * GenericTest
 * <br>
 * Requires having an MCPServer to test against at http://localhost:9090/mcp
 */
public class GenericTest {
    private URI serverURI;
    private String divider;

    @BeforeEach
    public void setup() {
        String[] dividerArr = new String[32];
        Arrays.fill(dividerArr, "-");
        divider = String.join("", dividerArr);
        this.serverURI = URI.create("http://localhost:9090/mcp");
    }

    void runWithClient(McpClient client) throws IOException {
        Map.of(
                "ping", client.sendPing(),
                "initialize", client.sendInitialize(),
                "Resources List", client.sendListResources())
                .forEach((k, v) -> {
                    System.out.println(divider);
                    System.out.print(k.toString() + ": ");
                    System.out.println(v);
                });

        System.out.println("Tools List: ");
        client.sendListTools().getTools().stream()
                .forEach(System.out::println);
        System.out.println(divider);
        System.out.println("Prompts: List");
        client.sendListPrompts().getPrompts().stream()
                .forEach(System.out::println);

    }

    @Test
    public void test0() {
        try {
            var client = new StreamableHttpClient(Capabilities.CAPABILITIES_MAP.get("2025-11-25"),
                    serverURI.toURL());
            runWithClient(client);
        } catch (IOException e) {
            fail(e);
        }
    }

    @Test
    public void test1() {
        try {
            StatelessClient client = new StatelessClient(Capabilities.CAPABILITIES_MAP.get("2025-11-25"),
                    URI.create("http://localhost:9090/mcp").toURL());
            runWithClient(client);
        } catch (IOException e) {
            fail(e);
        }
    }

}
