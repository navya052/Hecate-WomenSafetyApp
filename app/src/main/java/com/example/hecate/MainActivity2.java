package com.example.hecate;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity2 extends AppCompatActivity {
    Button join_nowButton, loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        join_nowButton = findViewById(R.id.join_now_button);
        join_nowButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}