package com.example.lesson9.test;

public class Test {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Test(String title, String message) {
        this.title = title;
        this.message = message;
    }

    private String title;
    private String message;

}
