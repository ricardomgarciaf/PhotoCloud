package com.example.ricardogarcia.photocloud.utils;

import android.os.Environment;

import com.example.ricardogarcia.photocloud.application.PhotoCloudApplication;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class FileUtils {

    public static File createPhotoFile() throws IOException {
        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        File storageDir = PhotoCloudApplication.getAppComponent().provideContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(
                timeStamp,
                ".jpg",
                storageDir);
    }
}
