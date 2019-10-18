package com.example.socialmediaintegration;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class FBLogin {

    private static final String TAG = "Social FBLogin";
    private static final String EMAIL = "email";
    private static final String PUBLIC_PROFILE = "public_profile";
    private Context context;
    private Activity activity;
    private CallbackManager callbackManager;

    public FBLogin(Context context, CallbackManager callbackManager) {
        this.context = context;
        this.activity = (Activity) context;
        this.callbackManager = callbackManager;
    }

    protected void initLoginManager(){
        LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList(EMAIL,PUBLIC_PROFILE));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });
    }

    AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            if (currentAccessToken!=null){
                loadUserProfile(currentAccessToken);
            }
        }
    };

    protected void checkLoginStatus(){
        if (AccessToken.getCurrentAccessToken()!= null &&
                !AccessToken.getCurrentAccessToken().isExpired()){
            loadUserProfile(AccessToken.getCurrentAccessToken());
        }
    }

    private void loadUserProfile(AccessToken newAccessToken){
        GraphRequest graphRequest = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                try {
                    String first_name = object.getString("first_name");
                    String last_name = object.getString("last_name");
                    String email = object.getString("email");
                    String id = object.getString("id");
                    String image_url = "https://graph.facebook.com/" +id+ "/picture?type=normal";

                    Log.i(TAG, "onCompleted: \n"+
                            "First Name : "+first_name+
                            "\nLast Name : "+last_name+
                            "\nEmail : "+email+
                            "\nID : "+id+
                            "\nImage URL : "+image_url);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Bundle parameter = new Bundle();
        parameter.putString("fields","first_name,last_name,email,id");
        graphRequest.setParameters(parameter);
        graphRequest.executeAsync();

    }


 /*   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_login);


        callbackManager = CallbackManager.Factory.create();
//        checkLoginStatus();
        findViewById(R.id.btnFBLogOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fbLogin = new FBLogin(FacebookLoginActivity.this, callbackManager);

            }
        });


       *//* AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();*//*



       *//* loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(EMAIL,PUBLIC_PROFILE));
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });*//*
    }
*/

}
