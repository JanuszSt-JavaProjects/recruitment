package blog.service;

import blog.config.Configuration;
import blog.domain.Post;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DbRequestService {
    private final Configuration config = new Configuration();

    private final Connection con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/blog?useSSL=FALSE",
            config.getName(),
            config.getPass());

    Statement statement = con.createStatement();


    public DbRequestService() throws SQLException {
    }


    public List<Post> getPosts() throws SQLException {
        List<Post> posts = new LinkedList<>();

        ResultSet resultSet = statement.executeQuery("select * from blog");


        while (resultSet.next()) {
            Post post = new Post();
            post.setId(resultSet.getInt(1));
            post.setText(resultSet.getString("text"));
            post.setUserid(resultSet.getString("userId"));
            posts.add(post);
        }
        return posts;
    }


    public Post getOne(int id) throws SQLException {

        ResultSet resultSet = null;
        Post post = new Post();

            resultSet = statement.executeQuery("select * from blog where id =" + id);
            resultSet.next();
            post = new Post();
            post.setId(resultSet.getInt(1));

            post.setText(resultSet.getString("text"));
            post.setUserid(resultSet.getString("userId"));
            return post;


    }
}



