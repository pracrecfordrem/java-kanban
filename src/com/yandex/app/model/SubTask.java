package com.yandex.app.model;

public class SubTask extends Task {
    private final int epicId;

    public SubTask(String name, String description, Status status, int epicId, int id) {
        super(name, description, status, id);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return id + "," + TaskType.SUBTASK + "," + name + "," + status + "," + description + "," + epicId;
    }
}
