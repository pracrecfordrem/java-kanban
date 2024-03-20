package com.yandex.app.service;

import com.yandex.app.model.*;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {

    HashMap<Integer,Task> tasks = new HashMap<>();
    HashMap<Integer,Epic> epics = new HashMap<>();
    HashMap<Integer,SubTask> subtasks = new HashMap<>();

    private static int countTasks;

    public static int getCountTasks() {
        return countTasks;
    }

    public void createTask (Task task) {
        tasks.put(++countTasks,task);
    }

    public void updateTask (int taskId, Task updatedtask) {
        if (!tasks.containsKey(taskId)){
            System.out.println("Изменяемая задача не найдена. Вопспользуйтесь методом добавления задачи");
        } else {
            tasks.put(taskId,updatedtask);
        }
    }

    public void createEpic (Epic epic) {
        epics.put(++countTasks, epic);
    }

    public void createSubtask (SubTask subtask) {
        subtasks.put(++countTasks,subtask);
        epics.get(subtask.getEpicId()).addSubtasks(subtask.getId());
        calculateEpicStatus(subtask.getEpicId());
    }

    public void updateSubtask (int subTaskId, SubTask subtask) {
        if (subtasks.containsKey(subTaskId)) {
            subtasks.put(subTaskId, subtask);
            epics.get(subtask.getEpicId()).getSubTaskIds().add(subTaskId);
            calculateEpicStatus(subtasks.get(subTaskId).getEpicId());
        } else {
            System.out.println("Изменяемая подзадача не найдена");
        }
    }
    public void deleteTask (int id) {
        if (tasks.containsKey(id)){
            tasks.remove(id);
        } else {
            System.out.println("Удаляемая задача не найдена.");
        }
    }

    public void deleteSubTask (int id) {
        if (subtasks.containsKey(id)) {
            SubTask delSubTask = subtasks.get(id);
            subtasks.remove(id);
            int idx = epics.get(delSubTask.getEpicId()).getSubTaskIds().indexOf(id);
            epics.get(delSubTask.getEpicId()).getSubTaskIds().remove(idx);
            calculateEpicStatus(delSubTask.getEpicId());
        } else {
            System.out.println("Удаляемая подзадача не найдена.");
        }
    }

    public void deleteEpic (int id){
        if (epics.containsKey(id)) {
            for (int key: subtasks.keySet()) {
                if (subtasks.get(key).getEpicId() == id) {
                    subtasks.remove(key);
                }
            }
            epics.remove(id);
        }
    }

    public ArrayList<Task> getOnlyTasks() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<SubTask> getOnlySubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public ArrayList<Epic> getOnlyEpics() {
        return new ArrayList<>(epics.values());
    }

    public void deleteAllTasks(){
        tasks.clear();
    }

    public void deleteAllEpics() {
        epics.clear();
    }

    public void deleteAllSubtasks() {
        subtasks.clear();
        for (Epic epic: epics.values()) {
            calculateEpicStatus(epic.getId());
            epic.getSubTaskIds().clear();
        }
    }

    public void getTaskById (int id) {
        if (tasks.containsKey(id)) {
            System.out.println(tasks.get(id));
        } else {
            System.out.println("Искомая задача отсутствует.");
        }
    }

    public void getSubTaskById (int id) {
        if (subtasks.containsKey(id)) {
            System.out.println(subtasks.get(id));
        } else {
            System.out.println("Искомая подзадача отсутствует.");
        }
    }

    public void getEpicById (int id) {
        if (epics.containsKey(id)) {
            System.out.println(epics.get(id));
        } else {
            System.out.println("Искомый эпик отсутствует.");
        }
    }

    public void calculateEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        int newStatus = 0;
        int inProgressStatus = 0;
        int doneStatus = 0;
        if (epic.getSubTaskIds().isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }
        for (Integer subtaskId: epic.getSubTaskIds()) {
            SubTask subtask = subtasks.get(subtaskId);
            if (subtask.getStatus() == Status.NEW) {
                newStatus++;
            } else if (subtask.getStatus() == Status.IN_PROGRESS){
                inProgressStatus++;
                epics.get(epicId).setStatus(Status.IN_PROGRESS);
                return;
            } else if (subtask.getStatus() == Status.DONE) {
                doneStatus++;
            }
        }
        if (doneStatus == epic.getSubTaskIds().size()) {
            epics.get(epicId).setStatus(Status.DONE);
        } else {
            epics.get(epicId).setStatus(Status.NEW);
        }
    }

    public void showAllTasks() {
        System.out.println("Список задач (id, task)");
        for (int key:tasks.keySet()) {
            System.out.println(key + " " + tasks.get(key).toString());
        }
        System.out.println("Список эпиков (id, epic)");
        for (int epic:epics.keySet()) {
            System.out.println(epic + " " + epics.get(epic).toString());
        }
        System.out.println("Список подзадач (id, subtask)");
        for (int subtask:subtasks.keySet()) {
            System.out.println(subtask + " " + subtasks.get(subtask).toString());
        }
    }
}
