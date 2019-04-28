package mysnapp.app.dei.com.mysnapp.login;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ObservableBoolean;
import android.support.annotation.VisibleForTesting;
import android.view.View;
import android.widget.EditText;

import mysnapp.app.dei.com.mysnapp.data.remote.ResponseModel;
import mysnapp.app.dei.com.mysnapp.login.models.LoginForm;
import mysnapp.app.dei.com.mysnapp.login.models.LoginModel;
import mysnapp.app.dei.com.mysnapp.repos.LoginRepo;
import mysnapp.app.dei.com.mysnapp.utils.Const;
import mysnapp.app.dei.com.mysnapp.utils.MyPreferences;

public class LoginViewModel extends ViewModel {

    //private LoginModel loginModel;
    private LoginForm login;
    private View.OnFocusChangeListener onFocusEmail;
    private View.OnFocusChangeListener onFocusPassword;
    public ObservableBoolean showLoader = new ObservableBoolean(false);
    private LoginRepo repo = LoginRepo.getInstance();

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
        if (login.isValidData(view.getContext())) {
            showLoader.set(true);
            loginResponse();
        }
    }

    public LoginForm getLogin() {
        return login;
    }

    void loginResponse() {


        repo.loginUser(login.getModel().getEmail(), login.getModel().getPassword());

      /*repo.fetchUserData1(login.getModel().getEmail(), login.getModel().getPassword())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Response<ResponseBody>>() {
                    @Override
                    public void onNext(Response<ResponseBody> response) {
                        Log.e("onNext","onNext");
                        if (response.isSuccessful()){
                            try {
                                JSONObject res = new JSONObject(response.body().string());
                                Log.e("",res.toString());
                            } catch (Exception e) { e.printStackTrace(); }
                        }
                    }
                    @Override
                    public void onError(Throwable e) {  Log.e("onError","onError"); }
                    @Override
                    public void onComplete() {}
                });*/

        /*repo.performLogin(login.getModel().getEmail(), login.getModel().getPassword())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<ResponseModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("onSubscribe","");
                    }

                    @Override
                    public void onNext(ResponseModel responseModel) {
                        Log.e("onNext","onNext");
                        //response.postValue(responseModel);
                    }

                    @Override
                    public void onError(Throwable e) {
                        showLoader.set(false);
                        Log.e("onError","onError");
                    }

                    @Override
                    public void onComplete() {
                        showLoader.set(false);
                        Log.e("onComplete","onComplete");
                    }
                });*/
    }

    public void onDestroy () {
        repo.unsubcribe();
    }

    public LiveData<ResponseModel> getLoginResponse() {
        return repo.getLoginReponse();
    }
    public LiveData<Throwable> getLoginError() {
        return repo.getLoginError();
    }
}