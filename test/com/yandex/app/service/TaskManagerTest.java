package com.yandex.app.service;

import com.yandex.app.model.Epic;
import com.yandex.app.model.Status;
import com.yandex.app.model.SubTask;
import com.yandex.app.model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TaskManagerTest {
    TaskManager taskManager;
    @BeforeEach
    public void createTaskManager() {
        taskManager = Managers.getDefault();
    }
    @Test
    public void tasksShouldBeEqualIfIdEqual() {
        taskManager.createTask(new Task("Встать с постели","Просто встать с постели", Status.NEW,taskManager.getCountTasks()));
        Assertions.assertEquals(taskManager.getOnlyTasks().getFirst().getId() == taskManager.getOnlyTasks().getFirst().getId(), taskManager.getOnlyTasks().getFirst().equals(taskManager.getOnlyTasks().getFirst()));
    }
    @Test
    public void epicsShouldBeEqualIfIdEqual() {
        taskManager.createEpic(new Epic("Собраться на работу","Долго и мучительно", taskManager.getCountTasks()));
        Assertions.assertEquals(taskManager.getOnlyEpics().getFirst().getId() == taskManager.getOnlyEpics().getFirst().getId(), taskManager.getOnlyEpics().getFirst().equals(taskManager.getOnlyEpics().getFirst()));
    }
    @Test
    public void subtasksShouldBeEqualIfIdEqual() {
        taskManager.createEpic(new Epic("Собраться на работу","Долго и мучительно", taskManager.getCountTasks()));
        taskManager.createSubtask(new SubTask("Встать с постели","Просто встать с постели",Status.NEW,taskManager.getOnlyEpics().getFirst().getId(), taskManager.getCountTasks()));
        Assertions.assertEquals(taskManager.getOnlySubtasks().getFirst().getId() == taskManager.getOnlySubtasks().getFirst().getId(), taskManager.getOnlySubtasks().getFirst().equals(taskManager.getOnlySubtasks().getFirst()));
    }
    @Test
    public void isUtilityClassWorking() {
        Assertions.assertTrue(Managers.getDefault() instanceof TaskManager);
        Assertions.assertTrue(Managers.getDefaultHistory() instanceof HistoryManager);
    }
    @Test
    public void canInMemoryTaskManagerAddTaskAndFindItByID() {
        String desc = "Просто встать с постели";
        String name = "Встать с постели";
        taskManager.createTask(new Task(name,desc,Status.NEW, taskManager.getCountTasks()));
        int newId = taskManager.getCountTasks();
        Assertions.assertTrue(taskManager.getTaskById(newId).getDescription().equals(desc) && taskManager.getTaskById(newId).getName().equals(name));
    }
    @Test
    public void canInMemoryTaskManagerAddEpicAndFindItByID() {
        String desc = "Долго и мучительно";
        String name = "Собраться на работу";
        taskManager.createEpic(new Epic(name,desc, taskManager.getCountTasks()));
        int newId = taskManager.getCountTasks();
        Assertions.assertTrue(taskManager.getEpicById(newId).getDescription().equals(desc) && taskManager.getEpicById(newId).getName().equals(name));
    }
    @Test
    public void canInMemoryTaskManagerAddSubtaskAndFindItByID() {
        String desc = "Долго и мучительно";
        String name = "Собраться на работу";
        taskManager.createEpic(new Epic(name,desc, taskManager.getCountTasks()));
        int epicId = taskManager.getOnlyEpics().getLast().getId();
        String subTaskName = "Имя сабтаска";
        String subTaskDesc = "Описание сабтаска";
        taskManager.createSubtask(new SubTask(subTaskName, subTaskDesc, Status.NEW, epicId, taskManager.getCountTasks()));
        int newId = taskManager.getCountTasks();
        Assertions.assertTrue(taskManager.getSubTaskById(newId).getDescription().equals(subTaskDesc) && taskManager.getSubTaskById(newId).getName().equals(subTaskName));
    }
}
