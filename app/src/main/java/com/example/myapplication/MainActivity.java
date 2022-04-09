package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.model.Response;
import com.example.myapplication.service.AccountService;

public class MainActivity extends AppCompatActivity {

    private Button join;
    private Button reg;
    private EditText user_name;
    private EditText user_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        join = findViewById(R.id.join);
        reg = findViewById(R.id.reg);
        user_name = findViewById(R.id.user_name);
        user_password = findViewById(R.id.user_password);



        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String login = user_name.getText().toString().trim();
                String password = user_password.getText().toString();
                AccountService.authenticate(login, password);
                while (!AccountService.responseIsPresent()) {
                }
                Response response = AccountService.retrieveResponse();

                if(response.getStatusCode() != 200) {
                    Toast.makeText(MainActivity.this, "Error: " + response.getData(),
                            Toast.LENGTH_LONG).show();
                    return;
                }
                else if(response.getData().equals("false")) {
                    Toast.makeText(MainActivity.this, "Incorrect login or password",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                Intent intent = new Intent(MainActivity.this, UsersActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
    }
}