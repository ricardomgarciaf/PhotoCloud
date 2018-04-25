package com.example.ricardogarcia.photocloud.activities.home;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.ricardogarcia.photocloud.R;
import com.example.ricardogarcia.photocloud.activities.home.core.HomeModel;
import com.example.ricardogarcia.photocloud.activities.home.core.HomePresenter;
import com.example.ricardogarcia.photocloud.activities.home.core.HomeView;
import com.example.ricardogarcia.photocloud.activities.home.dagger.DaggerHomeComponent;
import com.example.ricardogarcia.photocloud.activities.home.dagger.HomeModule;
import com.example.ricardogarcia.photocloud.application.PhotoCloudApplication;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements HomeView {

    @BindView(R.id.recyclerViewGallery)
    RecyclerView recyclerViewGallery;

    @Inject
    HomeModel model;

    private HomePresenter presenter;

    private MaterialDialog progressDialog;

    private MaterialDialog inputDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        DaggerHomeComponent.builder().appComponent(PhotoCloudApplication.getAppComponent()).homeModule(new HomeModule(this)).build().inject(this);

        progressDialog = new MaterialDialog.Builder(this)
                .title(R.string.loading)
                .progress(true, 0).build();

        presenter= new HomePresenter(this,model);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.create:
                createAlbum();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void showExistingAlbumName() {
        Toast.makeText(this, getString(R.string.existing_album_name), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAlbumError() {
        Toast.makeText(this, getString(R.string.empty_album), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAlbumCreated() {
        inputDialog.dismiss();
    }

    private void createAlbum() {
        inputDialog = new MaterialDialog.Builder(this)
                .title(R.string.create_album)
                .content(R.string.enter_album_name)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .autoDismiss(false)
                .input(getString(R.string.album_hint), "", (dialog, input) -> presenter.createAlbum(input.toString())).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
