package mysnapp.app.dei.com.mysnapp.login;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import mysnapp.app.dei.com.mysnapp.R;
import mysnapp.app.dei.com.mysnapp.databinding.ActivityLogin1Binding;
import mysnapp.app.dei.com.mysnapp.utils.Logs;
import mysnapp.app.dei.com.mysnapp.view.base.BaseActivity;

public class LoginActivityNew extends BaseActivity<ActivityLogin1Binding> {

    private LoginViewModel viewModel;
    //https://medium.com/@saquib3705/consuming-rest-api-using-retrofit-library-with-the-help-of-mvvm-dagger-livedata-and-rxjava2-in-67aebefe031d

    @Override
    public int getLayoutRes() {
        return R.layout.activity_login1;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init(this);
        }
        dataBinding.setModel(viewModel);

        viewModel.getLoginResponse().observe(this, res -> {
            Logs.shortToast(this, "Got Response");
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }


    @Override
    protected void onDestroy() {
        viewModel.onDestroy();
        super.onDestroy();
    }

}
