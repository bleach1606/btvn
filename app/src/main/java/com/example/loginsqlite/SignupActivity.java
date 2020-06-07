package com.example.loginsqlite;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    EditText txtUsername, txtPassword;
    Button btnSigin, btnSignup;
    DatabaseHandler database;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        txtUsername = findViewById(R.id.editText4);
        txtPassword = findViewById(R.id.editText3);
        btnSigin = findViewById(R.id.button4);
        btnSignup = findViewById(R.id.button3);
        database = new DatabaseHandler(this);

        btnSigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sql = String.format("SELECT id, username, password FROM user where username = '%s'",
                        txtUsername.getText().toString());
                Cursor cursor = database.GetData(sql);
                if (cursor.moveToNext()) {
                    int id = cursor.getInt(0);
                    String username = cursor.getString(1);
                    String password = cursor.getString(2);
                    user = new User(id, username, password);
                }
                if (user == null) {
                    sql = String.format("INSERT INTO user(username, password) values('%s', '%s')",
                            txtUsername.getText().toString(), txtPassword.getText().toString());
                    database.QueryData(sql);
                    Toast.makeText(SignupActivity.this, "Đăng kí tài khoản thành công !!!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                                  startActivity(intent);
                } else {
                    Toast.makeText(SignupActivity.this, "Tên đăng nhập đã tồn tại !!!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
