package mysnapp.app.dei.com.mysnapp.view.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class GenericAdapter<T> extends BaseAdapter {

    List<T> list;

    public GenericAdapter(List<T> mList) {
        list = mList;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public T getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int pos, View view, ViewGroup parent) {
        if (view == null) {
            view = onCreateView(view, pos, parent);
        }
        onBindView(view, pos, getItem(pos));
        return view;
    }

    public abstract View onCreateView(View v, int pos, ViewGroup parent);
    public abstract void onBindView (View v, int pos, T item);
}
