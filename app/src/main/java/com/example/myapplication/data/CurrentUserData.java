package com.example.myapplication.data;

import com.example.myapplication.model.User;

public class CurrentUserData {

    private static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        CurrentUserData.user = user;
    }

}
