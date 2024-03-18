package com.yandex.app.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Epic extends Task{
    private final ArrayList<Integer> subtaskIds;
    public Epic(String name, String description) {
        super(name, description, Status.NEW);
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

    @Override
    public String toString() {
        if (subtaskIds.isEmpty()) {
            return "com.yandex.app.model.Epic{" +
                    "name='" + name + '\'' +
                    ", description='" + description + '\'' +
                    ", id=" + id +
                    ", status=" + status +
                    "}";
        }
        return "com.yandex.app.model.Epic{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                ", subtasks=" + subtaskIds +
                '}';
    }
}
