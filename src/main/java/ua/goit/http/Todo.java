package ua.goit.http;

public class Todo {
    private int userId;
    private int id;
    private String title;
    private boolean completed;

    public Todo(int userId, int id, String title, boolean completed) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.completed = completed;
    }


    public boolean isCompleted() {
        return completed;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "ToDos{" +
                "userId=" + userId +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", completed=" + completed +
                '}';
    }
}


