package com.yandex.app.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;


public class Task implements Comparable<Task>{

    protected String name;
    protected String description;
    protected int id;
    protected Status status;
    protected Duration duration;

    protected LocalDateTime startTime;

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm dd.MM.yy");

    public Task(String name, String description, Status status, int duration, LocalDateTime startTime, int id) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.duration = Duration.ofMinutes(duration);
        this.startTime = startTime;
        this.id = id + 1;
    }

    public Task(String name, String description, Status status,  int id) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.id = id + 1;
    }
    public Optional<LocalDateTime> getStartTime() {
        return Optional.ofNullable(startTime);
    }

    public Status getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getEndTime() {
        return startTime.plus(duration);
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task task)) return false;
        return id == task.id && Objects.equals(name, task.name) && Objects.equals(description, task.description) && status == task.status;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash + id + Objects.hash(name, description, id, status);
        hash = hash * 31;
        return hash;
    }

    @Override
    public String toString() {
        return id + "," + TaskType.TASK + "," + name + "," + status + "," + duration.toMinutes() + "," + startTime.format(DATE_TIME_FORMATTER) + "," + description + ",";
    }

    @Override
    public int compareTo(Task task) {
        if (startTime.isBefore(task.startTime)) {
            return -1;
        } else if (startTime.isAfter(task.startTime)) {
            return 1;
        } else {
            return 0;
        }
    }
}
