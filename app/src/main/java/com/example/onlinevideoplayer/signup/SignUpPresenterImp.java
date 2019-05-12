package com.example.onlinevideoplayer.signup;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.onlinevideoplayer.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

/**
 * Created by Saif on 5/4/2019.
 */
public class SignUpPresenterImp implements SignUpPresenter, GoogleApiClient.OnConnectionFailedListener {

    private SignUpView signUpView;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private GoogleApiClient googleApiClient;
    public static final int GOOGLE_SIGN_IN = 1;
    private static String TAG = "SignUp";

    public SignUpPresenterImp(SignUpView signUpView) {
        this.signUpView = signUpView;
    }

    @Override
    public void handleSignUp(Context mContext) {

        signUpView.showProgressDialog();
        firebaseAuth = com.google.firebase.auth.FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
            }
        };

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(mContext.getResources().getString(R.string.web_client_id))
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(mContext)
                .enableAutoManage((SignUp) mContext, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        ((SignUp) mContext).startActivityForResult(intent, GOOGLE_SIGN_IN);
    }

    @Override
    public void handleSignInResult(Context mContext, GoogleSignInResult result) {

        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            String idToken = account.getIdToken();
            String name = account.getDisplayName();
            String email = account.getEmail();
            Log.e(TAG, "Profile: " + name + " " + email);
            AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
            firebaseAuthWithGoogle(mContext, credential,name);
        } else {
            signUpView.onLoginFailed();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        signUpView.onConnectionFailed();
        signUpView.hideProgressDialog();
    }

    private void firebaseAuthWithGoogle(Context mContext, AuthCredential credential, final String name) {
        signUpView.showProgressDialog();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener((SignUp) mContext, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.e(TAG, "Success");
                            signUpView.onLoginSuccess(name);
                            signUpView.hideProgressDialog();
                        } else {
                            signUpView.onLoginFailed();
                            signUpView.hideProgressDialog();
                            task.getException().printStackTrace();
                        }

                    }
                });
    }
}
