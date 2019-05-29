package mysnapp.app.dei.com.mysnapp.editphoto;


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


public class PageFragment extends Fragment {

    private RecyclerView recyclerView;

    public static<T> PageFragment newInstance(@NonNull final T image) {
        //Bundle arguments = new Bundle();
        //arguments.putString(PIC_URL, image.getImageUrl());

        PageFragment fragment = new PageFragment();
        //fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_slide_page_2, container, false);

        recyclerView = rootView.findViewById(R.id.recycler);
        setupRecyclerView();

        return rootView;
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

