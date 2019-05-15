package mysnapp.app.dei.com.mysnapp.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import org.json.JSONObject;

import mysnapp.app.dei.com.mysnapp.utils.Logs;
import retrofit2.HttpException;

public class SuperActivity extends AppCompatActivity {

    //private RxBus bus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //bus = RxBus.getInstance();
        super.onCreate(savedInstanceState);
    }

    /*public RxBus bus() {
        return bus;
    }*/

    protected void setupBackNavigation(Toolbar toolbar) {
        if (toolbar != null)
            setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    public void handlerNetworkError(Throwable e) {
        try {
            String body = ((HttpException) e).response().errorBody().string();
            JSONObject object = new JSONObject(body);
            Logs.shortToast(this, object.getString("message"));
        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * Shows the soft keyboard
     */
    public void showSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        view.requestFocus();
        inputMethodManager.showSoftInput(view, 0);
    }
}
