package blog.controller;

import blog.service.DbRequestService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Controller implements HttpHandler {

    DbRequestService dbRequestService = new DbRequestService();
    private String action;

    public Controller() throws SQLException {
    }

    @Override
    public void handle(HttpExchange t) throws IOException {

        int code = 200;
        String stringTSend;

        setAction(t);


        try {
            Object obj = getResponseFromDb(action);
            stringTSend = returnConvertedToJSON(obj);
            t.sendResponseHeaders(code, stringTSend.length());
            OutputStream os = t.getResponseBody();
            os.write(stringTSend.getBytes(StandardCharsets.UTF_8));
            os.close();
        } catch (IOException | SQLException e) {
            {
                code = 404;
                stringTSend = " No matching record !";
                t.sendResponseHeaders(code, stringTSend.length());
                OutputStream os = t.getResponseBody();
                os.write(stringTSend.getBytes(StandardCharsets.UTF_8));
                os.close();
            }
        } finally {
            t.close();
        }


    }

    private String returnConvertedToJSON(Object returnedObj) throws SQLException {

        return convertToJSON(returnedObj);

    }

    private Object getResponseFromDb(String action) throws SQLException {

        switch (action) {
/*            case "new_user":
                createNewUser();
            case "delete":
                deletePost();
            case "new":
                addPost();
            case "login":
                login();*/
            case "null":
                return dbRequestService.getPosts();
        }
        return null;
    }


    private String convertToJSON(Object input) {
        Gson gson = new Gson();
        return gson.toJson(input);
    }

    private void setAction(HttpExchange t) {

        Map<String, String> getQueryMap = new HashMap<>();
        String stringURI = t.getRequestURI().getQuery();
        if (stringURI == null) {
            action = "null";
        } else {
            String[] params = stringURI.split("&");
            Map<String, String> requestMap = new HashMap<>();

            for (String param : params) {
                String name = param.split("=")[0];
                String value = param.split("=")[1];
                System.out.println(name + "  " + value);
                requestMap.put(name, value);
            }
            action = requestMap.get("action");
        }

    }
}
