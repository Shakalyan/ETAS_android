package com.example.myapplication.service;

import android.util.Log;

import com.example.myapplication.model.Response;
import com.example.myapplication.model.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.Callable;

public class AccountService extends APIService{

    public static void authenticate(String login, String password) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                User user = new User(login, password);
                HashMap<String, String> params = new HashMap<>();
                Response resp = new Response();

                try {
                    resp = RequestService.sendPostJSON("/login/", params, user);
                } catch(IOException e) {
                    resp.setStatusCode(-1);
                    resp.setData(e.getMessage());
                }

                setResponse(resp);
            }
        });
        thread.start();
    }

    public static void register(String login, String password, String passwordCheck) {
        if (!password.equals(passwordCheck)) {
            setResponse(new Response(-1, "Passwords do not match"));
            return;
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                User user = new User(login, password);
                Response response = new Response();
                HashMap<String, String> params = new HashMap<>();
                try {
                    response = RequestService.sendPostJSON("/registration/", params, user);
                } catch(IOException e) {
                    response.setStatusCode(-1);
                    response.setData(e.getMessage());
                }
                setResponse(response);
            }
        });
        thread.start();
    }

}