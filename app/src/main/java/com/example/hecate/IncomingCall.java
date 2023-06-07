package com.example.hecate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaParser;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class IncomingCall extends AppCompatActivity {

    TextView id;
    String name;
    Button accept, decline;
    MediaPlayer mp;
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        mp.stop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_call);

        id = findViewById(R.id.name);
        String MY_PREFS_NAME = "name";
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        name = prefs.getString("name", "null");
        id.setText(name);

        accept = findViewById(R.id.accept);
        mp = MediaPlayer.create(this, R.raw.audio1);
        mp.start();
        mp.setLooping(true);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                Intent intent = new Intent(IncomingCall.this,ReceivedCall.class);
                startActivity(intent);
                finish();
            }
        });

        decline=findViewById(R.id.decline);
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                Intent intent = new Intent(IncomingCall.this,Dashboard.class);
                startActivity(intent);
                finish();
            }
        });

    }
}