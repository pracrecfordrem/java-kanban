package com.yandex.app.model;

import com.yandex.app.service.InMemoryTaskManager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static com.yandex.app.model.Status.*;

public class Epic extends Task {
    private final ArrayList<Integer> subtaskIds;

    private LocalDateTime endTime;

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Epic(String name, String description, int id) {
        super(name, description, Status.NEW, id);
        subtaskIds = new ArrayList<>();
    }

    public ArrayList<Integer> getSubTaskIds() {
        return subtaskIds;
    }

    public void addSubtasks(int subtaskid) {
        this.subtaskIds.add(subtaskid);
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Optional<LocalDateTime> getEpicEndTime() {
        return Optional.ofNullable(endTime);
    }

    @Override
    public String toString() {
        Optional<Duration> optDuration = Optional.ofNullable(duration);
        Optional<LocalDateTime> optStartTime = Optional.ofNullable(super.startTime);
        return id + "," + TaskType.EPIC + "," + name + "," + status + ", " + optDuration.orElse(Duration.ofMinutes(0)) + "," + optStartTime.orElse(LocalDateTime.MIN).format(DATE_TIME_FORMATTER) + "," + description + ",";
    }

}
