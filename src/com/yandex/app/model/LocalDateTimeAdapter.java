package com.yandex.app.model;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm dd.MM.yy");

    @Override
    public void write(final JsonWriter jsonWriter, final LocalDateTime localDate) throws IOException {
        if (localDate == null) {
            jsonWriter.value(String.valueOf(LocalDateTime.of(1900, 1, 1,0,0,0)));
        } else {
            jsonWriter.value(localDate.format(DATE_TIME_FORMATTER));
        }
    }

    @Override
    public LocalDateTime read(final JsonReader jsonReader) throws IOException {
        if (jsonReader.hasNext()) {
            return LocalDateTime.parse(jsonReader.nextString(), DATE_TIME_FORMATTER);
        } else {
            return LocalDateTime.of(1900, 1, 1,0,0,0);
        }
    }
} 