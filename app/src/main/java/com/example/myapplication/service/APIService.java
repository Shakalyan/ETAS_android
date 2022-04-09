package com.example.myapplication.service;

import com.example.myapplication.model.Response;

public abstract class APIService {

    private static Response response;

    static {
        response = null;
    }

    public static Response retrieveResponse() {
        Response outR = response;
        response = null;
        return outR;
    }

    public static void setResponse(Response resp) {
        response = resp;
    }

    public static boolean responseIsPresent() {
        return response != null;
    }

}
