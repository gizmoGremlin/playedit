package com.pickle.ourgames.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.pickle.ourgames.MainActivity;
import com.pickle.ourgames.R;
import com.pickle.ourgames.storage.SharedPref;

public class AuthActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private Button btnSendLink;
    private EditText etEmail;
    private EditText etPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);



        auth = FirebaseAuth.getInstance();
        Log.d("AuthStatusBefore", auth.getCurrentUser().getEmail());
        if (!(auth.getCurrentUser() == null)){
            Log.d("AuthStatusAfter", auth.getCurrentUser().getEmail());

            navagateToMainActivity();
        }

        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnSendLink = findViewById(R.id.btn_signin);


        btnSendLink.setOnClickListener(click -> {
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            if (!(email.length() > 5)  || !(password.length() > 6) ) {
                return;
            }

                auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        navagateToMainActivity();
                    }
                });


        });

    }

    private void navagateToMainActivity() {
        Intent intent =  new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }


}
