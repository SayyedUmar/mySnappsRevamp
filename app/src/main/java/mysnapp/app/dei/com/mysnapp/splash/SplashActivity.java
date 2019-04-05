package mysnapp.app.dei.com.mysnapp.splash;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import mysnapp.app.dei.com.mysnapp.R;
import mysnapp.app.dei.com.mysnapp.databinding.ActivityLogin1Binding;
import mysnapp.app.dei.com.mysnapp.login.LoginActivityNew;
import mysnapp.app.dei.com.mysnapp.login.LoginViewModel;
import mysnapp.app.dei.com.mysnapp.view.base.BaseActivity;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        navigateToLogin();
    }

    private void navigateToLogin() {
        new Handler().postDelayed(() -> startActivity(new Intent(SplashActivity.this, LoginActivityNew.class)), 3000);
    }


}