package com.example.thomas.projet100h;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


public class pageConnection extends AppCompatActivity {

    private CallbackManager callbackManager;
    private Intent i;
    private LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        i = new Intent(this, Acceuil.class);

        setContentView(R.layout.activity_page_connection);

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        Log.e("yo","1");
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Log.e("yo","2");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Log.e("yo","3");
                    }
                });
    }

    public void On(View v){
        Intent i = new Intent(this,Acceuil.class);
        startActivity(i);
    }




}

