package blog.service.user;

import blog.domain.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserService {

    Statement statement;

    public UserService(Statement stmt) {
        statement = stmt;
    }

    public boolean existByUsernameAndPassword(String username, String pass) throws SQLException {

        User user = findByNameAndPassword(username, pass);
        if (user.getUsername().equals("-1")) {
            return false;
        }

        return true;
    }

    public User addUser(String username, String password, String permission, String readonly) throws SQLException {

        String query = "insert into user Values (?,?,?,?,?)";
        PreparedStatement preparedStatement = statement.getConnection().prepareStatement(query);
        preparedStatement.setString(1, null);
        preparedStatement.setString(2, username);
        preparedStatement.setString(3, password);
        preparedStatement.setString(4, permission);
        preparedStatement.setString(5, readonly);

        preparedStatement.execute();
        return findByNameAndPassword(username, password);
    }

    public User findByNameAndPassword(String username, String password) throws SQLException {

        String query = "select * from user where username = (?)" + " and password =(?)";
        PreparedStatement preparedStatement = statement.getConnection().prepareStatement(query);

        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);

        ResultSet execute = preparedStatement.executeQuery();

        User user = new User();
        if (execute.next()) {
            user.setId(execute.getInt("userid"));
            user.setPassword(execute.getString("password"));
            user.setUsername(execute.getString("username"));
            user.setPermission(execute.getString("permission"));
            user.setReadonly(execute.getString("readonly"));

            return user;
        }
        user.setUsername("-1");
        return user;
    }

    public int authenticate(String username, String password) throws SQLException {

        PreparedStatement preparedStatement = statement.getConnection()
                .prepareStatement("select count(*) as numbers from user where username =(?) and password=(?)");
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt("numbers");
    }

}
