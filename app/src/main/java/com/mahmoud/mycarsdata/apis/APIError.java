package com.mahmoud.mycarsdata.apis;

public class APIError {

    private int statusCode;
    private String message;
    private String endPoint;

    public APIError() {
    }

    public int status() {
        return statusCode;
    }

    public String message() {
        return message;
    }

    public String endPoint() {
        return endPoint;
    }
}
