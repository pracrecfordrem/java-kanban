package com.yandex.app.model;

import com.google.gson.TypeAdapter;
import com.google.gson.internal.bind.util.ISO8601Utils;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.Duration;

public class DurationAdapter extends TypeAdapter<Duration> {

    @Override
    public void write(final JsonWriter jsonWriter, final Duration duration) throws IOException {
        if (duration == null) {
            jsonWriter.value(0);
        } else {
            jsonWriter.value(duration.toMinutes());
        }
    }

    @Override
    public Duration read(final JsonReader jsonReader) throws IOException,NumberFormatException {
        if (jsonReader.hasNext()) {
            try {
                return Duration.ofMinutes(Long.parseLong(jsonReader.nextString()));
            } catch (NumberFormatException exception) {
                throw new NumberFormatException("Введён некорректный формат числа");
            }
        } else {
            return Duration.ofMinutes(0);
        }
    }
}