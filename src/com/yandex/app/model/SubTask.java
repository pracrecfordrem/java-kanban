package com.yandex.app.model;

public class SubTask extends Task {
    private final int epicId;

    public SubTask(String name, String description, Status status, int epicId) {
        super(name, description, status);
        this.epicId = epicId;
    }
    public SubTask(String name, String description, Status status, int epicId, int id) {
        super(name, description, status, id);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "com.yandex.app.model.SubTask{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                ", epicId=" + epicId +
                '}';
    }
}
