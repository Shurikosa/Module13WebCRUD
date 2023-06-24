package ua.goit.http;

public class Comments {

    private String postId;
    private String id;
    private String name;
    private String email;
    private String body;

    public Comments(String postId, String id, String name, String email, String body){

        this.postId = postId;
        this.id = id;
        this.name = name;
        this.email = email;
        this.body = body;
    }

    public String getPostId() {
        return postId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getBody() {
        return body;
    }
    @Override
    public String toString() {
        return "CommentJPH{" +
                "postId='" + postId + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
