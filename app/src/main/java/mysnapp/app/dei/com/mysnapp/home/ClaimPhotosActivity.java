package mysnapp.app.dei.com.mysnapp.home;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import mysnapp.app.dei.com.mysnapp.R;
import mysnapp.app.dei.com.mysnapp.databinding.ActivityLogin1Binding;
import mysnapp.app.dei.com.mysnapp.utils.Logs;
import mysnapp.app.dei.com.mysnapp.view.base.BaseActivity;

public class ClaimPhotosActivity  extends BaseActivity<ActivityLogin1Binding>
                                    implements NavigationView.OnNavigationItemSelectedListener {


    @BindView(R.id.btnCloseDrawer)
    ImageButton btnCloseDrawer;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.btnClaimPhoto)
    Button btnClaimPhoto;
    @BindView(R.id.btnClaimedPhotos)
    Button btnClaimedPhotos;
    @BindView(R.id.etClaimCode)
    EditText etClaimCode;

    private ClaimPhotosViewModel viewModel;
    private ActionBarDrawerToggle toggle;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_claim_photos;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_claim_photos);
        ButterKnife.bind(this);
        setViewModel();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setEventListeners();

    }

    private void setViewModel() {
        viewModel = ViewModelProviders.of(this).get(ClaimPhotosViewModel.class);
    }

    private void setEventListeners() {
        btnCloseDrawer.setOnClickListener(v -> {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        btnClaimPhoto.setOnClickListener(v -> {
            claimPhotoAction();
        });

        viewModel.getClaimCodeResponse().observe(this, res -> {

            if (res.ResponseCode == "200" || res.ResponseCode == "000") {
                Logs.shortToast(this, "QR Code claimed successfully.");
            } else {
                Logs.shortToast(this, res.ResponseMessage);
            }
        });

        viewModel.getClaimCodeError().observe(this, err -> {
            Logs.shortToast(this, err.getMessage());
        });
    }

    private void claimPhotoAction() {
        etClaimCode.setError(null);
        String qrCode = etClaimCode.getText().toString().trim();
        if (qrCode.isEmpty()) {
            etClaimCode.setError("Please enter QR Code.");
            etClaimCode.requestFocus();
        } else if (qrCode.length() < 6) {
            etClaimCode.setError("Please enter valid QR Code.");
            etClaimCode.requestFocus();
        } else {
            viewModel.claimQRCode(qrCode);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.onDestroy();
    }
}
