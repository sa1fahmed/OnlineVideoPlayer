package com.example.onlinevideoplayer.signup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.onlinevideoplayer.OnlineVideoPlayer;
import com.example.onlinevideoplayer.R;
import com.example.onlinevideoplayer.videos.VideosList;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

public class SignUp extends AppCompatActivity implements SignUpView {

    SignUpPresenter signUpPresenter;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signUpPresenter = new SignUpPresenterImp(this);
        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpPresenter.handleSignUp(SignUp.this);
            }
        });
    }

    @Override
    public void onLoginSuccess(String name) {
        Toast.makeText(this, "Welcome "+name, Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, VideosList.class));
    }

    @Override
    public void onLoginFailed() {
        Toast.makeText(this, "Login Failed.....", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed() {
        Toast.makeText(this, "Connection Failed.....", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressDialog() {
        if (loading == null)
            loading = new ProgressDialog(this);
        loading.setCancelable(true);
        loading.setMessage("Loading...");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.show();
    }

    @Override
    public void hideProgressDialog() {
        if (loading != null && loading.isShowing())
            loading.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        hideProgressDialog();
        if (requestCode == SignUpPresenterImp.GOOGLE_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            signUpPresenter.handleSignInResult(this, result);
        }
    }
}
