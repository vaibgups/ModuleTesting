package com.example.socialmediaintegration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Social "+MainActivity.class.getSimpleName();

//    private Button btnGoogleSignOut;
    private static final int REQ_CODE = 9001;
    private ImageView google_login, facebook_login;
    private GoogleLogin googleLogin;
    private CallbackManager callbackManager;
    private FBLogin fbLogin;

    @Override
    protected void onStart() {
        super.onStart();
//         Check for existing Google Sign In account, if the user is already signed in
//         the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        googleLogin.updateUI(account);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();



    }

    private void init() {
        googleLogin = new GoogleLogin(MainActivity.this);
        callbackManager = CallbackManager.Factory.create();
        fbLogin = new FBLogin(MainActivity.this, callbackManager);
//        fbLogin.checkLoginStatus();

        google_login = findViewById(R.id.google_login);
        google_login.setOnClickListener(this);
        facebook_login = findViewById(R.id.facebook_login);
        facebook_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.google_login: {
                googleLogin.signIn();
                break;
            }

            case R.id.facebook_login: {
                fbLogin.initLoginManager();
//                googleLogin.signOut();
//                LoginManager.getInstance().logOut();
                break;
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            googleLogin.handleSignInResult(task);
        }
    }


}
