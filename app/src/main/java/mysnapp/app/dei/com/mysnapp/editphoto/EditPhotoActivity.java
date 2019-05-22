package mysnapp.app.dei.com.mysnapp.editphoto;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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
        List<String> title = new ArrayList<>();
        title.add("Border");
        title.add("Sticker");
        title.add("Contrast");
        title.add("Text");
        if (savedInstanceState == null) {
            viewModel.init(getIntent().getParcelableExtra("ITEM"),title, this);
        }
    }

    private void initialize() {
        MyApp.getImageLoader().displayImage(viewModel.getModel().getImageUrl(), imageView);
        viewPager.setAdapter(viewModel.getPagerAdapter());
        tabs.setupWithViewPager(viewPager);
    }

    private void setEventListeners() {

    }

    private void setObservers() {

    }

}
