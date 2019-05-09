package mysnapp.app.dei.com.mysnapp.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import mysnapp.app.dei.com.mysnapp.R;
import mysnapp.app.dei.com.mysnapp.home.ClaimPhotosActivity;
import mysnapp.app.dei.com.mysnapp.login.LoginActivityNew;
import mysnapp.app.dei.com.mysnapp.utils.AppConst;
import mysnapp.app.dei.com.mysnapp.utils.MyPreferences;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        MyPreferences.setBoolValue(this, AppConst.LOGIN_STATUS, false);
        if (MyPreferences.getBoolValue(this, AppConst.LOGIN_STATUS)) {
            navigateToHome();
        } else {
            navigateToLogin();
        }
    }

    private void navigateToLogin() {
        new Handler().postDelayed(() -> startActivity(new Intent(SplashActivity.this, LoginActivityNew.class)), 3000);
    }

    private void navigateToHome() {
        new Handler().postDelayed(() -> startActivity(new Intent(SplashActivity.this, ClaimPhotosActivity.class)), 3000);
    }


}