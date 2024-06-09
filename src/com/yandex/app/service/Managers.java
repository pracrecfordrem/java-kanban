package com.yandex.app.service;

import com.yandex.app.model.*;

import java.io.*;
import java.time.LocalDateTime;

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

    public static Task fromString(String value) throws IncorrectDataException {
        String[] valuesArr = value.split(",");
        Status currStatus = null;
        currStatus = switch (valuesArr[3]) {
            case "NEW" -> NEW;
            case "IN_PROGRESS" -> IN_PROGRESS;
            case "DONE" -> DONE;
            default -> throw new IncorrectDataException("Введён некорректный статус " + currStatus);
        };
        try {
            if (valuesArr[1].equals(String.valueOf(TaskType.TASK))) {
                return new Task(valuesArr[2],valuesArr[6],currStatus,Integer.parseInt(valuesArr[4]), LocalDateTime.parse(valuesArr[5],Task.DATE_TIME_FORMATTER),Integer.parseInt(valuesArr[0]) - 1);
            } else if (valuesArr[1].equals(String.valueOf(TaskType.SUBTASK))) {
                return new SubTask(valuesArr[2],valuesArr[6],currStatus,Integer.parseInt(valuesArr[4]),LocalDateTime.parse(valuesArr[5],Task.DATE_TIME_FORMATTER),Integer.parseInt(valuesArr[7]),Integer.parseInt(valuesArr[0]) - 1);
            } else {
                return new Epic(valuesArr[2], valuesArr[4], Integer.parseInt(valuesArr[0]) - 1);
            }
        } catch (IndexOutOfBoundsException o) {
            throw new IndexOutOfBoundsException("Недостаточно данных в прочитываемой строке");
        }
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        int addedTasks = 0;
        int cntlines = 0;
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file.getAbsolutePath());
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while (br.ready()) {
                String line = br.readLine();
                if (cntlines == 0) {
                    line = br.readLine();
                    cntlines++;
                }
                String type = line.split(",")[1];
                Task task = fromString(line);
                if (type.equals(String.valueOf(TaskType.TASK))) {
                    fileBackedTaskManager.tasks.put(task.getId(),task);
                    addedTasks++;
                } else if (type.equals(String.valueOf(TaskType.EPIC))) {
                    Epic epic = (Epic) task;
                    fileBackedTaskManager.epics.put(epic.getId(), epic);
                    addedTasks++;
                } else if (type.equals(String.valueOf(TaskType.SUBTASK))) {
                    SubTask subTask = (SubTask) task;
                    fileBackedTaskManager.subtasks.put(subTask.getId(), subTask);
                    addedTasks++;
                } else {
                    throw new IncorrectDataException("Введён некорректный статус " + type);
                }
                cntlines++;
            }
        } catch (IOException o) {
            throw new ManagerSaveException("Ошибка при работе с файлом", o);
        } catch (IncorrectDataException e) {
            System.out.println(e.getMessage());
        }
        fileBackedTaskManager.setCountTasks(addedTasks);
        return fileBackedTaskManager;
    }
}
