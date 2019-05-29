package mysnapp.app.dei.com.mysnapp.login.models;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import mysnapp.app.dei.com.mysnapp.BR;
import mysnapp.app.dei.com.mysnapp.R;
import mysnapp.app.dei.com.mysnapp.utils.Const;
import mysnapp.app.dei.com.mysnapp.utils.MyPreferences;

public class LoginForm extends BaseObservable {
    private LoginModel model = new LoginModel();
    private LoginErrorModel errors = new LoginErrorModel();

    @Bindable
    public boolean isValid() {
        boolean valid = isEmailValid(false);
        valid = isPasswordValid(false) && valid;
        notifyPropertyChanged(BR.emailError);
        notifyPropertyChanged(BR.passwordError);
        return valid;
    }

    public boolean isEmailValid(boolean setMessage) {
        // Minimum a@b.c
        String email = model.getEmail();
        if (email != null && email.length() > 5) {
            int indexOfAt = email.indexOf("@");
            int indexOfDot = email.lastIndexOf(".");
            if (indexOfAt > 0 && indexOfDot > indexOfAt && indexOfDot < email.length() - 1) {
                errors.setEmail(null);
                notifyPropertyChanged(BR.valid);
                return true;
            } else {
                if (setMessage) {
                    errors.setEmail(R.string.error_format_invalid);
                    notifyPropertyChanged(BR.valid);
                }
                return false;
            }
        } else if (setMessage) {
            errors.setEmail(R.string.error_too_short);
            notifyPropertyChanged(BR.valid);
        }

        return false;
    }

    public boolean isPasswordValid(boolean setMessage) {
        String password = model.getPassword();
        if (password != null && password.length() > 7) {
            errors.setPassword(null);
            notifyPropertyChanged(BR.valid);
            return true;
        } else {
            if (setMessage) {
                errors.setPassword(R.string.error_too_short);
                notifyPropertyChanged(BR.valid);
            }
            return false;
        }
    }

    public boolean isValidData(Context context) {
        if (isValid()) {
            MyPreferences.setBoolValue(context, Const.REMEMBER_ME, model.rememberMe);
            if (model.rememberMe) {
                MyPreferences.setStringValue(context, Const.USERNAME, model.getEmail());
                MyPreferences.setStringValue(context, Const.PASSWORD, model.getPassword());
            }
            return true;
        }
        return false;
    }

    public LoginModel getModel() {
        return model;
    }

    @Bindable
    public Integer getEmailError() {
        return errors.getEmail();
    }

    @Bindable
    public Integer getPasswordError() {
        return errors.getPassword();
    }

    @Bindable
    public Boolean getRememberMe() {
        return model.rememberMe;
    }

    public void rememberMeChanged(boolean value) {
        // Avoids infinite loops.
        if (model.rememberMe != value) {
            model.rememberMe = value;

            notifyPropertyChanged(BR.rememberMe);
        }
    }

}
