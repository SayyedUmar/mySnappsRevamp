package mysnapp.app.dei.com.mysnapp.editphoto;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import mysnapp.app.dei.com.mysnapp.MyApp;
import mysnapp.app.dei.com.mysnapp.R;
import mysnapp.app.dei.com.mysnapp.common.SuperActivity;

public class EditPhotoActivity extends SuperActivity {


    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.tvHeading)
    TextView tvHeading;

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
        MyApp.getImageLoader().displayImage(viewModel.getModel().getImageUrl(), imageView);
        viewPager.setAdapter(viewModel.getPagerAdapter());
        tabs.setupWithViewPager(viewPager);
    }

    private void setEventListeners() {
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
    }

    private void setObservers() {

    }

}
