package com.example.ricardogarcia.photocloud.repository.datasource;

import com.example.ricardogarcia.photocloud.repository.dao.AlbumDao;
import com.example.ricardogarcia.photocloud.repository.entity.Album;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Ricardo Garcia on 4/8/2018.
 */

public class AlbumDataSource implements DataSource<Album> {

    private AlbumDao albumDao;

    @Inject
    public AlbumDataSource(AlbumDao albumDao) {
        this.albumDao = albumDao;
    }

    @Override
    public List<Album> getAll() {
        return albumDao.getAll();
    }

    @Override
    public void addItem(Album item) {
        albumDao.insert(item);
    }

    @Override
    public void deleteItem(Album item) {
        albumDao.delete(item);
    }

    public List<Album> getAlbumsByUser(String user){
        return albumDao.getAlbumsByUser(user);
    }

    public boolean isAlbumNameRepeated(String albumName){
        return albumDao.isAlbumNameRepeated(albumName)==1;
    }
}
