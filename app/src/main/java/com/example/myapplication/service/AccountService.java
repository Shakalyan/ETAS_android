package com.example.myapplication.service;

import com.example.myapplication.model.Response;
import com.example.myapplication.model.User;

import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.util.HashMap;

public class AccountService extends APIService{

    public static void authenticate(String login, String password) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> params = new HashMap<>();
                params.put("login", login);
                Response resp = new Response();

                try {
                    resp = RequestService.send("/login/", "GET", params);
                } catch(IOException e) {
                    resp.setStatusCode(-1);
                    resp.setData(e.getMessage());
                }

                setResponse(resp);
            }
        });
        thread.start();

        while(!responseIsPresent()) {

        }

        Response response = retrieveResponse();
        if(response.getStatusCode() != 200) {
            setResponse(response);
            return;
        }

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String salt = response.getData();
                String hashedPassword = BCrypt.hashpw(password, salt);
                User user = new User(login, hashedPassword, salt);
                HashMap<String, String> params = new HashMap<>();
                Response resp = new Response();

                try {
                    resp = RequestService.sendJSON("/login/", "POST", params, user, null);
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
                String salt = BCrypt.gensalt();
                String hashedPassword = BCrypt.hashpw(password, salt);
                User user = new User(login, hashedPassword, salt);
                Response response = new Response();
                HashMap<String, String> params = new HashMap<>();
                try {
                    response = RequestService.sendJSON("/registration/", "POST", params, user, null);
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
