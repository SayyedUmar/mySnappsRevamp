package mysnapp.app.dei.com.mysnapp.repos;

import android.util.Log;

public class LoginRepo {

    private static LoginRepo instance;

    public static LoginRepo getInstance() {
        if (instance == null) {
            instance = new LoginRepo();
        }
        return instance;
    }

    private LoginRepo () {

    }

    public void getLoginData () {

    }

}
