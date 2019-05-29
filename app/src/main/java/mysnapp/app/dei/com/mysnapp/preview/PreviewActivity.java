package mysnapp.app.dei.com.mysnapp.preview;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import mysnapp.app.dei.com.mysnapp.R;
import mysnapp.app.dei.com.mysnapp.common.SuperActivity;
import mysnapp.app.dei.com.mysnapp.data.local.entity.Image;
import mysnapp.app.dei.com.mysnapp.photodetail.PhotoDetailVM;

public class PreviewActivity extends SuperActivity {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.imgClose)
    ImageView imgClose;

    private Image image;
    private PreviewVM viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_preview);
        ButterKnife.bind(this);
        setViewModel(savedInstanceState);
        initialize();
        setEventListeners();
    }

    private void setEventListeners() {
        imgClose.setOnClickListener(view -> onBackPressed());
    }

    private void setViewModel(Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this).get(PreviewVM.class);
        if (savedInstanceState == null) {
            viewModel.init(this,
                    getIntent().getIntExtra("POSITION", 0),
                    getIntent().getParcelableExtra("ITEM"),
                    getIntent().getParcelableArrayListExtra("ITEM_LIST")
            );
        }
    }

    private void initialize() {
        viewPager.setOffscreenPageLimit(10);
        viewPager.setAdapter(viewModel.getPagerAdapter());
        new Handler().postAtTime(() -> {
            viewPager.setCurrentItem(viewModel.getImagePosition());
        }, 100);
    }
}
