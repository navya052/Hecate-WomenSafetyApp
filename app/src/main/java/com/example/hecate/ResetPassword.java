package com.example.hecate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ResetPassword extends AppCompatActivity {

    EditText newp,confirm;
    Button reset;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        newp=findViewById(R.id.newp);
        confirm=findViewById(R.id.confirm);
        reset=findViewById(R.id.reset);
        user = FirebaseAuth.getInstance().getCurrentUser();

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newp.getText().toString().isEmpty()){
                    newp.setError("Required Field");
                    return;
                }
                if(confirm.getText().toString().isEmpty()){
                    confirm.setError("Required Field");
                }
                if(!newp.getText().toString().equals(confirm.getText().toString())){
                    confirm.setError("Password doesn't match");
                    return;
                }
                user.updatePassword(newp.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ResetPassword.this, "Password Updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), Dashboard.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ResetPassword.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}