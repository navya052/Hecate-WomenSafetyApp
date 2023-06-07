package com.example.hecate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Button;

public class AppDisguise extends AppCompatActivity {

    Button change, defaulticon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_disguise);

        change=findViewById(R.id.change);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneralUtils.changeAppIconDynamically(AppDisguise.this, true);
            }
        });

        defaulticon=findViewById(R.id.defaulticon);
        defaulticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneralUtils.changeAppIconDynamically(AppDisguise.this, false);
            }
        });

    }

}