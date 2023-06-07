package com.example.hecate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegisterActivity extends AppCompatActivity {
    private Button RegisterButton;
    private EditText RegisterEmailId, RegisterPassword, RegisterConfirmPassword;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        RegisterButton = (Button) findViewById(R.id.register_button);
        RegisterEmailId = (EditText) findViewById(R.id.register_EmailId);
        RegisterPassword = (EditText) findViewById(R.id.register_Password);
        RegisterConfirmPassword = (EditText) findViewById(R.id.confirm_Password);
        fAuth = FirebaseAuth.getInstance();



        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String r_email_id = RegisterEmailId.getText().toString();
                String r_password = RegisterPassword.getText().toString();
                String re_password = RegisterConfirmPassword.getText().toString();

                if (TextUtils.isEmpty(r_email_id) && TextUtils.isEmpty(r_password) && TextUtils.isEmpty(re_password)) {
                    Toast.makeText(RegisterActivity.this, "Can't be null", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (r_email_id.isEmpty()) {
                    RegisterEmailId.setError("Email-Id is required");
                    return;
                }
                if (r_password.isEmpty()) {
                    RegisterPassword.setError("Password is required");
                    return;
                }
                if (re_password.isEmpty()) {
                    RegisterConfirmPassword.setError("Confirm Password is required");
                    return;
                }
                if (!r_password.equals(re_password)) {
                    RegisterConfirmPassword.setError("Password doesn't match");
                    return;
                }
                fAuth.createUserWithEmailAndPassword(r_email_id, r_password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(RegisterActivity.this, "Your account has been successfully registered", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, UserInformation.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}