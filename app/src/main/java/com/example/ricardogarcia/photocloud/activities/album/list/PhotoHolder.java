package com.example.ricardogarcia.photocloud.activities.album.list;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.example.ricardogarcia.photocloud.R;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.photo_image)
    ImageView photoImage;
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.card_view)
    CardView cardView;

    public PhotoHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    void bind(HashMap<String,String> photo){
        
    }
}
