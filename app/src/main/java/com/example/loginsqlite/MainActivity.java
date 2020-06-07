package com.example.loginsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText txtUsername, txtPassword;
    Button btnLogin, btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtUsername = findViewById(R.id.editText);
        txtPassword = findViewById(R.id.editText2);
        btnLogin = findViewById(R.id.button);
        btnSignup = findViewById(R.id.button2);

//        Intent intent = new Intent(MainActivity.this, WecomeLogin.class);
//        startActivity(intent);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();
                User user = new User(username, password);

            }
        });
    }
}
