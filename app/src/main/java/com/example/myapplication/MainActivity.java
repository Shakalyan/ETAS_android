package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                if(user_name.getText().toString().trim().equals(""))
                    Toast.makeText(MainActivity.this, R.string.user_no_input,
                            Toast.LENGTH_LONG).show();
                else if (user_name.getText().toString().equals("admin") &&
                        user_password.getText().toString().equals("admin")){
                    Intent intent = new Intent(MainActivity.this, UsersActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                }
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