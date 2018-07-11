package com.example.ricardogarcia.photocloud.activities.album.list;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.ricardogarcia.photocloud.R;
import com.example.ricardogarcia.photocloud.activities.home.list.Function;
import com.example.ricardogarcia.photocloud.application.PhotoCloudApplication;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.subjects.PublishSubject;

public class PhotoHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.photo_image)
    ImageView photoImage;
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.card_view)
    CardView cardView;

    public PhotoHolder(View itemView, PublishSubject<Integer> click, PublishSubject<Integer> longClick) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        itemView.setOnClickListener(view -> click.onNext(getAdapterPosition()));
        itemView.setOnLongClickListener(view -> {
            longClick.onNext(getAdapterPosition());
            return true;
        });
    }

    void bind(HashMap<String,String> photo, boolean selected){
        if(selected){
            checkbox.setVisibility(View.VISIBLE);
            checkbox.setChecked(true);
        }else{
            checkbox.setVisibility(View.INVISIBLE);
            checkbox.setChecked(false);
        }
        Glide.with(PhotoCloudApplication.getAppComponent().provideContext())
                .load(photo.get(Function.KEY_PATH))
                .into(photoImage);
    }

    void select(){
        checkbox.setVisibility(View.VISIBLE);
        checkbox.setChecked(!checkbox.isChecked());
    }
}
