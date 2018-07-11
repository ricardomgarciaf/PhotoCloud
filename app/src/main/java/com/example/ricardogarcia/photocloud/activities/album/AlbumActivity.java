package com.example.ricardogarcia.photocloud.activities.album;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.ricardogarcia.photocloud.R;
import com.example.ricardogarcia.photocloud.activities.album.core.AlbumModel;
import com.example.ricardogarcia.photocloud.activities.album.core.AlbumPresenter;
import com.example.ricardogarcia.photocloud.activities.album.core.AlbumView;
import com.example.ricardogarcia.photocloud.activities.album.dagger.AlbumModule;
import com.example.ricardogarcia.photocloud.activities.album.dagger.DaggerAlbumComponent;
import com.example.ricardogarcia.photocloud.activities.album.list.PhotoAdapter;
import com.example.ricardogarcia.photocloud.activities.home.HomeActivity;
import com.example.ricardogarcia.photocloud.activities.home.list.ItemOffsetDecoration;
import com.example.ricardogarcia.photocloud.application.PhotoCloudApplication;
import com.example.ricardogarcia.photocloud.utils.ConstantUtils;
import com.example.ricardogarcia.photocloud.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindAnim;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import timber.log.Timber;

public class AlbumActivity extends AppCompatActivity implements AlbumView {

    @Inject
    AlbumModel albumModel;

    @BindView(R.id.recyclerViewGallery)
    RecyclerView recyclerViewGallery;

    AlbumPresenter albumPresenter;
    @BindView(R.id.fab_gallery)
    FloatingActionButton fabGallery;
    @BindView(R.id.fab_camera)
    FloatingActionButton fabCamera;
    @BindView(R.id.fab_add)
    FloatingActionButton fabAdd;
    @BindAnim(R.anim.fab_open)
    Animation fabOpen;
    @BindAnim(R.anim.fab_close)
    Animation fabClose;
    @BindAnim(R.anim.rotate_backward)
    Animation rotateBackward;
    @BindAnim(R.anim.rotate_forward)
    Animation rotateForward;

    private String filePath;

    private String albumName;

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private static final int REQUEST_PICK_FROM_GALLERY = 2;

    private boolean isFABOpen = false;

    private PhotoAdapter adapter;

    private boolean actionModeVisible = false;

    private ActionMode actionMode;

    public boolean isActionModeVisible() {
        return actionModeVisible;
    }

    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            MenuInflater inflater = actionMode.getMenuInflater();
            inflater.inflate(R.menu.context_home_menu, menu);
            actionModeVisible = true;
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            albumPresenter.deleteSelectedItems(adapter.getSelectedItems());
            actionMode.finish();
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode a) {
            actionMode = null;
            actionModeVisible = false;
            adapter.clearSelectedItems();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        ButterKnife.bind(this);

        DaggerAlbumComponent.builder().appComponent(PhotoCloudApplication.getAppComponent()).albumModule(new AlbumModule(this)).build().inject(this);

        Intent intent = getIntent();
        albumName = intent.getStringExtra(ConstantUtils.ALBUM_NAME);

        recyclerViewGallery.setLayoutManager(new GridLayoutManager(this, 2));
        ItemOffsetDecoration itemOffsetDecoration = new ItemOffsetDecoration(this, R.dimen.offset);
        recyclerViewGallery.addItemDecoration(itemOffsetDecoration);

        adapter = new PhotoAdapter();
        recyclerViewGallery.setAdapter(adapter);


        albumPresenter = new AlbumPresenter(this, albumModel);
        albumPresenter.onCreate(albumName);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        albumPresenter.onDestroy();
    }

    @Override
    public void onFailure() {
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(R.string.unexpected_error_title)
                .content(R.string.unexpected_error_description)
                .positiveText(R.string.ok)
                .onPositive((d, which) -> {
                    d.dismiss();
                    finish();
                }).build();
        dialog.show();
    }

    @Override
    public void onTakePictureClicked() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = FileUtils.createPhotoFile();
            } catch (IOException e) {
                e.printStackTrace();
                onFailure();
                return;
            }
            Uri photoURI = FileProvider.getUriForFile(this, getString(R.string.file_provider), photoFile);
            filePath=photoFile.getAbsolutePath();
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onImportPhotoClicked() {
        Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhotoIntent,REQUEST_PICK_FROM_GALLERY);
    }

    public void loadPhotos(List<HashMap<String, String>> photos) {
        adapter.swapData(photos);
    }

    public Observable<Integer> photoItemClicks() {
        return adapter.observePhotoItemClicks();
    }

    public Observable<Integer> photoLongItemClicks() {
        return adapter.observePhotoLongItemClicks();
    }

    public void selectItem(int position) {
        if (adapter.isItemInSelectedItem(position)) {
            adapter.removeSelectedItem(position);
        } else {
            adapter.addSelectedItem(position);
        }
        adapter.selectItem(position);
        actionMode.setTitle(String.valueOf(adapter.getSelectedItemsSize()));
    }

    public void selectLongItem(int position) {
        actionMode = startActionMode(actionModeCallback);
        adapter.addSelectedItem(position);
        adapter.selectItem(position);
        actionMode.setTitle(String.valueOf(adapter.getSelectedItemsSize()));
    }

    private void animateFAB() {
        if (isFABOpen) {
            fabAdd.startAnimation(rotateBackward);
            fabCamera.startAnimation(fabClose);
            fabGallery.startAnimation(fabClose);
            fabGallery.setClickable(false);
            fabCamera.setClickable(false);
            isFABOpen = false;
        } else {
            fabAdd.startAnimation(rotateForward);
            fabCamera.startAnimation(fabOpen);
            fabGallery.startAnimation(fabOpen);
            fabGallery.setClickable(true);
            fabCamera.setClickable(true);
            isFABOpen = true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_IMAGE_CAPTURE){
            if(resultCode==RESULT_OK){
                albumPresenter.createPhoto(filePath,albumName,"");
            }
        }else if(requestCode==REQUEST_PICK_FROM_GALLERY){
            if(resultCode==RESULT_OK){
                albumPresenter.createPhoto(data.getData().toString(),albumName,"");
            }
        }
    }

    @OnClick({R.id.fab_gallery, R.id.fab_camera, R.id.fab_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fab_gallery:
                onImportPhotoClicked();
                animateFAB();
                break;
            case R.id.fab_camera:
                onTakePictureClicked();
                animateFAB();
                break;
            case R.id.fab_add:
                animateFAB();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i= new Intent(this, HomeActivity.class);
        startActivity(i);
        finish();
    }
}
