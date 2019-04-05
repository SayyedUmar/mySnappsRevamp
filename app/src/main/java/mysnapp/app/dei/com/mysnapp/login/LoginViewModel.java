package mysnapp.app.dei.com.mysnapp.login;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.support.annotation.VisibleForTesting;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import mysnapp.app.dei.com.mysnapp.login.models.LoginForm;
import mysnapp.app.dei.com.mysnapp.login.models.LoginModel;
import mysnapp.app.dei.com.mysnapp.utils.Const;
import mysnapp.app.dei.com.mysnapp.utils.MyPreferences;

public class LoginViewModel extends ViewModel {

    //private LoginModel loginModel;
    private LoginForm login;
    private View.OnFocusChangeListener onFocusEmail;
    private View.OnFocusChangeListener onFocusPassword;


    @VisibleForTesting
    void init(Context context) {
        login = new LoginForm();
        LoginModel model = login.getModel();
        model.rememberMe = MyPreferences.getBoolValue(context, Const.REMEMBER_ME);
        if (model.rememberMe) {
            model.setEmail(MyPreferences.getStringValue(context, Const.USERNAME));
            model.setPassword(MyPreferences.getStringValue(context, Const.PASSWORD));
        }

        onFocusEmail = (view, focused) -> {
            EditText et = (EditText) view;
            if (et.getText().length() > 0 && !focused) {
                login.isEmailValid(true);
            }
        };

        onFocusPassword = (view, focused) -> {
            EditText et = (EditText) view;
            if (et.getText().length() > 0 && !focused) {
                login.isPasswordValid(true);
            }
        };
    };


    @BindingAdapter("error")
    public static void setError(EditText editText, Object strOrResId) {
        if (strOrResId instanceof Integer) {
            editText.setError(editText.getContext().getString((Integer) strOrResId));
        } else {
            editText.setError((String) strOrResId);
        }
    }

    @BindingAdapter("onFocus")
    public static void bindFocusChange(EditText editText, View.OnFocusChangeListener onFocusChangeListener) {
        if (editText.getOnFocusChangeListener() == null) {
            editText.setOnFocusChangeListener(onFocusChangeListener);
        }
    }

    public void checkedChangedListener (View v, boolean isChecked) {
        login.rememberMeChanged(isChecked);
    }

    public View.OnFocusChangeListener getEmailOnFocusChangeListener() {
        return onFocusEmail;
    }

    public View.OnFocusChangeListener getPasswordOnFocusChangeListener() {
        return onFocusPassword;
    }

    public void onButtonClick(View view) {
        login.onClick(view.getContext());
    }

    public LoginForm getLogin() {
        return login;
    }

}
