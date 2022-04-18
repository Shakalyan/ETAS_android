package com.example.myapplication.service;

import com.example.myapplication.model.Response;

import java.io.IOException;
import java.util.HashMap;

public class TranslationService extends APIService {

    public static void translate(String sentenceToTranslate, String sourceLan, String targetLan) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> params = new HashMap<>();
                params.put("sentence", sentenceToTranslate);
                params.put("source", sourceLan);
                params.put("target", targetLan);

                Response resp = new Response();

                try {
                    resp = RequestService.sendPost("/translations/translate/", params);
                } catch(IOException e) {
                    resp.setStatusCode(-1);
                    resp.setData(e.getMessage());
                }

                setResponse(resp);
            }
        });
        thread.start();
    }

}
