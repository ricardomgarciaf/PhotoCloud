package com.example.ricardogarcia.photocloud.activities.home.list;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ricardogarcia.photocloud.R;
import com.example.ricardogarcia.photocloud.application.PhotoCloudApplication;

import java.io.File;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.subjects.PublishSubject;

public class AlbumHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.album_image)
    ImageView albumImage;
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.album_photo_count)
    TextView albumPhotoCount;
    @BindView(R.id.album_title)
    TextView albumTitle;
    @BindView(R.id.card_view)
    CardView cardView;

    public AlbumHolder(View itemView, PublishSubject<Integer> click, PublishSubject<Integer> longClick) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(v->click.onNext(getAdapterPosition()));
        itemView.setOnLongClickListener(v->{
            longClick.onNext(getAdapterPosition());
            return true;
        });
    }

    void bind(HashMap<String, String> album, boolean selected) {
        if (selected) {
            checkbox.setVisibility(View.VISIBLE);
            checkbox.setChecked(true);
        } else {
            checkbox.setVisibility(View.INVISIBLE);
            checkbox.setChecked(false);
        }
        if (Integer.parseInt(album.get(Function.KEY_COUNT)) > 0) {
            Glide.with(PhotoCloudApplication.getAppComponent().provideContext())
                    .load(new File(album.get(Function.KEY_PATH)))
                    .into(albumImage);
        }
        albumTitle.setText(album.get(Function.KEY_ALBUM));
        albumPhotoCount.setText(new StringBuilder().append(album.get(Function.KEY_COUNT)).append(" ").append(PhotoCloudApplication.getAppComponent().provideContext().getString(R.string.photos)).toString());
    }

    void select(){
        checkbox.setVisibility(View.VISIBLE);
        checkbox.setChecked(!checkbox.isChecked());
    }


}
