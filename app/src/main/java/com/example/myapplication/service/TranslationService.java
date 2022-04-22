package com.example.myapplication.service;

import com.example.myapplication.model.Response;
import com.example.myapplication.model.Translation;
import com.example.myapplication.model.TranslationRequest;
import com.example.myapplication.model.User;

import java.io.IOException;
import java.util.HashMap;

public class TranslationService extends APIService {

    public static void translate(User user, String sentenceToTranslate, String sourceLan, String targetLan) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> params = new HashMap<>();
                Response resp = new Response();

                try {
                    resp = RequestService.sendJSON("/translations/translate/",
                                            "POST",
                                                    params,
                                                    user,
                                                    new TranslationRequest(sentenceToTranslate, sourceLan, targetLan));
                } catch(IOException e) {
                    resp.setStatusCode(-1);
                    resp.setData(e.getMessage());
                }

                setResponse(resp);
            }
        });
        thread.start();
    }

    public static boolean addTranslation(User user, Translation translation, Long dictId) {
        if(translation.getFlValue().isEmpty() || translation.getSlValue().isEmpty())
            return false;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> params = new HashMap<>();
                params.put("dict_id", dictId.toString());
                Response resp = new Response();

                try {
                    resp = RequestService.sendJSON("/translations/",
                            "POST",
                            params,
                            user,
                            translation);
                } catch(IOException e) {
                    resp.setStatusCode(-1);
                    resp.setData(e.getMessage());
                }

                setResponse(resp);
            }
        });
        thread.start();
        return true;
    }

    public static boolean deleteTranslations(User user, Translation[] translations, Long dictId) {
        if(translations.length == 0)
            return false;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> params = new HashMap<>();
                params.put("dict_id", dictId.toString());
                Response resp = new Response();

                try {
                    resp = RequestService.sendJSON("/translations/",
                            "DELETE",
                            params,
                            user,
                            translations);
                } catch(IOException e) {
                    resp.setStatusCode(-1);
                    resp.setData(e.getMessage());
                }

                setResponse(resp);
            }
        });
        thread.start();
        return true;
    }

}
