package mysnapp.app.dei.com.mysnapp.login;

import android.arch.lifecycle.ViewModel;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.support.annotation.VisibleForTesting;
import android.view.View;
import android.widget.EditText;

import mysnapp.app.dei.com.mysnapp.login.models.LoginForm;
import mysnapp.app.dei.com.mysnapp.login.models.LoginModel;

public class LoginViewModel extends ViewModel {

    //private LoginModel loginModel;
    private LoginForm login;
    private View.OnFocusChangeListener onFocusEmail;
    private View.OnFocusChangeListener onFocusPassword;



  /*  @Bindable
    public Boolean getRememberMe() {
        return loginModel.rememberMe;
    }

    public void rememberMeChanged(Boolean value) {
        // Avoids infinite loops.
        if (loginModel.rememberMe != value) {
            loginModel.rememberMe = value;

            // React to the change.
//            saveData();

            //notifyPropertyChanged(BR.rememberMe);
        }
    }*/

    @VisibleForTesting
    public void init() {
        login = new LoginForm();
        onFocusEmail =  new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View view, boolean focused) {
                EditText et = (EditText) view;
                if (et.getText().length() > 0 && !focused) {
                    login.isEmailValid(true);
                }
            }
        };

        onFocusPassword = new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View view, boolean focused) {
                EditText et = (EditText) view;
                if (et.getText().length() > 0 && !focused) {
                    login.isPasswordValid(true);
                }
            }
        };
    }


    @BindingAdapter("error")
    public static void setError(EditText editText, Object strOrResId) {
        if (strOrResId instanceof Integer) {
            editText.setError(
                    editText.getContext().getString((Integer) strOrResId));
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

    public View.OnFocusChangeListener getEmailOnFocusChangeListener() {
        return onFocusEmail;
    }

    public View.OnFocusChangeListener getPasswordOnFocusChangeListener() {
        return onFocusPassword;
    }

    public void onButtonClick() {
        login.onClick();
    }

    public LoginForm getLogin() {
        return login;
    }

}
