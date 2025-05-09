package com.daffafakhir.splashscreen;

public class Post {
    private String userEmail;
    private String message;
    private long timestamp;
    private String id; // Firestore document ID

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }


    public Post() {
        // Required empty constructor for Firestore
    }

    public Post(String userEmail, String message, long timestamp) {
        this.userEmail = userEmail;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
