package com.example.ricardogarcia.photocloud.repository.datasource;

import java.util.List;

/**
 * Created by Ricardo Garcia on 4/8/2018.
 */

public interface DataSource<T> {

    List<T> getAll();

    void addItem(T item);

    void deleteItem(T item);

}
