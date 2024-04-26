package com.yandex.app;

import com.yandex.app.model.Status;
import com.yandex.app.model.SubTask;
import com.yandex.app.model.Task;
import com.yandex.app.service.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {

//          ArrayList<Integer> test = new ArrayList<>();
//          HashMap<Integer,String> hashTest = new HashMap<>();
//          test.add(6);
//          test.add(6);
//          int index = test.indexOf(6);
//          System.out.println(index);
//        System.out.println(hashTest);
//        hashTest.put(5,"ABC");
//        System.out.println(hashTest);
//        hashTest.put(5,"ABC");
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
         com.yandex.app.service.TaskManager taskManager = com.yandex.app.service.Managers.getDefault();
         com.yandex.app.service.TaskManager taskManager2 = com.yandex.app.service.Managers.getDefault();
        System.out.println(taskManager2 + " " + taskManager );
         com.yandex.app.service.HistoryManager historyManager = com.yandex.app.service.Managers.getDefaultHistory();


        taskManager.createTask(new com.yandex.app.model.Task("Встать с постели","Просто встать с постели",com.yandex.app.model.Status.NEW,taskManager.getCountTasks()));
        taskManager.createEpic(new com.yandex.app.model.Epic("Собраться на работу", "Долго и мучительно",taskManager.getCountTasks()));
        taskManager.createSubtask(new com.yandex.app.model.SubTask("Собраться на работу", "Долго и мучительно",com.yandex.app.model.Status.NEW,2, taskManager.getCountTasks()));
        System.out.println(taskManager.getTaskById(1));
        System.out.println(historyManager.getHistory());
        taskManager.showAllTasks();
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        taskManager.updateTask(1,new com.yandex.app.model.Task("Встать с постели","Просто встать с постели",com.yandex.app.model.Status.IN_PROGRESS,0));
        taskManager.updateSubtask(3,new com.yandex.app.model.SubTask("Собраться на работу", "Долго и мучительно",com.yandex.app.model.Status.IN_PROGRESS,2,2));

        taskManager.showAllTasks();
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        taskManager.deleteTask(3);
        taskManager.createSubtask(new com.yandex.app.model.SubTask("Пора уже выходить", "БЫСТРО",com.yandex.app.model.Status.NEW,2, taskManager.getCountTasks()));
        taskManager.createSubtask(new com.yandex.app.model.SubTask("Дойти до остановки", "БЫСТРО",com.yandex.app.model.Status.DONE,2,taskManager.getCountTasks()));
        taskManager.showAllTasks();
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println(taskManager.getOnlyTasks());
//        System.out.println(taskManager2.getOnlyTasks());
//        System.out.println(taskManager.getOnlyEpics());
//        System.out.println(taskManager.getOnlySubtasks());
        System.out.println(taskManager.getTaskById(1));
        taskManager.getEpicById(2);
        System.out.println(historyManager.getHistory());
//        System.out.println(taskManager.getTaskById(123));
//        System.out.println(historyManager.getHistory());

    }
}
