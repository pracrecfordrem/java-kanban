package com.yandex.app.service;

import com.yandex.app.model.*;

import java.io.*;

import static com.yandex.app.model.Status.*;

public class Managers {
    private static InMemoryTaskManager taskManager;
    private static FileBackedTaskManager fileBackedTaskManager;
    private static InMemoryHistoryManager inMemoryHistoryManager;

    public static TaskManager getDefault() {
        if (taskManager == null) {
            return new InMemoryTaskManager();
        } else {
            return taskManager;
        }
    }

    public static InMemoryHistoryManager getDefaultHistory() {
        if (inMemoryHistoryManager == null) {
            return new InMemoryHistoryManager();
        } else {
            return inMemoryHistoryManager;
        }
    }

    public static TaskManager getDefaultFiledBacked(String filePath) {
        if (fileBackedTaskManager == null) {
            return new FileBackedTaskManager(filePath);
        } else {
            return fileBackedTaskManager;
        }
    }

    public static Task fromString(String value) {
        String[] valuesArr = value.split(",");
        Status currStatus;
        if (valuesArr[3].equals("NEW")) {
            currStatus = NEW;
        } else if (valuesArr[3].equals("IN_PROGRESS")) {
            currStatus = IN_PROGRESS;
        } else {
            currStatus = DONE;
        }
        if (valuesArr[1].equals(String.valueOf(TaskType.TASK))) {
            return new Task(valuesArr[2],valuesArr[4],currStatus,Integer.parseInt(valuesArr[0]) - 1);
        } else if (valuesArr[1].equals(String.valueOf(TaskType.SUBTASK))) {
            return new SubTask(valuesArr[2],valuesArr[4],currStatus,Integer.parseInt(valuesArr[5]),Integer.parseInt(valuesArr[0]) - 1);
        } else {
            return new Epic(valuesArr[2], valuesArr[4], Integer.parseInt(valuesArr[0]) - 1);
        }
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        int addedTasks = 0;
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file.getAbsolutePath());
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while (br.ready()) {
                String line = br.readLine();
                String type = line.split(",")[1];
                if (type.equals(String.valueOf(TaskType.TASK))) {
                    Task task = fromString(line);
                    fileBackedTaskManager.tasks.put(task.getId(),task);
                    addedTasks++;
                } else if (type.equals(String.valueOf(TaskType.EPIC))) {
                    Epic epic = (Epic) fromString(line);
                    fileBackedTaskManager.epics.put(epic.getId(), epic);
                    addedTasks++;
                } else if (type.equals(String.valueOf(TaskType.SUBTASK))) {
                    SubTask subTask = (SubTask) fromString(line);
                    fileBackedTaskManager.subtasks.put(subTask.getId(), subTask);
                    addedTasks++;
                }
            }
        } catch (IOException o) {
            throw new ManagerSaveException("Ошибка при работе с файлом", o);
        }
        fileBackedTaskManager.setCountTasks(addedTasks);
        return fileBackedTaskManager;
    }
}
