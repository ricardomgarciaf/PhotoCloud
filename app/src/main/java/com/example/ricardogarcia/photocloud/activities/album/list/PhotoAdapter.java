package com.example.ricardogarcia.photocloud.activities.album.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ricardogarcia.photocloud.R;
import com.example.ricardogarcia.photocloud.application.PhotoCloudApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {

    private PublishSubject<Integer> photoItemClicks=PublishSubject.create();
    private PublishSubject<Integer> photoLongItemClicks=PublishSubject.create();
    private List<PhotoHolder> holders;
    private List<HashMap<String,String>> data;
    private ArrayList<Integer> selectedItems;

    public PhotoAdapter() {
        data=new ArrayList<>();
        selectedItems=new ArrayList<>();
        holders= new ArrayList<>();
    }

    @NonNull
    @Override
    public PhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(PhotoCloudApplication.getAppComponent().provideContext());
        View row = inflater.inflate(R.layout.photo_item, parent, false);
        return new PhotoHolder(row,photoItemClicks,photoLongItemClicks);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoHolder holder, int position) {
        holder.bind(data.get(position),selectedItems.contains(position));
        holders.add(position,holder);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public Observable<Integer> observePhotoItemClicks(){
        return photoItemClicks;
    }

    public Observable<Integer> observePhotoLongItemClicks(){
        return photoLongItemClicks;
    }

    public void swapData(List<HashMap<String,String>> photoMap){
        data.clear();
        data.addAll(photoMap);
        notifyDataSetChanged();
    }

    public void selectItem(int position){
        holders.get(position).select();
    }

    public void clearSelectedItems(){
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public void addSelectedItem(int position){
        selectedItems.add(position);
    }

    public void removeSelectedItem(int position){
        selectedItems.remove(position);
    }

    public boolean isItemInSelectedItem(int position){
        return selectedItems.contains(position);
    }

    public int getSelectedItemsSize(){
        return selectedItems.size();
    }

    public ArrayList<Integer> getSelectedItems() {
        return selectedItems;
    }
}
