package com.example.thomas.projet100h;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
        FacebookSdk.sdkInitialize(getApplicationContext());
        i = new Intent(this, Acceuil.class);

        setContentView(R.layout.activity_page_connection);
        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) findViewById(R.id.loginButton);
        loginButton.setReadPermissions("email");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {

              Log.e("Yo","User ID: "
                      + loginResult.getAccessToken().getUserId()
                      + "\n" +
                      "Auth Token: "
                      + loginResult.getAccessToken().getToken());
                startActivity(i);
            }

            @Override
            public void onCancel() {
                Log.e("Yo","2");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.e("Yo","3");
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }





}

