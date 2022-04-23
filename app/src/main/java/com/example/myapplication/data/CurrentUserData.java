package com.example.myapplication.data;

import android.content.Context;
import android.widget.Toast;

import com.example.myapplication.model.Dictionary;
import com.example.myapplication.model.Response;
import com.example.myapplication.model.User;
import com.example.myapplication.service.AccountService;

public class CurrentUserData {

    private static User user;
    private static Dictionary currentDictionary;
    private static boolean translationReversed = false;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        CurrentUserData.user = user;
    }

    public static Dictionary getCurrentDictionary() {
        return currentDictionary;
    }

    public static void setCurrentDictionary(Dictionary currentDictionary) {
        CurrentUserData.currentDictionary = currentDictionary;
    }

    public static boolean isTranslationReversed() {
        return translationReversed;
    }

    public static void setTranslationReversed(boolean translationReversed) {
        CurrentUserData.translationReversed = translationReversed;
    }

    public static boolean currentDictionaryIsNull(boolean showMessage, Context context) {
        if(CurrentUserData.getCurrentDictionary() == null) {
            if(showMessage)
                Toast.makeText(context, String.format("Chosen dictionary is null"), Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

}
