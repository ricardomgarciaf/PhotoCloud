package com.example.ricardogarcia.photocloud.repository.migration;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.migration.Migration;
import android.support.annotation.NonNull;

public class Migrations {
    public static final Migration MIGRATION_1_2= new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE user ADD COLUMN createdDate TEXT");
            database.execSQL("ALTER TABLE album ADD COLUMN createdDate TEXT");
            database.execSQL("ALTER TABLE photo ADD COLUMN createdDate TEXT");
        }
    };
}
