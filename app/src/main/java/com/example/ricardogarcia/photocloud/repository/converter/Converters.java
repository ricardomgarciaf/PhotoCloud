package com.example.ricardogarcia.photocloud.repository.converter;

import android.arch.persistence.room.TypeConverter;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class Converters {

    private static final DateTimeFormatter formatter=DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    @TypeConverter
    public static OffsetDateTime stringToOffsetDateTime(String value){
        return OffsetDateTime.parse(value,formatter);
    }

    @TypeConverter
    public static String fromOffsetDateTime(OffsetDateTime date){
        return date.format(formatter);
    }
}
