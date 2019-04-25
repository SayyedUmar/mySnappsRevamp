package mysnapp.app.dei.com.mysnapp.login.models;

import android.databinding.ObservableBoolean;

public class LoginModel {

    private String email;
    private String password;
    public boolean rememberMe;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
