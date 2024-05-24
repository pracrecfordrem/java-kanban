package com.yandex.app;


import com.yandex.app.service.FileBackedTaskManager;

import java.io.File;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {

          String filePath = "C:\\Users\\rbarinov\\Desktop\\temp\\Tasks.txt";

        //Пользовательский сценарий №1

        com.yandex.app.service.FileBackedTaskManager fileBackedTaskManager = (FileBackedTaskManager) com.yandex.app.service.Managers.getDefaultFiledBacked(filePath);
        com.yandex.app.service.TaskManager taskManager2 = com.yandex.app.service.Managers.getDefault();
        com.yandex.app.service.InMemoryHistoryManager historyManager = com.yandex.app.service.Managers.getDefaultHistory();


        fileBackedTaskManager.createTask(new com.yandex.app.model.Task("Встать с постели","Просто встать с постели",com.yandex.app.model.Status.NEW, 100, LocalDateTime.now(),fileBackedTaskManager.getCountTasks()));
        fileBackedTaskManager.createEpic(new com.yandex.app.model.Epic("Собраться на работу", "Долго и мучительно",fileBackedTaskManager.getCountTasks()));
        fileBackedTaskManager.createSubtask(new com.yandex.app.model.SubTask("Собраться на работу", "Долго и мучительно",com.yandex.app.model.Status.NEW,150, LocalDateTime.now().plusMinutes(100),2,fileBackedTaskManager.getCountTasks()));

        fileBackedTaskManager.showAllTasks();
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        fileBackedTaskManager.updateTask(1,new com.yandex.app.model.Task("Встать с постели","Просто встать с постели",com.yandex.app.model.Status.IN_PROGRESS,100, LocalDateTime.now(),0));
        fileBackedTaskManager.updateSubtask(3,new com.yandex.app.model.SubTask("Собраться на работу","Долго и мучительно",com.yandex.app.model.Status.IN_PROGRESS,150,LocalDateTime.now(), 2,3));

        fileBackedTaskManager.showAllTasks();
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        fileBackedTaskManager.deleteTask(3);
        fileBackedTaskManager.createSubtask(new com.yandex.app.model.SubTask("Пора уже выходить", "БЫСТРО",com.yandex.app.model.Status.NEW,123,LocalDateTime.now().minusHours(2), 2,fileBackedTaskManager.getCountTasks()));
        fileBackedTaskManager.createSubtask(new com.yandex.app.model.SubTask("Дойти до остановки", "БЫСТРО",com.yandex.app.model.Status.DONE,13,LocalDateTime.now().plusMinutes(13), 2,fileBackedTaskManager.getCountTasks()));
        fileBackedTaskManager.showAllTasks();
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println(fileBackedTaskManager.getOnlyTasks());
        System.out.println(fileBackedTaskManager.getOnlyEpics());
        System.out.println(fileBackedTaskManager.getOnlySubtasks());
        System.out.println("head: " + historyManager.getCustomLinkedList().getHead() + " tail: " + historyManager.getCustomLinkedList().getTail() + ", size: " + historyManager.getCustomLinkedList().getSize() + ", viewedTasks: " + historyManager.getCustomLinkedList().getViewedTasks());
        System.out.println(fileBackedTaskManager.getTaskById(1));
        System.out.println("head: " + historyManager.getCustomLinkedList().getHead() + " tail: " + historyManager.getCustomLinkedList().getTail() + ", size: " + historyManager.getCustomLinkedList().getSize() + ", viewedTasks: " + historyManager.getCustomLinkedList().getViewedTasks());
        System.out.println(fileBackedTaskManager.getEpicById(2));
        System.out.println("head: " + historyManager.getCustomLinkedList().getHead() + " tail: " + historyManager.getCustomLinkedList().getTail() + ", size: " + historyManager.getCustomLinkedList().getSize() + ", viewedTasks: " + historyManager.getCustomLinkedList().getViewedTasks());
        System.out.println(fileBackedTaskManager.getSubTaskById(3));
        System.out.println("head: " + historyManager.getCustomLinkedList().getHead() + " tail: " + historyManager.getCustomLinkedList().getTail() + ", size: " + historyManager.getCustomLinkedList().getSize() + ", viewedTasks: " + historyManager.getCustomLinkedList().getViewedTasks());
        //Проверка работы getEndTime
        System.out.println("Проверка работы метода getEndTime: " + fileBackedTaskManager.getTaskById(1).getEndTime());
        System.out.println("Проверка работы метода getEndTime: " + fileBackedTaskManager.getEpicById(2).getStartTime() + "    " + fileBackedTaskManager.getEpicById(2).getEpicEndTime());
        System.out.println("Проверка приоритетности задач: " + fileBackedTaskManager.getPrioritizedTasks());
        System.out.println("История просмотров: " + historyManager.getCustomLinkedList().getViewedTasks());
        fileBackedTaskManager.showAllTasks();

//        Пользовательский сценарий №2
        com.yandex.app.service.TaskManager fileBackedTaskManagerFromFile = com.yandex.app.service.Managers.loadFromFile(new File(filePath));
        System.out.print("tasks: ");
        System.out.println(fileBackedTaskManagerFromFile.getOnlyTasks());
        System.out.print("epics: ");
        System.out.println(fileBackedTaskManagerFromFile.getOnlyEpics());
        System.out.print("subtasks: ");
        System.out.println(fileBackedTaskManagerFromFile.getOnlySubtasks());
        System.out.println(fileBackedTaskManagerFromFile.getCountTasks());
    }
}