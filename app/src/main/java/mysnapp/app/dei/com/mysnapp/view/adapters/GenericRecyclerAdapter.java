package mysnapp.app.dei.com.mysnapp.view.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

public abstract class GenericRecyclerAdapter<T, R extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<R> {

    List<T> list;

    public GenericRecyclerAdapter (List<T> list) {
        this.list = list;
    }

    @NonNull
    @Override
    abstract public R onCreateViewHolder(@NonNull ViewGroup parent, int pos);


    @Override
    abstract public void onBindViewHolder(@NonNull R viewHolder, int pos);

    @Override
    public int getItemCount() {
        return list.size();
    }


}
