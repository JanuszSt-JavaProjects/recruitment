package blog.domain;

import java.util.Objects;

public class User {
    private int id;
    private String username;
    private String password;
    private String permission;
    private String readonly;

    public User(int id, String username, String password, String permission, String readonly) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.permission = permission;
        this.readonly = readonly;
    }

    public User(String username, String password, String permission, String readonly) {
        this.username = username;
        this.password = password;
        this.permission = permission;
        this.readonly = readonly;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", permission='" + permission + '\'' +
                ", readonly='" + readonly + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(permission, user.permission) && Objects.equals(readonly, user.readonly);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, permission, readonly);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getReadonly() {
        return readonly;
    }

    public void setReadonly(String readonly) {
        this.readonly = readonly;
    }
}
