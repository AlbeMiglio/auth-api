package it.albemiglio.authapi.services;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import it.albemiglio.authapi.AuthAPI;
import org.bukkit.Bukkit;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class WebService {

    private static AuthAPI plugin;

    public WebService(AuthAPI main) {
        plugin = main;
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(plugin.getConfigService().readPort()), 0);
            server.createContext("/auth", new AuthHandler());
            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class AuthHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                InputStream is = exchange.getRequestBody();
                String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                JSONObject jsonRequest = new JSONObject(body);

                String username = jsonRequest.getString("username");
                String pwd = jsonRequest.getString("password");
                String secretKey = jsonRequest.getString("secret_key");

                int isValid = plugin.getModuleService().authenticate(username, pwd, secretKey);
                Bukkit.getLogger().info("Received authentication request for user " + username + " with result " + isValid);

                JSONObject jsonResponse = new JSONObject();
                jsonResponse.put("status", isValid == -1 ? "wrong token" : isValid == 1 ? "valid" : "invalid");

                String response = jsonResponse.toString();
                exchange.getResponseHeaders().add("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        }
    }
}
