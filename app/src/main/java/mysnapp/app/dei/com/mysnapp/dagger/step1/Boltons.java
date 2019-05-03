package mysnapp.app.dei.com.mysnapp.dagger.step1;

import android.util.Log;

import javax.inject.Inject;

public class Boltons implements House {

    private static final String TAG = "Boltons";

    @Inject
    public Boltons() {

    }

    @Override
    public void rule() {
        Log.e(TAG, "rule: ");
    }

    @Override
    public void prepareForWar() {
        Log.e(TAG, "prepareForWar: ");
    }

    @Override
    public void reportToWar() {
        Log.e(TAG, "reportToWar: ");
    }


}
