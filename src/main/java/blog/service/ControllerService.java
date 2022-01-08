package blog.service;

import blog.config.Configuration;
import blog.domain.FinalResponse;
import blog.domain.Post;
import blog.domain.User;
import blog.service.post.PostService;
import blog.service.user.UserService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class ControllerService {
    private final Configuration config = new Configuration();

    private final Connection con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/blog?useSSL=FALSE",
            config.getName(),
            config.getPass());

    Statement statement = con.createStatement();
    PostService postService = new PostService(statement);
    UserService userService = new UserService(statement);

    Map<String, String> givenParams;

    private String action;
    private int code;
    private String JSONResponse;

    public ControllerService() throws SQLException {
    }

    public FinalResponse processRequest(HttpExchange t) throws SQLException {
        code = 400;
        JSONResponse = "Your request could not be processed.";

        setAction(t);
        getResponseFromDb(action);

        return new FinalResponse(
                code, JSONResponse
        );
    }


    private void setAction(HttpExchange t) throws SQLException {

        String baseURI = t.getRequestURI().getPath();
        if (!baseURI.equals("/blog.php")) {
            action = "error";
            return;
        }

        String queryURI = t.getRequestURI().getQuery();
        if (queryURI == null) {
            action = "null";
        } else {
            String[] params = queryURI.split("&");
            Map<String, String> requestMap = new HashMap<>();


            try {
                for (String param : params) {
                    String name = param.split("=")[0];
                    String value = param.split("=")[1];
                    requestMap.put(name, value);
                }
                action = requestMap.get("action") == null ? "error" : requestMap.get("action");

                givenParams = requestMap;

            } catch (Exception e) {
                action = "error";
            }
        }
    }


    private void getResponseFromDb(String action) throws SQLException {

        switch (action) {
            case "new_user":
                createNewUser();
                break;
            case "delete":
                deletePost();
                break;

            case "new":
                addPost();
                break;
            case "login":
                login();
                break;
            case "null":
                getPosts();
                break;
            case "error":
                setError();
        }
    }

    private void login() throws SQLException {
        String name = givenParams.get("user");
        String password = givenParams.get("password");

        if (userService.authenticate(name, password) == 1) {
            code = 200;
            JSONResponse = "Welcome!";
        } else {
            code = 403;
            JSONResponse = "Wrong Credentials!";
        }

    }

    private void setError() {
        JSONResponse = "Bad Request!";
    }

    private void addPost() throws SQLException {
        String txt = givenParams.get("text");
        postService.add(txt);
        if (postService.add(txt) == 1) {
            code = 200;
            JSONResponse = "Post Has been added!";
        }
    }

    private void deletePost() throws SQLException {

        int id = Integer.parseInt(givenParams.get("id"));
        if (postService.deleteById(id) == 1) {
            code = 200;
            JSONResponse = "Post has been successfully deleted!";
        } else {
            code = 400;
            JSONResponse = "Post has not been found!";
        }
    }

    private void createNewUser() throws SQLException {
        String username = givenParams.get("username");
        String pass = givenParams.get("password");
        String permission = givenParams.get("permission");
        String readonly = givenParams.get("readonly");

        if (userService.existByUsernameAndPassword(username, pass)) {
            code = 409;
            JSONResponse = "User already exists in the database!";

        } else {
            User user = userService.addUser(username, pass, permission, readonly);
            code = 200;
            JSONResponse = convertToJSON(user);
        }
    }


    public void getPosts() throws SQLException {
        code = 200;

        JSONResponse = convertToJSON(postService.getPosts());
    }

    public Post getOne(int id) throws SQLException {
        return postService.getOne(id);
    }

    private String convertToJSON(Object input) {
        Gson gson = new Gson();
        return gson.toJson(input);
    }
}



