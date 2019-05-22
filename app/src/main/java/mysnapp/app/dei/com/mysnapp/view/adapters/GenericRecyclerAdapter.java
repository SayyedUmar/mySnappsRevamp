package mysnapp.app.dei.com.mysnapp.view.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import mysnapp.app.dei.com.mysnapp.R;

public class GenericRecyclerAdapter<T> extends RecyclerView.Adapter<GenericRecyclerAdapter.Holder> {

    List<T> list;

    public GenericRecyclerAdapter (List<T> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int pos) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_border, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder viewHolder, int pos) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class Holder extends RecyclerView.ViewHolder {

        public Holder(@NonNull View v) {
            super(v);
        }
    }

}
