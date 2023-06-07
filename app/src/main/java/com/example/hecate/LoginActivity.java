package com.example.hecate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {

    private Button LoginButton;
    private EditText LoginEmailId, LoginPassword;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        LoginButton = (Button) findViewById(R.id.login_button);
        LoginEmailId = (EditText) findViewById(R.id.login_EmailId);
        LoginPassword = (EditText) findViewById(R.id.login_Password);
        ProgressBar progress = (ProgressBar)findViewById(R.id.progress);
        firebaseAuth = FirebaseAuth.getInstance();


        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (LoginEmailId.getText().toString().isEmpty() && LoginPassword.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Can't be null", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (LoginEmailId.getText().toString().isEmpty()) {
                    LoginEmailId.setError("Email-Id is required");
                    return;
                }
                if (LoginPassword.getText().toString().isEmpty()) {
                    LoginPassword.setError("Password is required");
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(LoginEmailId.getText().toString(),LoginPassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            progress.setVisibility(View.VISIBLE);
                            startActivity(new Intent(getApplicationContext(), Dashboard.class));
                            finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), Dashboard.class));
            finish();
        }
    }
}