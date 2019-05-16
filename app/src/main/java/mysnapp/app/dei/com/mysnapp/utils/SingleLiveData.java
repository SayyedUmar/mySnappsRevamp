package mysnapp.app.dei.com.mysnapp.utils;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.concurrent.atomic.AtomicBoolean;

public class SingleLiveData<T> extends MutableLiveData<T> {

    private AtomicBoolean mPending = new AtomicBoolean(false);
    private static final String TAG = "SingleLiveData";

    @MainThread
    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<T> observer) {

        if (hasActiveObservers()) {
            Logs.e(TAG, "Multiple observers registered but only one will be notified of changes.");
        }

        super.observe(owner, val -> {
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(val);
            }
        });

        //super.observe(owner, observer);
    }


    @MainThread
    @Override
    public void setValue(T t) {
        mPending.set(true);
        super.setValue(t);
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    public void call() {
        //value = null;
    }
}
/*

class SingleLiveData<T> : MutableLiveData<T>() {

    private val mPending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<T>) {

        if (hasActiveObservers()) {
            Log.w(TAG, "Multiple observers registered but only one will be notified of changes.")
        }

        // Observe the internal MutableLiveData
        super.observe(owner, Observer { t ->
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        })
    }

    @MainThread
    override fun setValue(t: T?) {
        mPending.set(true)
        super.setValue(t)
    }

@MainThread
fun call() {
    value = null
}

    companion object {
private val TAG = "SingleLiveData"
        }
        }
*/