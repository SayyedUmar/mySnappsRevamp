package mysnapp.app.dei.com.mysnapp.editphoto;

import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.co.cyberagent.android.gpuimage.GPUImageView;
import mysnapp.app.dei.com.mysnapp.MyApp;
import mysnapp.app.dei.com.mysnapp.R;
import mysnapp.app.dei.com.mysnapp.common.SuperActivity;

public class EditPhotoActivity extends SuperActivity {


    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.linlay_back)
    LinearLayout linlay_back;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.gpuImageView)
    GPUImageView gpuImageView;
    @BindView(R.id.tvHeading)
    TextView tvHeading;
    @BindView(R.id.img_back)
    ImageView img_back;
    @BindView(R.id.tvSave)
    TextView tvSave;

    private EditPhotoVM viewModel;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_photo);
        ButterKnife.bind(this);

        setViewModel(savedInstanceState);
        initialize();
        setEventListeners();
        setObservers();
    }

    private void setViewModel(Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this).get(EditPhotoVM.class);

        if (savedInstanceState == null) {
            viewModel.init(getIntent().getParcelableExtra("ITEM"), this);
        }
    }

    private void initialize() {
        //((TouchImageView)imageView).setMaxZoom(5);
        MyApp.getImageLoader().displayImage(viewModel.getModel().getImageUrl(), imageView);
        viewPager.setAdapter(viewModel.getPagerAdapter());
        tabs.setupWithViewPager(viewPager);
    }

    private void setEventListeners() {
        img_back.setOnClickListener(v -> onBackPressed());

        viewModel.getLiveBorders().observe(this, val -> {
            if (val != null && val.size() > 0) {
                viewModel.updateBorders();
            } else {
                viewModel.fetchAllBorders();
            }
        });

        viewModel.getLiveGraphics().observe(this, val -> {
            if (val != null && val.size() > 0) {
                viewModel.updateGraphics();
            } else {
                viewModel.fetchAllGraphics();
            }
        });

        tvSave.setOnClickListener(view -> {
            gpuImageView.setImage(MyApp.getImageLoader().getDiskCache().get(viewModel.getModel().getImageUrl()));
        });

        viewModel.getBorderLiveData().observe(this, border -> {
            File file = MyApp.getImageLoader().getDiskCache().get(border.getBorderFilePath().replace(" ", ""));
            Bitmap frame = BitmapFactory.decodeFile(file.getPath());
            Resources r = getResources();
            Drawable framedrawable = new BitmapDrawable(r, frame);
            int width = imageView.getWidth();
            int height = imageView.getHeight();

           /* if (height > width) {
                if (ImageRotationAngle == 90 || ImageRotationAngle == 270) {
                    linImagComtaier.setLayoutParams(new FrameLayout.LayoutParams(height, width, Gravity.CENTER));
                    testImageView.setLayoutParams(new FrameLayout.LayoutParams(width, height, Gravity.CENTER));
                    lin_back.setLayoutParams(new FrameLayout.LayoutParams(width, height, Gravity.CENTER));
                } else {
                    linImagComtaier.setLayoutParams(new FrameLayout.LayoutParams(width, height, Gravity.CENTER));
                    testImageView.setLayoutParams(new FrameLayout.LayoutParams(width, height, Gravity.CENTER));
                    lin_back.setLayoutParams(new FrameLayout.LayoutParams(width, height, Gravity.CENTER));
                }
            } else {
                if (ImageRotationAngle == 0 || ImageRotationAngle == 180) {
                    linImagComtaier.setLayoutParams(new FrameLayout.LayoutParams(width, height, Gravity.CENTER));
                    testImageView.setLayoutParams(new FrameLayout.LayoutParams(width, height, Gravity.CENTER));
                    lin_back.setLayoutParams(new FrameLayout.LayoutParams(width, height, Gravity.CENTER));
                } else {
                    linImagComtaier.setLayoutParams(new FrameLayout.LayoutParams(height, width, Gravity.CENTER));
                    testImageView.setLayoutParams(new FrameLayout.LayoutParams(width, height, Gravity.CENTER));
                    lin_back.setLayoutParams(new FrameLayout.LayoutParams(width, height, Gravity.CENTER));
                }
            }*/
            linlay_back.setLayoutParams(new FrameLayout.LayoutParams(width, height, Gravity.CENTER));
            linlay_back.setBackground(framedrawable);

        });
    }

    private void setObservers() {

    }

}
