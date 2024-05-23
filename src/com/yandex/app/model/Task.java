package com.yandex.app.model;

import java.util.Objects;

import static com.yandex.app.model.Status.*;

public class Task {

    protected String name;
    protected String description;
    protected int id;
    protected Status status;

    public Task(String name, String description, Status status, int id) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.id = id + 1;
    }

    public Status getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
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
        return id + "," + TaskType.TASK + "," + name + "," + status + "," + description + ",";
    }

}
