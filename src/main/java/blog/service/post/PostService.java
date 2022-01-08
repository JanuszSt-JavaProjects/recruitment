package blog.service.post;

import blog.domain.Post;

import java.sql.PreparedStatement;
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

    public int deleteById(int recordId) throws SQLException {
        String sql = "delete from blog where id= " + recordId;
        return statement.executeUpdate(sql);
    }

    public int add(String txt) throws SQLException {
        int FIXED_USER_ID = 0;

        PreparedStatement preparedStatement = statement.getConnection()
                .prepareStatement("insert into blog VALUES (null,?,?)");

        preparedStatement.setString(1, txt);
        preparedStatement.setInt(2, FIXED_USER_ID);

        return preparedStatement.executeUpdate();
    }
}
