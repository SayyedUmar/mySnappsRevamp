package mysnapp.app.dei.com.mysnapp.login;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import mysnapp.app.dei.com.mysnapp.login.models.LoginFields;

public class LoginViewModel extends BaseObservable {

    private String username;
    private String password;
    private boolean rememberMe;

    private LoginFields loginModel;



    @Bindable
    public Boolean getRememberMe() {
        return loginModel.rememberMe;
    }

    public void rememberMeChanged(Boolean value) {
        // Avoids infinite loops.
        if (loginModel.rememberMe != value) {
            loginModel.rememberMe = value;

            // React to the change.
//            saveData();

            // Notify observers of a new value.
//            notifyPropertyChanged(BR.remember_me);
        }
    }

    @Bindable
    public String getPasswordQuality() {
        if (password == null || password.isEmpty()) {
            return "Enter a password";
        } else if (password.equals("password")) {
            return "Very bad";
        } else if (password.length() < 6) {
            return "Short";
        } else {
            return "Okay";
        }
    }

    public void setPassword(String password) {
        this.password = password;
        //notifyPropertyChanged(BR.passwordQuality);
    }

}
