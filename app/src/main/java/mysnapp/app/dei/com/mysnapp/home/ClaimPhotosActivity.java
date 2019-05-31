package mysnapp.app.dei.com.mysnapp.home;

import android.app.AlertDialog;
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
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.zxing.integration.android.IntentIntegrator;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.net.URI;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import mysnapp.app.dei.com.mysnapp.R;
import mysnapp.app.dei.com.mysnapp.common.SuperActivity;
import mysnapp.app.dei.com.mysnapp.gallary.GalleryActivity;
import mysnapp.app.dei.com.mysnapp.login.LoginActivityNew;
import mysnapp.app.dei.com.mysnapp.utils.Logs;

public class ClaimPhotosActivity  extends SuperActivity
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
    @BindView(R.id.btnScan)
    Button btnScan;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.img_back)
    ImageView img_back;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    private ClaimPhotosViewModel viewModel;
    private AlertDialog progress;
    private LinearLayout linLogout;

    /*@Override
    protected int getLayoutRes() {
        return R.layout.activity_claim_photos;
    }
*/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_claim_photos);
        ButterKnife.bind(this);
        setViewModel(savedInstanceState);

        setSupportActionBar(toolbar);
        img_back.setVisibility(View.GONE);

        progress = new SpotsDialog(this, R.style.DilaogStyle);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        linLogout =  navigationView.findViewById(R.id.linlayLogout);

        setEventListeners();
    }

    private void setViewModel(Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this).get(ClaimPhotosViewModel.class);
        if (savedInstanceState == null)
            viewModel.init();
    }

    private void setEventListeners() {
        linLogout.setOnClickListener(view -> {
            startActivity(new Intent(this, LoginActivityNew.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            );
            finish();
        });

        btnCloseDrawer.setOnClickListener(v -> {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        btnClaimPhoto.setOnClickListener(v -> {
            progress.show();
            claimPhotoAction();
        });

        btnScan.setOnClickListener(v -> {
            //startActivity(new Intent(this, ScanQRCodeActivity.class));
            openQrCodeScanner();
        });

        viewModel.getClaimCodeResponse().observe(this, res -> {
            progress.dismiss();
            if (res.ResponseCode == "200" || res.ResponseCode == "000") {
                Logs.shortToast(this, "QR Code claimed successfully.");
            } else {
                Logs.shortToast(this, res.ResponseMessage);
            }
        });

        viewModel.getClaimCodeError().observe(this, err -> {
            progress.dismiss();
            Logs.shortToast(this, err.getMessage());
        });

        btnClaimedPhotos.setOnClickListener(v -> {
            startActivity(new Intent(this, GalleryActivity.class));
        });
    }

    private void openQrCodeScanner() {

        AndPermission.with(this)
                .runtime()
                .permission(Permission.Group.CAMERA)
                .onGranted(permissions -> openCameraScan())
                .onDenied(permissions -> Logs.shortToast(getApplicationContext(), "Camera access permission denied!"))
                .start();

    }

    private void openCameraScan () {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scan your QR card.");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(true);
        integrator.initiateScan();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IntentIntegrator.REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String result = data.getStringExtra("SCAN_RESULT");
                String format = data.getStringExtra("SCAN_RESULT_FORMAT");

                if (format.equalsIgnoreCase("QR_CODE")) {

                    if (URLUtil.isValidUrl(result)) {
                        try {
                            URI uri = new URI(result);
                            String query = uri.getQuery();
                            int index = query.indexOf("=");
                            String qrCode = "";
                            if (index != -1)
                                qrCode = query.substring(query.indexOf("=")+1);
                            if (qrCode.trim().isEmpty() || qrCode.length() < 6) {
                                startActivity(new Intent(this, WebviewActivity.class).putExtra("Url", result));
                            } else {
                                etClaimCode.setText(qrCode);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            startActivity(new Intent(this, WebviewActivity.class).putExtra("Url", result));
                        }

                    } else {etClaimCode.setText(result);}
                } else {etClaimCode.setText(result); }

            } else if (resultCode == RESULT_CANCELED && data != null) {etClaimCode.setText("");}
        }
    }

}
