package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UsersActivity extends AppCompatActivity {

    private Button words;
    private Button tests;
    private Button settings;
    private Button translated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        words = findViewById(R.id.words_id);
        tests = findViewById(R.id.tests_id);
        settings = findViewById(R.id.settings_id);
        translated = findViewById(R.id.translated_id);

    }
}