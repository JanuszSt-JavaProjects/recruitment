package blog.service.post;

import blog.domain.Post;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class PostService {

    Statement statement;


    public PostService(Statement stmt) {
        statement = stmt;
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
        Post post;

        resultSet = statement.executeQuery("select * from blog where id =" + id);
        resultSet.next();
        post = new Post();
        post.setId(resultSet.getInt(1));

        post.setText(resultSet.getString("text"));
        post.setUserid(resultSet.getString("userId"));
        return post;


    }

}
