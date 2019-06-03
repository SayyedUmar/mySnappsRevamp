package mysnapp.app.dei.com.mysnapp.editphoto;


import android.arch.lifecycle.LiveData;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mysnapp.app.dei.com.mysnapp.R;
import mysnapp.app.dei.com.mysnapp.data.local.entity.Image;


public class PageFragment extends Fragment {

    private RecyclerView recyclerView;
    private String title;
    private LiveData liveData;

    public static<T> PageFragment newInstance(String title, @NonNull Image image, LiveData<T> liveData) {
        Bundle arguments = new Bundle();
        PageFragment fragment = new PageFragment();
        fragment.setArguments(arguments);
        fragment.liveData = liveData;
        fragment.title = title;

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_slide_page_2, container, false);

        recyclerView = rootView.findViewById(R.id.recycler);
        setupRecyclerView();
        setListeners();

        return rootView;
    }

    private void setListeners() {
       /* liveData.observe(this, o -> {
            if (title.equalsIgnoreCase("Border")) {

            }
        });*/
    }


    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);
        //recyclerView.setAdapter(new GenericAdapter<>());
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

