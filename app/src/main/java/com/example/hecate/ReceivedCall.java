package com.example.hecate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ReceivedCall extends AppCompatActivity {

    TextView id;
    String name;
    Button end;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_call);

        id = findViewById(R.id.name1);
        String MY_PREFS_NAME = "name";
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        name = prefs.getString("name", "null");
        id.setText(name);

        end = findViewById(R.id.end);
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReceivedCall.this,Dashboard.class);
                startActivity(intent);
                finish();
            }
        });
    }
}