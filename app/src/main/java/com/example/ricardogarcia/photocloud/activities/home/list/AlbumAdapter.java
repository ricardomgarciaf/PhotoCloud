package com.example.ricardogarcia.photocloud.activities.home.list;

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

/**
 * Created by Ricardo Garcia on 4/7/2018.
 */

public class AlbumAdapter extends RecyclerView.Adapter<AlbumHolder> {

    private PublishSubject<Integer> albumItemClicks= PublishSubject.create();
    private PublishSubject<Integer> albumLongItemClicks= PublishSubject.create();
    private List<AlbumHolder> holders;
    private List<HashMap<String,String>> data;
    private ArrayList<Integer> selectedItems;
    public AlbumAdapter() {
        data=new ArrayList<>();
        selectedItems= new ArrayList<>();
        holders= new ArrayList<>();
    }

    @NonNull
    @Override
    public AlbumHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(PhotoCloudApplication.getAppComponent().provideContext());
        View row=inflater.inflate(R.layout.album_item,parent,false);
        return new AlbumHolder(row,albumItemClicks,albumLongItemClicks);
    }

    @Override
    public void onBindViewHolder(AlbumHolder holder, int position) {
        holder.bind(data.get(position),selectedItems.contains(position));
        holders.add(position,holder);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public Observable<Integer> observeAlbumItemClicks(){
        return albumItemClicks;
    }

    public Observable<Integer> observeAlbumLongItemClicks(){
        return albumLongItemClicks;
    }

    public void swapData(List<HashMap<String,String>> albumsMap){
        data.clear();
        data.addAll(albumsMap);
        notifyDataSetChanged();
    }

    public void selectItem(int position){
        holders.get(position).select();
    }

    public void clearSelectedItems() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public void addSelectedItem(int position) {
        selectedItems.add(position);
    }

    public void removeSelectedItem(int position) {
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
