package mysnapp.app.dei.com.mysnapp.photodetail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import mysnapp.app.dei.com.mysnapp.MyApp;
import mysnapp.app.dei.com.mysnapp.R;
import mysnapp.app.dei.com.mysnapp.common.SuperActivity;
import mysnapp.app.dei.com.mysnapp.editphoto.EditPhotoActivity;
import mysnapp.app.dei.com.mysnapp.preview.PreviewActivity;
import mysnapp.app.dei.com.mysnapp.utils.Logs;

public class PhotoDetailActivity extends SuperActivity {


    @BindView(R.id.img_back)
    ImageView img_back;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.tvEdit)
    TextView tvEdit;
    @BindView(R.id.tvSave)
    TextView tvSave;
    @BindView(R.id.tvShare)
    TextView tvShare;
    @BindView(R.id.tvPurchase)
    TextView tvPurchase;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.viewPager)
    ViewPager viewPager;


    private PhotoDetailVM viewModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_photo_detail);
        ButterKnife.bind(this);

        setViewModel(savedInstanceState);
        initialize();
        setEventListeners();
        setObservers();
    }

    private void setViewModel(Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this).get(PhotoDetailVM.class);
        if (savedInstanceState == null) {
            viewModel.init(this,
                    getIntent().getIntExtra("POSITION", 0),
                    getIntent().getParcelableExtra("ITEM"),
                    getIntent().getParcelableArrayListExtra("ITEM_LIST")
            );
        }
    }

    private void setObservers() {
        viewModel.getImageSaveListener().observe(this, msg -> {
            if (!msg.isEmpty())
                Logs.shortToast(getApplicationContext(), msg);
            progressBar.setVisibility(View.GONE);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Disposable d = MyApp.getBus().toObservable().subscribe(viewModel.getConsumer());
        viewModel.getCompositeDisposable().add(d);
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewModel.getCompositeDisposable().clear();
    }

    @Override
    protected void onStop() {
        super.onStop();
        viewModel.getCompositeDisposable().clear();
    }

    private void initialize() {

        viewModel.setConsumer(image ->
                startActivity(new Intent(this, PreviewActivity.class)
                        .putParcelableArrayListExtra("ITEM_LIST", (ArrayList<? extends Parcelable>) viewModel.getImageList())
                        .putExtra("POSITION", viewModel.getImagePosition())
                        .putExtra("ITEM", image)
                )
        );

        viewModel.getImageLoader().displayImage(viewModel.getModel().getImageUrl(), imageView);
        viewPager.setOffscreenPageLimit(10);
        viewPager.setAdapter(viewModel.getPagerAdapter());
        new Handler().postAtTime(() -> {
            viewPager.setCurrentItem(viewModel.getImagePosition());
        }, 100);
    }

    private void setEventListeners() {

        img_back.setOnClickListener(v -> onBackPressed());

        tvEdit.setOnClickListener(v -> {
            viewModel.enableBackground(tvEdit, tvSave, tvShare, tvPurchase);
            startActivity(new Intent(this, EditPhotoActivity.class)
                    .putExtra("ITEM", viewModel.getModel())
            );
        });

        tvSave.setOnClickListener(v -> {
            viewModel.enableBackground(tvSave, tvEdit, tvShare, tvPurchase);
            saveORshareImage(true);
        });

        tvShare.setOnClickListener(v -> {
            viewModel.enableBackground(tvShare, tvEdit, tvSave, tvPurchase);
            saveORshareImage(false);
        });

        tvPurchase.setOnClickListener(v -> {
            viewModel.enableBackground(tvPurchase, tvEdit, tvSave, tvShare);
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }
            @Override
            public void onPageSelected(int position) {
                viewModel.setModel(position);
                viewModel.getImageLoader().displayImage(viewModel.getModel().getImageUrl(), imageView, MyApp.getDisplayImageOptions());
            }
            @Override
            public void onPageScrollStateChanged(int state) { }
        });
    }

    private void saveORshareImage(boolean toSave) {

        AndPermission.with(this)
                .runtime()
                .permission(Permission.Group.STORAGE)
                .onGranted(permissions ->  {
                    BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                    if (drawable != null) {
                        progressBar.setVisibility(View.VISIBLE);
                        if (toSave) {
                            viewModel.saveToGallery(viewPager.getCurrentItem(), drawable.getBitmap());
                        } else {
                            viewModel.saveShareImage(viewPager.getCurrentItem(), drawable.getBitmap(), this);
                        }
                    }
                })
                .onDenied(permissions -> Logs.shortToast(getApplicationContext(), "Storage access permission denied"))
                .start();
    }

}
