package com.yandex.app.model;

import java.time.LocalDateTime;


public class SubTask extends Task {
    private final int epicId;

    public SubTask(String name, String description, Status status, int duration, LocalDateTime startTime, int epicId, int id) {
        super(name, description, status, duration, startTime, id);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return id + "," + TaskType.SUBTASK + "," + name + "," + status + "," + duration.toMinutes() + "," + startTime.format(DATE_TIME_FORMATTER) + "," + description + "," + epicId;
    }

}
