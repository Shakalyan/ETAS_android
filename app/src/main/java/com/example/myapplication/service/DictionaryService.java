package com.example.myapplication.service;

import com.example.myapplication.model.Dictionary;
import com.example.myapplication.model.Response;
import com.example.myapplication.model.User;

import java.io.IOException;
import java.util.HashMap;

public class DictionaryService extends APIService {

    public static boolean createDictionary(User user, Dictionary dictionary) {
        for(Dictionary d : user.getDictionaries())
            if(d.getName().equals(dictionary.getName()))
                return false;
        if(dictionary.getName().isEmpty())
            return false;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                HashMap<String, String> params = new HashMap<>();
                Response response = new Response();
                try {
                    response = RequestService.sendJSON("/dictionaries/", "POST", params, user, dictionary);
                } catch(IOException e) {
                    response.setStatusCode(-1);
                    response.setData(e.getMessage());
                }

                setResponse(response);
            }
        });
        thread.start();
        return true;
    }

    public static void deleteDictionary(User user, Dictionary dictionary) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> params = new HashMap<>();
                Response response = new Response();
                try {
                    response = RequestService.sendJSON("/dictionaries/", "DELETE", params, user, dictionary.getId());
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
