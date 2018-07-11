package com.example.ricardogarcia.photocloud.activities.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.ricardogarcia.photocloud.R;
import com.example.ricardogarcia.photocloud.activities.album.AlbumActivity;
import com.example.ricardogarcia.photocloud.activities.home.core.HomeModel;
import com.example.ricardogarcia.photocloud.activities.home.core.HomePresenter;
import com.example.ricardogarcia.photocloud.activities.home.core.HomeView;
import com.example.ricardogarcia.photocloud.activities.home.dagger.DaggerHomeComponent;
import com.example.ricardogarcia.photocloud.activities.home.dagger.HomeModule;
import com.example.ricardogarcia.photocloud.activities.home.list.AlbumAdapter;
import com.example.ricardogarcia.photocloud.activities.home.list.ItemOffsetDecoration;
import com.example.ricardogarcia.photocloud.application.PhotoCloudApplication;
import com.example.ricardogarcia.photocloud.utils.ConstantUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

public class HomeActivity extends AppCompatActivity implements HomeView {

    @BindView(R.id.recyclerViewGallery)
    RecyclerView recyclerViewGallery;

    @Inject
    HomeModel model;

    private HomePresenter presenter;

    private MaterialDialog progressDialog;

    private MaterialDialog inputDialog;

    private AlbumAdapter adapter;

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
            presenter.deleteSelectedItems(adapter.getSelectedItems());
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
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        DaggerHomeComponent.builder().appComponent(PhotoCloudApplication.getAppComponent()).homeModule(new HomeModule(this)).build().inject(this);

        progressDialog = new MaterialDialog.Builder(this)
                .title(R.string.loading)
                .progress(true, 0).build();

        recyclerViewGallery.setLayoutManager(new GridLayoutManager(this, 2));
        ItemOffsetDecoration itemOffsetDecoration = new ItemOffsetDecoration(this, R.dimen.offset);
        recyclerViewGallery.addItemDecoration(itemOffsetDecoration);

        adapter = new AlbumAdapter();
        recyclerViewGallery.setAdapter(adapter);

        presenter = new HomePresenter(this, model);
        presenter.onCreate();
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
    public void showExistingAlbumName() {
        Toast.makeText(this, getString(R.string.existing_album_name), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAlbumNameError() {
        Toast.makeText(this, getString(R.string.empty_album), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAlbumCreated() {
        inputDialog.dismiss();
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

    private void createAlbum() {
        inputDialog = new MaterialDialog.Builder(this)
                .title(R.string.create_album)
                .content(R.string.enter_album_name)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .autoDismiss(false)
                .input(getString(R.string.album_hint), "", (dialog, input) -> presenter.createAlbum(input.toString())).show();
    }

    public void loadAlbums(List<HashMap<String, String>> albumMaps) {
        adapter.swapData(albumMaps);
    }

    public Observable<Integer> albumItemClicks() {
        return adapter.observeAlbumItemClicks();
    }

    public Observable<Integer> albumLongItemClicks() {
        return adapter.observeAlbumLongItemClicks();
    }

    public void selectAlbumLongItem(int position) {
        actionMode = startActionMode(actionModeCallback);
        adapter.addSelectedItem(position);
        adapter.selectItem(position);
        actionMode.setTitle(String.valueOf(adapter.getSelectedItemsSize()));
    }

    public void selectAlbumItem(int position) {
        if (adapter.isItemInSelectedItem(position)) {
            adapter.removeSelectedItem(position);
        } else {
            adapter.addSelectedItem(position);
        }
        adapter.selectItem(position);
        actionMode.setTitle(String.valueOf(adapter.getSelectedItemsSize()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void goToAlbumDescription(String albumName) {
        Intent i = new Intent(this, AlbumActivity.class);
        i.putExtra(ConstantUtils.ALBUM_NAME, albumName);
        startActivity(i);
        finish();
    }
}
