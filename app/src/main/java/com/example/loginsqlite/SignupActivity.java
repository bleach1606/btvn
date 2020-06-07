package com.example.loginsqlite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Signup extends AppCompatActivity {

    EditText txtUsername, txtPassword;
    Button btnSigin, btnSignup;
//    DatabaseHandler database;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        txtUsername = findViewById(R.id.editText4);
        txtPassword = findViewById(R.id.editText3);
        btnSigin = findViewById(R.id.button4);
        btnSignup = findViewById(R.id.button3);

        btnSigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Signup.this, MainActivity.class);
//                startActivity(intent);
            }
        });
    }
}
