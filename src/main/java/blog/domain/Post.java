package blog.domain;

import java.util.Objects;

public class Post {
    private int id;
    private String text;
    private String userid;


    public Post(int id, String text, String userid) {
        this.id = id;
        this.text = text;
        this.userid = userid;
    }

    public Post() {
    }

    @Override
    public String toString() {
        return "Post{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", userid='" + userid + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id) && Objects.equals(text, post.text) && Objects.equals(userid, post.userid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, userid);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
