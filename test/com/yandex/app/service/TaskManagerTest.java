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
        taskManager.createTask(new Task("Встать с постели","Просто встать с постели", Status.NEW));
        Assertions.assertEquals(taskManager.getOnlyTasks().getFirst().id == taskManager.getOnlyTasks().getFirst().id, taskManager.getOnlyTasks().getFirst().equals(taskManager.getOnlyTasks().getFirst()));
    }
    @Test
    public void epicsShouldBeEqualIfIdEqual() {
        taskManager.createEpic(new Epic("Собраться на работу","Долго и мучительно"));
        Assertions.assertEquals(taskManager.getOnlyEpics().getFirst().id == taskManager.getOnlyEpics().getFirst().id, taskManager.getOnlyEpics().getFirst().equals(taskManager.getOnlyEpics().getFirst()));
    }
    @Test
    public void subtasksShouldBeEqualIfIdEqual() {
        taskManager.createEpic(new Epic("Собраться на работу","Долго и мучительно"));
        taskManager.createSubtask(new SubTask("Встать с постели","Просто встать с постели",Status.NEW,taskManager.getOnlyEpics().getFirst().getId()));
        Assertions.assertEquals(taskManager.getOnlySubtasks().getFirst().id == taskManager.getOnlySubtasks().getFirst().id, taskManager.getOnlySubtasks().getFirst().equals(taskManager.getOnlySubtasks().getFirst()));
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
        taskManager.createTask(new Task(name,desc,Status.NEW));
        int newId = InMemoryTaskManager.getCountTasks();
        Assertions.assertTrue(taskManager.getTaskById(newId).description.equals(desc) && taskManager.getTaskById(newId).name.equals(name));
    }
    @Test
    public void canInMemoryTaskManagerAddEpicAndFindItByID() {
        String desc = "Долго и мучительно";
        String name = "Собраться на работу";
        taskManager.createEpic(new Epic(name,desc));
        int newId = InMemoryTaskManager.getCountTasks();
        Assertions.assertTrue(taskManager.getEpicById(newId).description.equals(desc) && taskManager.getEpicById(newId).name.equals(name));
    }
    @Test
    public void canInMemoryTaskManagerAddSubtaskAndFindItByID() {
        int epicId = taskManager.getOnlyEpics().getLast().getId();
        String name = "Имя сабтаска";
        String desc = "Описание сабтаска";
        taskManager.createSubtask(new SubTask(name, desc, Status.NEW, epicId));
        int newId = InMemoryTaskManager.getCountTasks();
        Assertions.assertTrue(taskManager.getSubTaskById(newId).description.equals(desc) && taskManager.getSubTaskById(newId).name.equals(name));
    }
}
