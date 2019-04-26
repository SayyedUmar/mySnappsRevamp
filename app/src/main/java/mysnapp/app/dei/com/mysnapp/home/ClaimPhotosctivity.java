package mysnapp.app.dei.com.mysnapp.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import mysnapp.app.dei.com.mysnapp.R;
import mysnapp.app.dei.com.mysnapp.login.LoginViewModel;

public class ClaimPhotosctivity extends AppCompatActivity {

    private LoginViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_claim_photos);
    }
}
