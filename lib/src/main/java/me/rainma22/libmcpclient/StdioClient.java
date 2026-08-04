package me.rainma22.libmcpclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;
import java.util.UUID;

import org.json.JSONObject;

import me.rainma22.jsonrpc.Request;
import me.rainma22.jsonrpc.Response;

public class StdioClient implements McpClient {
    private McpClientCapabilities capabilities;
    private Scanner scanner;
    private OutputStream out;

    public StdioClient(McpClientCapabilities c, InputStream i, OutputStream o) {
        capabilities = c;
        scanner = new Scanner(i);
        out = o;
    }

    private Response readInput() throws IOException {
        while (true) {
            while (!scanner.hasNextLine()) {
                try{
                    Thread.sleep(100);
                }catch(InterruptedException e){
                    //ignored
                }
            }
            JSONObject object = new JSONObject(scanner.nextLine());
            if(object.getString("method").startsWith("notification")){
                continue;
                // skip notification
            } else {
                return new Response(object);
            }
        }
    }

    @Override
    public Response sendPing() throws IOException {
        out.write(capabilities.ping().toString().getBytes());
        out.flush();

        return readInput();
    }

    @Override
    public Response sendInitialize() throws IOException {
        out.write(capabilities.initialize().toString().getBytes());
        out.flush();
        return readInput();
    }

    @Override
    public Response sendListTools() throws IOException {
        out.write(capabilities.listTools().toString().getBytes());
        out.flush();
        return readInput();
    }

    @Override
    public Response sendListPrompts() throws IOException {
        out.write(capabilities.listPrompts().toString().getBytes());
        out.flush();
        return readInput();
    }

    @Override
    public Response sendListResources() throws IOException {
        out.write(capabilities.listResources().toString().getBytes());
        out.flush();
        return readInput();
    }

    @Override
    public void sendNotification(String notif) throws IOException {
        out.write(new Request(UUID.randomUUID(), notif).toString().getBytes());
        out.flush();
    }

}
