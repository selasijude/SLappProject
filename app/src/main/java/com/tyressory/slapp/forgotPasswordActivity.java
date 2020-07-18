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
import com.google.firebase.auth.FirebaseAuth;

public class forgotPasswordActivity extends AppCompatActivity {

    private EditText inputEmail;
    private Button btnReset;
    private FirebaseAuth auth;

    @SuppressWarnings("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        inputEmail = (EditText) findViewById(R.id.mailforgotPassword);
        btnReset = (Button) findViewById(R.id.btnSendMail);
        auth = FirebaseAuth.getInstance();

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Please enter your E-mail address", Toast.LENGTH_LONG).show();
                    return;
                }

                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(forgotPasswordActivity.this, "We've sent you a mail", Toast.LENGTH_LONG).show();
                                }
                                else{
                                    Toast.makeText(forgotPasswordActivity.this, "Error", Toast.LENGTH_LONG).show();
                                }
                            }
                        });


            }
        });
    }


    public void signIn(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
