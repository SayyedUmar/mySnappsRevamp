package mysnapp.app.dei.com.mysnapp.gallary;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import mysnapp.app.dei.com.mysnapp.R;
import mysnapp.app.dei.com.mysnapp.data.local.entity.Image;
import mysnapp.app.dei.com.mysnapp.databinding.ActivityGalleryBinding;
import mysnapp.app.dei.com.mysnapp.view.adapters.GenericAdapter;
import mysnapp.app.dei.com.mysnapp.view.base.BaseActivity;


public class GalleryActivity  extends BaseActivity<ActivityGalleryBinding> {

    private static final String TAG = "GalleryActivity";

    private GalleryActivityVM viewModel;
    private AlertDialog progress;
    private List<Image> list = new ArrayList<>();

    private GenericAdapter adapter;

    @BindView(R.id.gridView)
    GridView gridView;
    @BindView(R.id.tvHeading)
    TextView tvHeading;
    @BindView(R.id.img_back)
    ImageView img_back;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_gallery;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);
        viewModel = ViewModelProviders.of(this).get(GalleryActivityVM.class);
        if (savedInstanceState ==  null) {
            viewModel.init(this);
        }

        initialize();
        setEventListeners();
    }

    static class Holder {
        ImageView imageView;
        public Holder (View v) {
            imageView = v.findViewById(R.id.imageView);
        }
    }

    private void initialize() {

        progress = new SpotsDialog(this, R.style.DilaogStyle);

        gridView.setColumnWidth(GridView.AUTO_FIT);
        adapter = viewModel.getAdapter(list);
        gridView.setAdapter(adapter);
    }

    private void setEventListeners() {

        img_back.setOnClickListener(v -> onBackPressed());

        viewModel.getAllImages().observe(this, images -> {
            if (images != null) {
                this.list.clear();
                this.list.addAll(images);
                adapter.notifyDataSetChanged();
            }
        });
    }

}