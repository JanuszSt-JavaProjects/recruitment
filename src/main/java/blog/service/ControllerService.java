package blog.service;

import blog.config.Configuration;
import blog.domain.FinalResponse;
import blog.domain.Post;
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

        code=403;
        JSONResponse ="No permission!";

        setAction(t);
        getResponseFromDb(action);

        return new FinalResponse(
                code, JSONResponse
        );


    }

    private void setAction(HttpExchange t) {

        String stringURI = t.getRequestURI().getQuery();
        if (stringURI == null) {
            action = "null";
        } else {
            String[] params = stringURI.split("&");
            Map<String, String> requestMap = new HashMap<>();

            for (String param : params) {
                String name = param.split("=")[0];
                String value = param.split("=")[1];
                requestMap.put(name, value);
            }


            action = requestMap.get("action");
            givenParams = requestMap;
        }

    }

    private void getResponseFromDb(String action) throws SQLException {

        switch (action) {
            case "new_user":
                createNewUser();
                break;
 /*           case "delete":
                deletePost();
                break;

            case "new":
                addPost();
                break;
            case "login":
                login();
                break;*/
            case "null":
                getPosts();
        }
    }

    private void createNewUser() throws SQLException {
        String username =givenParams.get("username");
        String pass = givenParams.get("password");
        String permission =givenParams.get("permission");
        String readonly =givenParams.get("readonly");

        System.out.println("1  :\n" +username +" "+pass+ " "+permission+" "+readonly);

       if (userService.existByUsernameAndPassword( username,  pass) ){
           code=409;
           JSONResponse ="User already exists in the database!";

           System.out.println(" 1: exc wyjście  ifa =========>");
       }else{
           System.out.println("1: normalna proc =========>");
           userService.addUser(username,pass,permission,readonly);
       }


        /*{password=test, readonly=yes, action=new_user, permission=superuser, username=test}
         */

    }


    public void getPosts() throws SQLException {
        code = 200;

       JSONResponse =convertToJSON(postService.getPosts());
    }

    public Post getOne(int id) throws SQLException {
        return postService.getOne(id);
    }

    private String convertToJSON(Object input) {
        Gson gson = new Gson();
        return gson.toJson(input);
    }
}



