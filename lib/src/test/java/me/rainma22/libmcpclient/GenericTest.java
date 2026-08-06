package me.rainma22.libmcpclient;

import static org.junit.jupiter.api.Assertions.fail;

import java.net.URI;
import java.util.stream.Stream;

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

    @BeforeEach
    public void setup() {
        this.serverURI = URI.create("http://localhost:9090/mcp");
    }

    @Test
    public void test0() {
        try {
            var client = new StreamableHttpClient(Capabilities.CAPABILITIES_MAP.get("2025-11-25"),
                    serverURI.toURL());
            Stream.of(
                    client.sendPing(),
                    client.sendInitialize(),
                    client.sendListPrompts(),
                    client.sendListResources()).forEach(System.out::println);
            client.sendListTools().getTools().stream()
                    .forEach(System.out::println);
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void test1() {
        try {
            StatelessClient client = new StatelessClient(Capabilities.CAPABILITIES_MAP.get("2025-11-25"),
                    URI.create("http://localhost:9090/mcp").toURL());
            Stream.of(
                    client.sendPing(),
                    client.sendInitialize(),
                    client.sendListPrompts(),
                    client.sendListResources()).forEach(System.out::println);
            client.sendListTools().getTools().stream()
                    .forEach(System.out::println);
        } catch (Exception e) {
            fail(e);
        }
    }

}
