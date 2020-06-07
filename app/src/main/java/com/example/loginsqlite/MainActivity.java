package com.example.loginsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText txtUsername, txtPassword;
    Button btnLogin, btnSignup;
    DatabaseHandler database;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtUsername = findViewById(R.id.editText);
        txtPassword = findViewById(R.id.editText2);
        btnLogin = findViewById(R.id.button);
        btnSignup = findViewById(R.id.button2);

        database = new DatabaseHandler(this);
        database.QueryData("CREATE table if not exists user(id INTEGER PRIMARY KEY AUTOINCREMENT, username varchar(20), password varchar(20))");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sql = String.format("SELECT id, username, password FROM user where username = '%s' and password = '%s'",
                        txtUsername.getText().toString(), txtPassword.getText().toString());
                Cursor cursor = database.GetData(sql);
                if (cursor.moveToNext()) {
                    int id = cursor.getInt(0);
                    String username = cursor.getString(1);
                    String password = cursor.getString(2);
                    user = new User(id, username, password);
                }
                if (user != null) {
                    Toast.makeText(MainActivity.this, "Đăng nhập thành công !!!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Tên đăng nhập hoặc mật khẩu không đúng !!!", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}
