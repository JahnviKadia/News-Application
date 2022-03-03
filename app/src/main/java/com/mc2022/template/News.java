package com.mc2022.template;

public class News {

    private int number;
    public String title;
    public String body;
    public String imageURL;

    public int getNumber() {
        return number;
    }

    public News(){

    }

    public News(int number, String title, String body, String imageURL) {
        this.number = number;
        this.title = title;
        this.body = body;
        this.imageURL = imageURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

