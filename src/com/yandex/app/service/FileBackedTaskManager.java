package com.yandex.app.service;

import com.yandex.app.model.Epic;
import com.yandex.app.model.SubTask;
import com.yandex.app.model.Task;

public class FileBackedTaskManager extends InMemoryTaskManager{
    void save(){

    }
    @Override
    public void createTask(Task task) {
        super.createTask(task);
        save();
    }
    @Override
    public void updateTask(int taskId, Task updatedtask) {
        super.updateTask(taskId,updatedtask);
        save();
    }
    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        save();
    }

    @Override
    public void createSubtask(SubTask subtask) {
        super.createSubtask(subtask);
        save();
    }

    @Override
    public void updateSubtask(int subTaskId, SubTask subtask) {
        super.updateSubtask(subTaskId, subtask);
        save();
    }

    @Override
    public void deleteTask(int id) {
        super.deleteTask(id);
        save();
    }

    @Override
    public void deleteSubTask(int id) {
        super.deleteSubTask(id);
        save();
    }

    @Override
    public void deleteEpic(int id) {
        super.deleteEpic(id);
        save();
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public void deleteAllSubtasks() {
        super.deleteAllSubtasks();
        save();
    }
}
