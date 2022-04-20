package com.example.myapplication.service;

import android.util.Pair;

import com.example.myapplication.model.Request;
import com.example.myapplication.model.Response;
import com.example.myapplication.model.User;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class RequestService {

    private final static String DOMAIN = "https://etas-s.herokuapp.com";

    public static Response sendJSON(String path, String method, Map<String, String> params, User user, Object object ) throws IOException {
        String json = new Gson().toJson(new Request<Object>(user, object));
        HttpURLConnection connection = getConnection(buildURL(path, params));
        connection.setRequestMethod(method);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        connection.getOutputStream().write(json.getBytes(StandardCharsets.UTF_8));
        return getResponse(connection);
    }

    public static Response send(String path, String method, Map<String, String> params) throws IOException {
        HttpURLConnection connection = getConnection(buildURL(path, params));
        connection.setRequestMethod(method);
        return getResponse(connection);
    }

    private static String buildURL(String path, Map<String, String> params) {
        StringBuilder urlSb = new StringBuilder(DOMAIN + path);
        urlSb.append('?');
        for (String paramName : params.keySet()) {
            urlSb.append(paramName).append('=').append(params.get(paramName)).append('&');
        }
        urlSb.deleteCharAt(urlSb.length() - 1);
        return urlSb.toString();
    }

    private static HttpURLConnection getConnection(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        return connection;
    }

    private static Response getResponse(HttpURLConnection connection) throws IOException {
        int status = connection.getResponseCode();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;
        while((inputLine = reader.readLine()) != null) {
            response.append(inputLine);
        }
        connection.disconnect();
        if (status != 200)
            return new Response(status, response.toString());
        else
            return new Gson().fromJson(response.toString(), Response.class);
    }

}
