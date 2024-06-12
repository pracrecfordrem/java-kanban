package com.yandex.app.service;

import com.yandex.app.model.Epic;
import com.yandex.app.model.Status;
import com.yandex.app.model.SubTask;
import com.yandex.app.model.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class InMemoryTaskManager implements TaskManager {

    HashMap<Integer, Task> tasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();
    HashMap<Integer, SubTask> subtasks = new HashMap<>();
    TreeSet<Task> prioritizedTasks = new TreeSet<>();

    private int countTasks;

    public void updateEpicDuration(SubTask subTask) {
        Epic viewedEpic = epics.get(subTask.getEpicId());
        LocalDateTime start;
        LocalDateTime end;
        if (viewedEpic.getStartTime().isPresent()) {
            start = viewedEpic.getStartTime().get();
        } else {
            start = LocalDateTime.of(4999, 12, 31, 23, 59, 59);
        }
        if (viewedEpic.getEpicEndTime().isPresent()) {
            end = viewedEpic.getEpicEndTime().get();
        } else {
            end = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        }
        if (subTask.getStartTime().isPresent()) {
            if (start.isAfter(subTask.getStartTime().get())) {
                start = subTask.getStartTime().get();
            }
            if (end.isBefore(subTask.getEndTime())) {
                end = subTask.getEndTime();
            }
        }
        viewedEpic.setStartTime(start);
        viewedEpic.setEndTime(end);
    }

    @Override
    public int getCountTasks() {
        return countTasks;
    }

    @Override
    public int createTask(Task task) {
        if (task.getStartTime().isPresent()) {
            if (checkTime(task)) {
                prioritizedTasks.add(task);
            } else {
                System.out.println("Добавляемая задача " + task.getName() + " имеет пересечение(я) времени выполнения");
                return -1;
            }
        }
        tasks.put(++countTasks, task);
        return 1;
    }

    @Override
    public int updateTask(int taskId, Task updatedtask) {
        if (!tasks.containsKey(taskId)) {
            System.out.println("Изменяемая задача не найдена. Вопспользуйтесь методом добавления задачи");
            return 0;
        } else {
            if (updatedtask.getStartTime().isPresent()) {
                if (!checkTime(updatedtask)) {
                    System.out.println("Добавляемая задача " + updatedtask.getName() + " имеет пересечение(я) времени выполнения");
                    return -1;
                }
            }
            tasks.put(taskId, updatedtask);
            return 1;
        }
    }

    @Override
    public void createEpic(Epic epic) {
        epics.put(++countTasks, epic);
    }

    @Override
    public int createSubtask(SubTask subtask) {
        if (subtask.getStartTime().isPresent()) {
            if (checkTime(subtask)) {
                prioritizedTasks.add(subtask);
            } else {
                System.out.println("Добавляемая задача имеет пересечение(я) времени выполнения");
                return -1;
            }
        }
        subtasks.put(++countTasks, subtask);
        epics.get(subtask.getEpicId()).addSubtasks(subtask.getId());
        calculateEpicStatus(subtask.getEpicId());
        updateEpicDuration(subtask);
        return 1;
    }

    @Override
    public int updateSubtask(int subTaskId, SubTask subtask) {
        if (subtasks.containsKey(subTaskId)) {
            if (subtask.getStartTime().isPresent()) {
                if (!checkTime(subtask)) {
                    System.out.println("Добавляемая задача имеет пересечение(я) времени выполнения");
                    return -1;
                }
            }
            subtasks.put(subTaskId, subtask);
            subtask.setId(subtask.getId() - 1);
            calculateEpicStatus(subtasks.get(subTaskId).getEpicId());
            updateEpicDuration(subtask);
        } else {
            System.out.println("Изменяемая подзадача не найдена");
            return 0;
        }
        return 1;
    }

    @Override
    public void deleteTask(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        } else {
            System.out.println("Удаляемая задача не найдена.");
        }
    }

    @Override
    public void deleteSubTask(int id) {
        if (subtasks.containsKey(id)) {
            SubTask delSubTask = subtasks.get(id);
            subtasks.remove(id);
            int idx = epics.get(delSubTask.getEpicId()).getSubTaskIds().indexOf(id);
            epics.get(delSubTask.getEpicId()).getSubTaskIds().remove(idx);
            calculateEpicStatus(delSubTask.getEpicId());
            updateEpicDuration(delSubTask);
        } else {
            System.out.println("Удаляемая подзадача не найдена.");
        }
    }

    @Override
    public void deleteEpic(int id) {
        if (epics.containsKey(id)) {
            for (int key : subtasks.keySet()) {
                if (subtasks.get(key).getEpicId() == id) {
                    subtasks.remove(key);
                }
            }
            epics.remove(id);
        }
    }

    @Override
    public ArrayList<Task> getOnlyTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<SubTask> getOnlySubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public ArrayList<Epic> getOnlyEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void deleteAllSubtasks() {
        subtasks.clear();
        epics.values().forEach(epic -> {
            calculateEpicStatus(epic.getId());
            epic.getSubTaskIds().clear();
            epic.setStartTime(null);
            epic.setEndTime(null);
        });
    }

    @Override
    public Task getTaskById(int id) {
        if (tasks.containsKey(id)) {
            Task viewedTask = tasks.get(id);
            Managers.getDefaultHistory().add(viewedTask);
            return viewedTask;
        } else {
            System.out.println("Искомая задача отсутствует.");
            return null;
        }
    }

    @Override
    public SubTask getSubTaskById(int id) {
        if (subtasks.containsKey(id)) {
            if (Managers.getDefaultHistory().getHistory().size() < 10) {
                Managers.getDefaultHistory().add(subtasks.get(id));
            } else {
                Managers.getDefaultHistory().getHistory().removeFirst();
                Managers.getDefaultHistory().add(subtasks.get(id));
            }
            return subtasks.get(id);
        } else {
            System.out.println("Искомая подзадача отсутствует.");
            return null;
        }
    }

    @Override
    public Epic getEpicById(int id) {
        if (epics.containsKey(id)) {
            Epic viewedEpic = epics.get(id);
            if (Managers.getDefaultHistory().getHistory().size() < 10) {
                Managers.getDefaultHistory().add(viewedEpic);
            } else {
                Managers.getDefaultHistory().getHistory().removeFirst();
                Managers.getDefaultHistory().add(viewedEpic);
            }
            return viewedEpic;
        } else {
            System.out.println("Искомый эпик отсутствует.");
            return null;
        }
    }

    @Override
    public void calculateEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        int newStatus = 0;
        int inProgressStatus = 0;
        int doneStatus = 0;
        if (epic.getSubTaskIds().isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }
        for (Integer subtaskId : epic.getSubTaskIds()) {
            SubTask subtask = subtasks.get(subtaskId);
            if (subtask.getStatus() == Status.NEW) {
                newStatus++;
            } else if (subtask.getStatus() == Status.IN_PROGRESS) {
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

    @Override
    public void showAllTasks() {
        System.out.println("Список задач (id, task)");
        for (int key : tasks.keySet()) {
            System.out.println(key + " " + tasks.get(key).toString());
        }
        System.out.println("Список эпиков (id, epic)");
        for (int epic : epics.keySet()) {
            System.out.println(epic + " " + epics.get(epic).toString());
        }
        System.out.println("Список подзадач (id, subtask)");
        for (int subtask : subtasks.keySet()) {
            System.out.println(subtask + " " + subtasks.get(subtask).toString());
        }
    }

    public void setCountTasks(int countTasks) {
        this.countTasks = countTasks;
    }

    public TreeSet<Task> getPrioritizedTasks() {
        return prioritizedTasks;
    }

    public boolean checkTime(Task task) {
        if (task.getStartTime().isPresent()) {
            for (Task checkedTask : prioritizedTasks) {
                if ((((task.getStartTime().get().isBefore(checkedTask.getEndTime()) &&
                        task.getStartTime().get().isAfter(checkedTask.getStartTime().get())) ||
                        (task.getEndTime().isBefore(checkedTask.getEndTime()) &&
                        task.getEndTime().isAfter(checkedTask.getStartTime().get()))) ||
                        ((task.getEndTime().isAfter(checkedTask.getStartTime().get())) && ((task.getStartTime().get().isBefore(checkedTask.getStartTime().get()) || task.getStartTime().get().isBefore(checkedTask.getEndTime()))))
                        )
                        &&
                        task.getId() != checkedTask.getId()) {
                    return false;
                }
            }
        }
        return true;
    }
}
