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
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import mysnapp.app.dei.com.mysnapp.MyApp;
import mysnapp.app.dei.com.mysnapp.R;
import mysnapp.app.dei.com.mysnapp.data.local.entity.Graphic;
import mysnapp.app.dei.com.mysnapp.utils.SingleLiveData;
import mysnapp.app.dei.com.mysnapp.view.adapters.GenericRecyclerAdapter;

public class GraphicsFragment extends Fragment {

    private RecyclerView recyclerView;
    public LiveData<List<Graphic>> liveData;
    private List<Graphic> graphics = new ArrayList<>();
    private static ImageLoader imageLoader = MyApp.getImageLoader();
    public SingleLiveData<Graphic> singleLiveData = new SingleLiveData();
    private ProgressBar progress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_slide_page_2, container, false);

        recyclerView = rootView.findViewById(R.id.recycler);
        progress = rootView.findViewById(R.id.progress);
        setupRecyclerView();
        setListeners();

        return rootView;

    }


    private void setListeners() {
        liveData.observe(this, val -> {
            if (val != null && val.size() > 0) {
                progress.setVisibility(View.GONE);
                graphics.clear();
                graphics.addAll(val);
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        });
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new GenericRecyclerAdapter<Graphic, Holder>(graphics) {
            @NonNull
            @Override
            public Holder onCreateViewHolder(@NonNull ViewGroup parent, int pos) {
                View v = LayoutInflater.from(getContext()).inflate(R.layout.row_border, parent, false);
                return new Holder(v);
            }

            @Override
            public void onBindViewHolder(@NonNull Holder holder, int pos) {
                pos = holder.getAdapterPosition();
                Graphic border = graphics.get(pos);
                imageLoader.displayImage(border.getGraphicFilePath().replace(" ", ""), holder.imageView, MyApp.getDisplayImageOptions());
                holder.imageView.setOnClickListener(view -> {
                    singleLiveData.postValue(border);
                });
            }
        });
    }

    static class Holder extends RecyclerView.ViewHolder {

        ImageView imageView;
        public Holder(@NonNull View v) {
            super(v);
            imageView = v.findViewById(R.id.imageView);
        }
    }

}
