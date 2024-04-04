package com.yandex.app.service;

import com.yandex.app.model.Epic;
import com.yandex.app.model.SubTask;
import com.yandex.app.model.Task;

import java.util.ArrayList;

public interface TaskManager {

    void createTask(Task task);

    void updateTask(int taskId, Task updatedtask);

    void createEpic(Epic epic);

    void createSubtask(SubTask subtask);

    void updateSubtask(int subTaskId, SubTask subtask);

    void deleteTask(int id);

    void deleteSubTask(int id);

    void deleteEpic(int id);

    ArrayList<Task> getOnlyTasks();

    ArrayList<SubTask> getOnlySubtasks();

    ArrayList<Epic> getOnlyEpics();

    void deleteAllTasks();

    void deleteAllEpics();

    void deleteAllSubtasks();

    Task getTaskById(int id);

    SubTask getSubTaskById(int id);

    Epic getEpicById(int id);

    void calculateEpicStatus(int epicId);

    void showAllTasks();
}
