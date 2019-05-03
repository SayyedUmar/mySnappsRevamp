package mysnapp.app.dei.com.mysnapp.dagger.step1;

import android.util.Log;

import javax.inject.Inject;

public class Starks implements House {

    private static final String TAG = "Starks";

    @Inject
    public Starks () {

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



//
//    @Inject
//    Allies allies;
//
//    @Inject
//    public Starks(){
//        //do something..
//    }
//
//    //Method injection
//    @Inject
//    private void prepareForWar(){
//        //do something..
//    }
}
