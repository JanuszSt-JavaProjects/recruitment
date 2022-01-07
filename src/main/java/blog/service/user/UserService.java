package blog.service.user;

import blog.domain.User;

import java.sql.*;

public class UserService {

    Statement statement;

    public UserService(Statement stmt) {
        statement = stmt;
    }


    public boolean existByUsernameAndPassword(String username, String pass) throws SQLException {

        String query = "select count(*) as existingUsers from user where username = (?)"+" and password =(?)";
        PreparedStatement preparedStatement = statement.getConnection().prepareStatement(query);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, pass);

        ResultSet execute = preparedStatement.executeQuery();

        execute.next();
        boolean answer = execute.getInt("existingUsers")>0;
        return answer;
    }

    public User addUser(String username, String password, String permission, String readonly) throws SQLException {

        User user = new User();

        String query = "insert into user Values (?,?,?,?,?)";
        PreparedStatement preparedStatement = statement.getConnection().prepareStatement(query);
        preparedStatement.setString(1, null);
        preparedStatement.setString(2, username);
        preparedStatement.setString(3, password);
        preparedStatement.setString(4, permission);
        preparedStatement.setString(5, readonly);
        preparedStatement.execute();

        return null;
    }
}
