package com.yandex.app.service;

import com.yandex.app.model.Epic;
import com.yandex.app.model.SubTask;
import com.yandex.app.model.Task;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;

public class FileBackedTaskManager extends InMemoryTaskManager {

    Logger logger = Logger.getLogger(FileBackedTaskManager.class.getName());
    String absoluteFilePath;

    public FileBackedTaskManager(String filePath) {
        this.absoluteFilePath = filePath;
    }

    void save() throws ManagerSaveException {
        try (FileWriter fileWriter = new FileWriter(absoluteFilePath)) {
            fileWriter.write("id,type,name,status,description,epic\n");
            for (int taskid : tasks.keySet()) {
                fileWriter.write(tasks.get(taskid).toString() + '\n');
            }
            for (int epicId : epics.keySet()) {
                fileWriter.write(epics.get(epicId).toString() + '\n');
            }
            for (int subTaskId : subtasks.keySet()) {
                fileWriter.write(subtasks.get(subTaskId).toString() + '\n');
            }
        } catch (IOException o) {
            logger.log(Level.INFO,"Ошибка при сохранении изменений в файл: " + absoluteFilePath,o);
            throw new ManagerSaveException("Ошибка при работе с файлом: " + absoluteFilePath, o);
        }
    }

    @Override
    public void createTask(Task task) {
        super.createTask(task);
        save();
    }

    @Override
    public void updateTask(int taskId, Task updatedtask) {
        super.updateTask(taskId, updatedtask);
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
