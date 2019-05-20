package mysnapp.app.dei.com.mysnapp.photodetail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import mysnapp.app.dei.com.mysnapp.R;
import mysnapp.app.dei.com.mysnapp.common.SuperActivity;
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

    private ImageLoader imageLoader;
    private PhotoDetailVM viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_photo_detail);
        ButterKnife.bind(this);

        initialize();
        setEventListeners();
        setObservers();
    }

    private void setObservers() {
        viewModel.getImageSaveListener().observe(this, msg -> {
            Logs.shortToast(getApplicationContext(), msg);
            progressBar.setVisibility(View.GONE);
        });

        viewModel.getImageShareListener().observe(this, msg -> {
            shareImage((Uri) msg[0]);
            progressBar.setVisibility(View.GONE);
        });
    }



    private void initialize() {
        viewModel = ViewModelProviders.of(this).get(PhotoDetailVM.class);
        viewModel.setModel(getIntent().getParcelableExtra("ITEM"));
        imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(viewModel.getModel().getImageUrl(), imageView);
    }

    private void setEventListeners() {
        img_back.setOnClickListener(v -> onBackPressed());

        tvEdit.setOnClickListener(v -> {
            enableBackground(tvEdit, tvSave, tvShare, tvPurchase);
        });
        tvSave.setOnClickListener(v -> {
            enableBackground(tvSave, tvEdit, tvShare, tvPurchase);
            saveORshareImage(true);
        });
        tvShare.setOnClickListener(v -> {
            enableBackground(tvShare, tvEdit, tvSave, tvPurchase);
            saveORshareImage(false);
        });
        tvPurchase.setOnClickListener(v -> {
            enableBackground(tvPurchase, tvEdit, tvSave, tvShare);
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
                            viewModel.saveToGallery(drawable.getBitmap());
                        } else {
                            viewModel.saveShareImage(drawable.getBitmap());
                        }
                    }
                })
                .onDenied(permissions -> Logs.shortToast(getApplicationContext(), "Storage access permission denied!"))
                .start();
    }

    public void enableBackground(TextView selected, TextView...unselected) {
        selected.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_rect_round_red));
        for (TextView item : unselected) {
            item.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_rect_round_gray));
        }
    }

    private void shareImage(Uri uri) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        share.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(share, "Share Image"));
    }
}
