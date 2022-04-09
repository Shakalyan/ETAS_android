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

public class RegActivity extends AppCompatActivity {

    private Button reg_1;
    private Button back_reg;
    private EditText new_user_name;
    private EditText new_user_password;
    private EditText dup_user_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        back_reg = findViewById(R.id.back_reg);

        back_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        reg_1 = findViewById(R.id.reg_1);
        new_user_name = findViewById(R.id.new_user_name);
        new_user_password = findViewById(R.id.new_user_password);
        dup_user_password = findViewById(R.id.dup_new_password);

        reg_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String new_login = new_user_name.getText().toString().trim();
                String new_password = new_user_password.getText().toString();
                String dup_password = dup_user_password.getText().toString();
                AccountService.register(new_login, new_password, dup_password);

                while (!AccountService.responseIsPresent()) {
                }

                Response response = AccountService.retrieveResponse();

                if(response.getStatusCode() != 200) {
                    Toast.makeText(RegActivity.this, "Error: " + response.getData(),
                            Toast.LENGTH_LONG).show();
                    return;
                }
                else if(response.getData().equals("false")) {
                    Toast.makeText(RegActivity.this, "User with same login already exists",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(RegActivity.this, "Registration successfully complete",
                        Toast.LENGTH_LONG).show();
                
                Intent intent = new Intent(RegActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
    }
}