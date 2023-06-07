package com.example.hecate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import android.location.Location;
import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.telephony.SmsManager;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import java.util.ArrayList;
import java.util.Locale;

import com.google.firebase.auth.FirebaseAuth;


public class Dashboard extends AppCompatActivity {

    private static final String TAG = Dashboard.class.getSimpleName();
    private static final int UPDATE_INTERVAL = 5000000;//
    private final Handler mHandler = new Handler();
    FusedLocationProviderClient locationProviderClient;
    LocationRequest locationRequest;
    LocationCallback locationCallback;
    private Location currentLocation;
    private int LOCATION_PERMISSION = 100;
    ToggleButton msg, siren;
    MediaPlayer mp3;
    Button fakecall,msg2;
    String contact,contact1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        voiceautomation();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        siren = findViewById(R.id.siren);
        mp3 = MediaPlayer.create(this, R.raw.n);
        fakecall = findViewById(R.id.fake_call_btn);

        String MY_PREFS_NAME = "contact";
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        contact = prefs.getString("contact", "null");

        String MY_PREFS_NAME1 = "contact1";
        SharedPreferences prefs1 = getSharedPreferences(MY_PREFS_NAME1, MODE_PRIVATE);
        contact1 = prefs1.getString("contact1", "null");

        ActivityCompat.requestPermissions(Dashboard.this, new String[]{Manifest.permission.SEND_SMS,Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);

        msg2= findViewById(R.id.msg2);
        msg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "Hi, I am being cautious. Please help me by reaching to below location.\n\nGoogle Map Location: https://www.google.com/maps/search/?api=1&query="+currentLocation.getLatitude()+","+currentLocation.getLongitude()+"\n\n(sent via Hecate - Women Safety Application)";
                SmsManager mySmsManager = SmsManager.getDefault();
                Log.i("number",contact);
                ArrayList<String> parts = mySmsManager.divideMessage(message);
                mySmsManager.sendMultipartTextMessage(contact,null,parts ,null,null);
                mySmsManager.sendMultipartTextMessage(contact1, null, parts, null, null);
                Toast.makeText(Dashboard.this, "Message Sent", Toast.LENGTH_SHORT).show();
            }
        });

        msg = findViewById(R.id.msg);
        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if(msg.isChecked()){
                     messageSending.run();
                 }else{
                     mHandler.removeCallbacks(messageSending);
                 }
            }
        });

        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationCallback = new LocationCallback(){
            @Override
            public void onLocationAvailability(LocationAvailability locationAvailability) {
                super.onLocationAvailability(locationAvailability);
                if(locationAvailability.isLocationAvailable()){
                    Log.i(TAG,"Location is available");
                }else {
                    Log.i(TAG,"Location is unavailable");
                }
            }

            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Log.i(TAG,"Location result is available");
            }
        };


        fakecall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Dashboard.this,IncomingCall.class);
                startActivity(intent1);
                finish();
            }
        });

        siren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(siren.isChecked()){
                    mp3.start();
                    mp3.setLooping(true);
                }
                else
                    mp3.stop();
            }
        });
    }

    private void voiceautomation() {
        Intent voice = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        voice.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        voice.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        voice.putExtra(RecognizerIntent.EXTRA_PROMPT,"SPEAK NOW");
        startActivityForResult(voice,1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null){
            ArrayList<String> arrayList=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (arrayList.get(0).equals("help me please")){
                msg.setChecked(true);
                messageSending.run();
            }
        }
    }

    Runnable messageSending = new Runnable() {
        @Override
        public void run() {
            startGettingLocation();
            String message = "Hi, I am in trouble. Please help me by reaching to below location.\n\nGoogle Map Location: https://www.google.com/maps/search/?api=1&query="+currentLocation.getLatitude()+","+currentLocation.getLongitude()+"\n\n(sent via Hecate - Women Safety Application)";

            SmsManager mySmsManager1 = SmsManager.getDefault();
            ArrayList<String> parts = mySmsManager1.divideMessage(message);
            mySmsManager1.sendMultipartTextMessage(contact,null,parts ,null,null);
            mySmsManager1.sendMultipartTextMessage(contact1, null, parts, null, null);

            Toast.makeText(Dashboard.this, "Message Sent", Toast.LENGTH_SHORT).show();
            mHandler.postDelayed(this,10000);
        }
    };


    private void startGettingLocation() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            locationProviderClient.requestLocationUpdates(locationRequest,locationCallback, Dashboard.this.getMainLooper());
            locationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    currentLocation = location;

                }
            });

            locationProviderClient.getLastLocation().addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.i(TAG, "Exception while getting the location: "+e.getMessage());
                }
            });

        }else {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
                Toast.makeText(Dashboard.this, "Permission needed", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(Dashboard.this,
                        new String[] { Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        LOCATION_PERMISSION);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,  @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        startGettingLocation();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        for (int i=0;i<menu.size();i++){
            MenuItem menuItem = menu.getItem(i);
            SpannableString spannable = new SpannableString(menu.getItem(i).getTitle().toString());
            spannable.setSpan(new ForegroundColorSpan(Color.BLACK),0, spannable.length(),0);
            menuItem.setTitle(spannable);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.reset) {
            Intent intent = new Intent(Dashboard.this, ResetPassword.class);
            startActivity(intent);
        } else if (id == R.id.timer) {
            Intent intent = new Intent(Dashboard.this, Timer.class);
            startActivity(intent);
        } else if (id == R.id.about) {
            Intent intent = new Intent(Dashboard.this, AboutUs.class);
            startActivity(intent);
        } else if (id == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(Dashboard.this, MainActivity2.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.setting) {
            Intent intent = new Intent(Dashboard.this, AppDisguise.class);
            startActivity(intent);
        }else if (id == R.id.voice) {
            voiceautomation();
        }
        return true;
    }
}