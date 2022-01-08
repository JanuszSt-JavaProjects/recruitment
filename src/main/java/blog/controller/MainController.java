package blog.controller;

import blog.domain.FinalResponse;
import blog.service.ControllerService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class MainController implements HttpHandler {

    private final ControllerService controllerService = new ControllerService();

    public MainController() throws SQLException {
    }

    @Override
    public void handle(HttpExchange t) throws IOException {

        try {
            FinalResponse finalResponse = controllerService.processRequest(t);
            t.sendResponseHeaders(
                    finalResponse.getCode(),
                    finalResponse.getStringToSend().length());
            OutputStream os = t.getResponseBody();
            os.write(finalResponse.getStringToSend().getBytes(StandardCharsets.UTF_8));
            os.flush();
            os.close();
        } catch (IOException | SQLException e) {
            {
                int code = 400;
                String string = "Your request could not be processed.";
                t.sendResponseHeaders(code, string.length());
                OutputStream os = t.getResponseBody();
                os.write(string.getBytes(StandardCharsets.UTF_8));
                os.flush();
                os.close();
            }
        } finally {

            t.close();
        }
    }
}
