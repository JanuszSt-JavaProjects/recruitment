package blog.config;

import blog.controller.Controller;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;

public class Server {

    private static HttpServer server;

    public static void ExecuteStartServer() {

        try {
            server = HttpServer.create(new InetSocketAddress(8000), 0);
            server.createContext("/", new Controller());
            server.setExecutor(null);
            server.start();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void executeStopServer() {
        server.stop(10);
    }
}
