package com.yandex.app;


import java.io.File;

public class Main {

    public static void main(String[] args) {
//        String filePath = "C:\\Users\\rbarinov\\Desktop\\temp\\Tasks.txt";

//        Пользовательский сценарий №1
//        com.yandex.app.service.TaskManager fileBackedTaskManagerFromFile = com.yandex.app.service.Managers.loadFromFile(new File(filePath));
//        System.out.println(fileBackedTaskManagerFromFile.getOnlyTasks());
//        System.out.println(fileBackedTaskManagerFromFile.getOnlyEpics());
//        System.out.println(fileBackedTaskManagerFromFile.getOnlySubtasks());
//        System.out.println(fileBackedTaskManagerFromFile.getCountTasks());
//
//        //Пользовательский сценарий №2

//        com.yandex.app.service.TaskManager fileBackedTaskManager = com.yandex.app.service.Managers.getDefaultFiledBacked(filePath);
//        com.yandex.app.service.TaskManager taskManager2 = com.yandex.app.service.Managers.getDefault();
//        com.yandex.app.service.InMemoryHistoryManager historyManager = com.yandex.app.service.Managers.getDefaultHistory();
//
//
//        fileBackedTaskManager.createTask(new com.yandex.app.model.Task("Встать с постели","Просто встать с постели",com.yandex.app.model.Status.NEW,fileBackedTaskManager.getCountTasks()));
//        fileBackedTaskManager.createEpic(new com.yandex.app.model.Epic("Собраться на работу", "Долго и мучительно",fileBackedTaskManager.getCountTasks()));
//        fileBackedTaskManager.createSubtask(new com.yandex.app.model.SubTask("Собраться на работу", "Долго и мучительно",com.yandex.app.model.Status.NEW,2, fileBackedTaskManager.getCountTasks()));
//
//        fileBackedTaskManager.showAllTasks();
//        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------");
//        fileBackedTaskManager.updateTask(1,new com.yandex.app.model.Task("Встать с постели","Просто встать с постели",com.yandex.app.model.Status.IN_PROGRESS,0));
//        fileBackedTaskManager.updateSubtask(3,new com.yandex.app.model.SubTask("Собраться на работу", "Долго и мучительно",com.yandex.app.model.Status.IN_PROGRESS,2,2));
//
//        fileBackedTaskManager.showAllTasks();
//        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------");
//        fileBackedTaskManager.deleteTask(3);
//        fileBackedTaskManager.createSubtask(new com.yandex.app.model.SubTask("Пора уже выходить", "БЫСТРО",com.yandex.app.model.Status.NEW,2, fileBackedTaskManager.getCountTasks()));
//        fileBackedTaskManager.createSubtask(new com.yandex.app.model.SubTask("Дойти до остановки", "БЫСТРО",com.yandex.app.model.Status.DONE,2,fileBackedTaskManager.getCountTasks()));
//        fileBackedTaskManager.showAllTasks();
//        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------");
//        System.out.println(fileBackedTaskManager.getOnlyTasks());
//        System.out.println(fileBackedTaskManager.getOnlyEpics());
//        System.out.println(fileBackedTaskManager.getOnlySubtasks());
//        System.out.println("head: " + historyManager.getCustomLinkedList().getHead() + " tail: " + historyManager.getCustomLinkedList().getTail() + ", size: " + historyManager.getCustomLinkedList().getSize() + ", viewedTasks: " + historyManager.getCustomLinkedList().getViewedTasks());
//        System.out.println(fileBackedTaskManager.getTaskById(1));
//        System.out.println("head: " + historyManager.getCustomLinkedList().getHead() + " tail: " + historyManager.getCustomLinkedList().getTail() + ", size: " + historyManager.getCustomLinkedList().getSize() + ", viewedTasks: " + historyManager.getCustomLinkedList().getViewedTasks());
//        System.out.println(fileBackedTaskManager.getTaskById(1));
//        System.out.println("head: " + historyManager.getCustomLinkedList().getHead() + " tail: " + historyManager.getCustomLinkedList().getTail() + ", size: " + historyManager.getCustomLinkedList().getSize() + ", viewedTasks: " + historyManager.getCustomLinkedList().getViewedTasks());
//        System.out.println(fileBackedTaskManager.getEpicById(2));
//        System.out.println("head: " + historyManager.getCustomLinkedList().getHead() + " tail: " + historyManager.getCustomLinkedList().getTail() + ", size: " + historyManager.getCustomLinkedList().getSize() + ", viewedTasks: " + historyManager.getCustomLinkedList().getViewedTasks());
//        System.out.println(fileBackedTaskManager.getEpicById(2));
//        System.out.println("head: " + historyManager.getCustomLinkedList().getHead() + " tail: " + historyManager.getCustomLinkedList().getTail() + ", size: " + historyManager.getCustomLinkedList().getSize() + ", viewedTasks: " + historyManager.getCustomLinkedList().getViewedTasks());
//
//
//        System.out.println("История просмотров: " + historyManager.getCustomLinkedList().getViewedTasks());

    }
}