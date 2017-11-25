package com.example.kpmadan.otshealthproject;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirstUserData extends AppCompatActivity {
    private EditText mNameField;
    private EditText mEmailField;
    private EditText mSexField;

    private Button mSaveData;
    private FirebaseAuth mAuth;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_user_data);


        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        mProgress = new ProgressDialog(this);

        mNameField = (EditText) findViewById(R.id.nameField);
        mEmailField = (EditText) findViewById(R.id.emailField);
        mSexField = (EditText) findViewById(R.id.sexField);

        mSaveData = (Button) findViewById(R.id.saveData);
        mSaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSavingUserData();
            }
        });
    }

    public void startSavingUserData() {

        final String name = mNameField.getText().toString().trim();
        String email = mEmailField.getText().toString().trim();
        String sex = mSexField.getText().toString().trim();
        mProgress.setMessage("Saving data...");
        mProgress.show();
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(sex)) {


            String user_id = mAuth.getCurrentUser().getUid();
            String user_phone = mAuth.getCurrentUser().getPhoneNumber();

            DatabaseReference current_user_db = mDatabase.child(user_id);
            current_user_db.child("name").setValue(name);
            current_user_db.child("email").setValue(email);
            current_user_db.child("bloodgroup").setValue("");
            current_user_db.child("age").setValue("");

            current_user_db.child("image").setValue("https://firebasestorage.googleapis.com/v0/b/otshealth-4bc93.appspot.com/o/dc.jpg?alt=media&token=95ce5ca6-cd03-471e-ba94-5ec20855b731");

            current_user_db.child("sex").setValue(sex);

            mProgress.dismiss();

            Intent mainIntent = new Intent(FirstUserData.this, MainActivity.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mainIntent);

        }
    }
}