package mysnapp.app.dei.com.mysnapp.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import mysnapp.app.dei.com.mysnapp.R;
import mysnapp.app.dei.com.mysnapp.databinding.ActivityLogin1Binding;
import mysnapp.app.dei.com.mysnapp.view.base.BaseActivity;

public class LoginActivityNew extends BaseActivity<ActivityLogin1Binding> {

    @Override
    public int getLayoutRes() {
        return R.layout.activity_login1;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }
}
