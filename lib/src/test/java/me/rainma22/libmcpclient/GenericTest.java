package me.rainma22.libmcpclient;

import static org.junit.jupiter.api.Assertions.fail;

import java.net.URI;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public class GenericTest {
    @Test
    public void test0() {
        try {
            var client = new StreamableHttpClient(Capabilities.CAPABILITIES_MAP.get("2025-11-25"),
                    URI.create("http://localhost:9090/mcp").toURL());
            Stream.of(
                    client.sendPing(),
                    client.sendInitialize(),
                    client.sendListPrompts(),
                    client.sendListResources(),
                    client.sendListTools()).forEach(System.out::println);
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void test1() {
        try {
            JSONClient client = new JSONClient(Capabilities.CAPABILITIES_MAP.get("2025-11-25"),
                    URI.create("http://localhost:9090/mcp").toURL());
            Stream.of(
                    client.sendPing(),
                    client.sendInitialize(),
                    client.sendListPrompts(),
                    client.sendListResources(),
                    client.sendListTools()).forEach(System.out::println);
        } catch (Exception e) {
            fail(e);
        }
    }

}
