package com.techbyking.flashchatnewfirebase;

/**
 * Created by K2_Da on 3/10/2018.
 */

public class InstantMessage {

    private String message;
    private String author;

    public InstantMessage(String message, String author) {
        this.message = message;
        this.author = author;
    }

    public InstantMessage() {

    }

    public String getMessage() {
        return message;
    }


    public String getAuthor() {
        return author;
    }

}
