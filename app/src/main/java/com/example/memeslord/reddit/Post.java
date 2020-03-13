package com.example.memeslord.reddit;

public class Post {
    private String title;
    private String author;
    private String postURL;
    private String thumbnailURL;
    private String imageURL;

    public Post(String title, String author, String postURL, String thumbnailURL, String imageURL) {
        this.title = title;
        this.author = author;
        this.postURL = postURL;
        this.thumbnailURL = thumbnailURL;
        this.imageURL = imageURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPostURL() {
        return postURL;
    }

    public void setPostURL(String postURL) {
        this.postURL = postURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
