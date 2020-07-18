package com.tyressory.slapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements TextWatcher, CompoundButton.OnCheckedChangeListener {

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    EditText emailsignIn, passwordsignIn;
    Button signinbtn;
    CheckBox rememberMe, showPass;



    //Shared prefences
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String PREF_NAME = "prefer";
    private static final String KEY_REMEMBER = "remember";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASS = "password";
    //ends here

    private FirebaseAuth auth;

    // On back pressed
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        MainActivity.super.onBackPressed();
                    }
                }).create().show();
    }

    //ends here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_toolbar);

        emailsignIn = (EditText) findViewById(R.id.emailSignIn);
        passwordsignIn = (EditText) findViewById(R.id.passwordsignIn);
        signinbtn = (Button) findViewById(R.id.signIn);
        rememberMe = (CheckBox) findViewById(R.id.remember);
        showPass = (CheckBox) findViewById(R.id.showPassword);


        auth = FirebaseAuth.getInstance();


        //Initializing shared preferences
        sharedPreferences =getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor= sharedPreferences.edit();

        //If statements
        if(sharedPreferences.getBoolean(KEY_REMEMBER, false))
            rememberMe.setChecked(true);
        else
            rememberMe.setChecked(false);

        emailsignIn.setText(sharedPreferences.getString(KEY_USERNAME,""));
        passwordsignIn.setText(sharedPreferences.getString(KEY_PASS,""));


        emailsignIn.addTextChangedListener(this);
        passwordsignIn.addTextChangedListener(this);
        rememberMe.setOnCheckedChangeListener(this);


        //ends here

        //show password
        showPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    passwordsignIn.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else{
                    passwordsignIn.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });


        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = emailsignIn.getText().toString();
                final String pass = passwordsignIn.getText().toString();

                if (TextUtils.isEmpty(mail)) {
                    Toast.makeText(getApplicationContext(), "Please enter your E-mail address", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(getApplicationContext(), "Please enter your Password", Toast.LENGTH_LONG).show();
                }

                //Remember me authentication


                // Authenticating the user
                auth.signInWithEmailAndPassword(mail, pass)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    // Error
                                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                                } else {
                                    startActivity(new Intent(MainActivity.this, MapsActivity.class));
                                    finish();
                                }
                            }


                        });
            }



        });
    }


                public void forgotPassword (View v){
                        Intent intent = new Intent(this, forgotPasswordActivity.class);
                        startActivity(intent);
                }


                public void signUp (View v){
                    Intent intent = new Intent(this, signUp.class);
                    startActivity(intent);

                }
                public void stdInterface (View v){
                    Intent intent = new Intent(this, UserActivity.class);
                    startActivity(intent);
                }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        managePrefs();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        managePrefs();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        managePrefs();
    }
    private void managePrefs() {
        if(rememberMe.isChecked()){
            editor.putString(KEY_USERNAME,emailsignIn.getText().toString().trim());
            editor.putString(KEY_PASS, passwordsignIn.getText().toString().trim());
            editor.putBoolean(KEY_REMEMBER, true);
            editor.apply(); // saves it to the shared preferences
        }
        else{
            editor.putBoolean(KEY_REMEMBER, false);
            editor.remove(KEY_PASS);//editor.putString(KEY_PASS, **);
            editor.remove(KEY_USERNAME);//editor.putString(KEY_USERNAME, **);
            editor.apply();
        }
    }

}


