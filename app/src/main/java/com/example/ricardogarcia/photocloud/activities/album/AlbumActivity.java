package com.example.ricardogarcia.photocloud.activities.album;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.ricardogarcia.photocloud.R;
import com.example.ricardogarcia.photocloud.activities.album.core.AlbumModel;
import com.example.ricardogarcia.photocloud.activities.album.core.AlbumPresenter;
import com.example.ricardogarcia.photocloud.activities.album.core.AlbumView;
import com.example.ricardogarcia.photocloud.activities.album.dagger.AlbumModule;
import com.example.ricardogarcia.photocloud.activities.album.dagger.DaggerAlbumComponent;
import com.example.ricardogarcia.photocloud.application.PhotoCloudApplication;
import com.example.ricardogarcia.photocloud.utils.ConstantUtils;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlbumActivity extends AppCompatActivity implements AlbumView {

    @Inject
    AlbumModel albumModel;

    @BindView(R.id.recyclerViewGallery)
    RecyclerView recyclerViewGallery;

    AlbumPresenter albumPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        ButterKnife.bind(this);

        DaggerAlbumComponent.builder().appComponent(PhotoCloudApplication.getAppComponent()).albumModule(new AlbumModule(this)).build().inject(this);

        Intent intent= getIntent();
        String albumName=intent.getStringExtra(ConstantUtils.ALBUM_NAME);

        albumPresenter= new AlbumPresenter(this,albumModel);
        albumPresenter.onCreate(albumName);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        albumPresenter.onDestroy();
    }

    @Override
    public void onTakePictureClicked() {

    }

    @Override
    public void onImportPhotoClicked() {

    }

    public void loadPhotos(List<HashMap<String, String>> photos) {

    }
}
