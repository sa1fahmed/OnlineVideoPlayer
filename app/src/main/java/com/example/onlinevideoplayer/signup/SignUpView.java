package com.example.onlinevideoplayer.signup;

/**
 * Created by Saif on 5/4/2019.
 */
public interface SignUpView {

    void onLoginSuccess(String name);

    void onLoginFailed();

    void onConnectionFailed();

    void showProgressDialog();

    void hideProgressDialog();

}
