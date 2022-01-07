package blog.service;

import blog.config.Configuration;
import blog.domain.Post;
import blog.service.post.PostService;
import blog.service.user.UserService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DbRequestService {
    private final Configuration config = new Configuration();

    private final Connection con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/blog?useSSL=FALSE",
            config.getName(),
            config.getPass());

    Statement statement = con.createStatement();
    PostService postService = new PostService(statement);
    UserService userService = new UserService(statement);


    public DbRequestService() throws SQLException {
    }


    public List<Post> getPosts() throws SQLException {
        return postService.getPosts();
    }

    public Post getOne(int id) throws SQLException {
        return postService.getOne(id);
    }
}



