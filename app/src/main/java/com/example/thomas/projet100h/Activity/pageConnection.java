package com.example.thomas.projet100h.Activity;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.example.thomas.projet100h.R;
import com.example.thomas.projet100h.Utilities.Connection;
import com.example.thomas.projet100h.entities.Utilisateur;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


public class pageConnection extends AppCompatActivity {

    private CallbackManager callbackManager;
    private Intent i;
    private LoginButton loginButton;
    private String id;
    public static final Utilisateur user = new Utilisateur("",1,"","");
    private LoginManager loginManager;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        i = new Intent(this, Acceuil.class);
        setContentView(R.layout.activity_page_connection);
        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) findViewById(R.id.loginButton);
        loginButton.setReadPermissions("email");


        loginManager = LoginManager.getInstance();



        Log.e("LB",loginManager.getDefaultAudience().toString());
        Log.e("LB",loginManager.toString());
        Log.e("LB",loginManager.getLoginBehavior().toString());
        loginManager.logOut();

        loginButton.getDefaultAudience().toString();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {


            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e("Acces",loginResult.getAccessToken().toString());
                id = loginResult.getAccessToken().getUserId();
                Log.e("idFacebook PC",id);
                Connection conn = new Connection(id);
                conn.execute();
                i.putExtra("id",id);
                startActivity(i);




            }

            @Override
            public void onCancel() {   Log.e("Yo","cancel");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.e("Yo","error");
            }
        });





    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }



    public void onClick(View V){
        startActivity(i);
    }


}

