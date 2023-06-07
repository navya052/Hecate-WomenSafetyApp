package com.example.hecate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

public class UserInformation extends AppCompatActivity {

    EditText etName, etPhone,etName1, etPhone1, email1, etName2, etPhone2,email2;
    TextView saveData, next;
    DatabaseReference userDbRef;
    String PhoneNumber1,PhoneNumber2,ContactNumber,Name1,Name2;
    int count = 0;
    SharedPreferences.Editor editor,editor1,editor2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        etName = findViewById(R.id.user_name);
        etPhone = findViewById(R.id.Phone_number);

        etName1 = findViewById(R.id.user_name1);
        etPhone1 = findViewById(R.id.Phone_number1);
        email1 = findViewById(R.id.EC1_EmailId);

        editor = getSharedPreferences("contact", MODE_PRIVATE).edit();
        editor1 = getSharedPreferences("contact1", MODE_PRIVATE).edit();
        editor2 = getSharedPreferences("name", MODE_PRIVATE).edit();

        etName2 = findViewById(R.id.user_name2);
        etPhone2 = findViewById(R.id.Phone_number2);
        email2= findViewById(R.id.EC2_EmailId);

        saveData = findViewById(R.id.textViewSave);
        next = findViewById(R.id.textViewNext);


        TextView next = findViewById(R.id.textViewNext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Dashboard.class);
                startActivity(intent);
                finish();
            }
        });

        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Name = etName.getText().toString();
                ContactNumber = etPhone.getText().toString();

                Name1 = etName1.getText().toString();
                String Email1 = email1.getText().toString();
                PhoneNumber1 = etPhone1.getText().toString();

                String Name2 = etName2.getText().toString();
                String Email2 = email2.getText().toString();
                PhoneNumber2 = etPhone2.getText().toString();

                if (Name.isEmpty()) {
                    etName.setError("Please enter your name.");
                    return;
                }
                if (ContactNumber.isEmpty()  ) {
                    etPhone.setError("Please enter your Phone Number");
                    return;
                }
                if (ContactNumber.length() != 10 ) {
                    etPhone.setError("Please enter valid Phone Number");
                    return;
                }
                if (Name1.isEmpty()) {
                    etName1.setError("Please enter name");
                    return;
                }
                if (PhoneNumber1.isEmpty()) {
                    etPhone1.setError("Please enter Phone Number");
                    return;
                }
                if (PhoneNumber1.length()!=10) {
                    etPhone1.setError("Please enter valid Phone Number");
                    return;
                }
                if (Email1.isEmpty() ) {
                    email1.setError("Please enter valid Email-ID");
                    return;
                }
                if (Name2.isEmpty()) {
                    etName2.setError("Please enter name");
                    return;
                }
                if (PhoneNumber2.isEmpty()) {
                    etPhone2.setError("Please enter Phone Number");
                    return;
                }
                if (PhoneNumber2.length()!=10) {
                    etPhone2.setError("Please enter valid Phone Number");
                    return;
                }
                if (Email2.isEmpty() ) {
                    email2.setError("Please enter valid Email-ID");
                    return;
                }
                    ValidateInformation(Name,ContactNumber,Name1,PhoneNumber1,Email1,Name2,PhoneNumber2,Email2);
            }


            private void ValidateInformation(String Name, String ContactNumber,String Name1,String PhoneNumber1,String Email1,String Name2,String PhoneNumber2,String Email2) {
                userDbRef = FirebaseDatabase.getInstance().getReference();
                userDbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        HashMap<String,Object>hashMap = new HashMap<>();
                        hashMap.put("Name",Name);
                        hashMap.put("Phone Number",ContactNumber);

                        UserInfo userInfo1 = new UserInfo(Name1,PhoneNumber1,Email1);
                        UserInfo userInfo2 = new UserInfo(Name2,PhoneNumber2,Email2);

                        userDbRef.child("Users").child(ContactNumber).child("User Contact").updateChildren(hashMap);


                        userDbRef.child("Users").child(ContactNumber).child("Emergency Contact").child(System.currentTimeMillis()+"").setValue(userInfo1);
                        userDbRef.child("Users").child(ContactNumber).child("Emergency Contact").child(System.currentTimeMillis()+"").setValue(userInfo2);
                        Toast.makeText(UserInformation.this, "Your information has been saved. Click on next to continue", Toast.LENGTH_SHORT).show();

                        editor.putString("contact", PhoneNumber1);
                        Log.i("contact", "contact" +PhoneNumber1);
                        editor.apply();

                        editor1.putString("contact1", PhoneNumber2);
                        Log.i("contact1", "contact1" +PhoneNumber2);
                        editor1.apply();

                        editor2.putString("name", Name1);
                        editor2.apply();

                        saveData.setVisibility(View.GONE);
                        next.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}
