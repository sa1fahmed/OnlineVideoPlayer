package com.example.onlinevideoplayer.signup;

import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignInResult;

/**
 * Created by Saif on 5/4/2019.
 */
public interface SignUpPresenter {

    void handleSignUp(Context mContext);

    void handleSignInResult(Context mContext, GoogleSignInResult result);
}
