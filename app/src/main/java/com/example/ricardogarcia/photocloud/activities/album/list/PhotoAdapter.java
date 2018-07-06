package com.example.ricardogarcia.photocloud.activities.album.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ricardogarcia.photocloud.R;
import com.example.ricardogarcia.photocloud.application.PhotoCloudApplication;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {

    @NonNull
    @Override
    public PhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(PhotoCloudApplication.getAppComponent().provideContext());
        View row = inflater.inflate(R.layout.photo_item, parent, false);
        return new PhotoHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
