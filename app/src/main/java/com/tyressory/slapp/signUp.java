package com.tyressory.slapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Map;

public class signUp extends AppCompatActivity {
    Button signupbtn;
    EditText email, password;
    Button signinbtn;
    private FirebaseAuth auth;

    @SuppressWarnings("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = findViewById(R.id.emailSignUp);
        password = findViewById(R.id.passwordsignUp);
        auth = FirebaseAuth.getInstance();
        signupbtn = (Button) findViewById(R.id.register);

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = email.getText().toString();
                String pass = password.getText().toString();

                if (TextUtils.isEmpty(mail)) {
                    Toast.makeText(getApplicationContext(), "Please enter your E-mail address", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(getApplicationContext(), "Please enter your Password", Toast.LENGTH_LONG).show();
                }
                if (pass.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please enter your Password", Toast.LENGTH_LONG).show();
                }
                if (pass.length() < 8) {
                    Toast.makeText(getApplicationContext(), "Password must be more than 8 digit", Toast.LENGTH_LONG).show();
                } else {
                    auth.createUserWithEmailAndPassword(mail, pass)
                            .addOnCompleteListener(signUp.this, new OnCompleteListener<AuthResult>() {
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (!task.isSuccessful()) {
                                        Toast.makeText(signUp.this, "ERROR", Toast.LENGTH_LONG).show();
                                    } else {
                                        startActivity(new Intent(signUp.this, MapsActivity.class));
                                        finish();
                                    }


                                }
                            });
                }
            }
        });
            }



    public void signIn(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
