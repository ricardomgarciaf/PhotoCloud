package com.example.ricardogarcia.photocloud.repository.datasource;

import com.example.ricardogarcia.photocloud.repository.dao.PhotoDao;
import com.example.ricardogarcia.photocloud.repository.entity.Photo;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Ricardo Garcia on 4/8/2018.
 */

public class PhotoDataSource implements DataSource<Photo> {

    private PhotoDao photoDao;

    @Inject
    public PhotoDataSource(PhotoDao photoDao) {
        this.photoDao = photoDao;
    }

    @Override
    public List<Photo> getAll() {
        return photoDao.getAll();
    }

    @Override
    public void addItem(Photo item) {
        photoDao.insert(item);
    }

    @Override
    public void deleteItem(Photo item) {
        photoDao.delete(item);
    }

    public List<Photo> getPhotosByAlbum(String album){
        return photoDao.getPhotosByAlbum(album);
    }

    public int getNumberOfPhotosByAlbum(String album){
        return photoDao.getPhotosByAlbum(album).size();
    }

    public void deletePhotoById(String photoId){
        photoDao.deletePhotoById(photoId);
    }
}
