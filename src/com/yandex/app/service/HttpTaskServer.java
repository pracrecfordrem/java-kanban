package com.yandex.app.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpServer;
import com.yandex.app.model.DurationAdapter;
import com.yandex.app.model.LocalDateTimeAdapter;

import java.time.Duration;
import java.time.LocalDate;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;

public class HttpTaskServer {
    public static final int PORT = 8080;
    public static void userScenarioFirst () {
                //Пользовательский сценарий №1
        String filePath = "C:\\Users\\rbarinov\\Desktop\\temp\\Tasks.txt";
        com.yandex.app.service.FileBackedTaskManager fileBackedTaskManager = (FileBackedTaskManager) com.yandex.app.service.Managers.getDefaultFiledBacked(filePath);
        com.yandex.app.service.TaskManager taskManager2 = com.yandex.app.service.Managers.getDefault();
        com.yandex.app.service.InMemoryHistoryManager historyManager = com.yandex.app.service.Managers.getDefaultHistory();

        LocalDateTime firstTaskStartTime = LocalDateTime.now();
        LocalDateTime secondTaskStartTime = firstTaskStartTime.plusMinutes(100);

        fileBackedTaskManager.createTask(new com.yandex.app.model.Task("Встать с постели","Просто встать с постели",com.yandex.app.model.Status.NEW, 100, firstTaskStartTime,fileBackedTaskManager.getCountTasks()));
        fileBackedTaskManager.createEpic(new com.yandex.app.model.Epic("Собраться на работу", "Долго и мучительно",fileBackedTaskManager.getCountTasks()));
        fileBackedTaskManager.createSubtask(new com.yandex.app.model.SubTask("Собраться на работу", "Долго и мучительно",com.yandex.app.model.Status.NEW,150, secondTaskStartTime,2,fileBackedTaskManager.getCountTasks()));
        System.out.println(fileBackedTaskManager.tasks);
    }
    public static void main(String[] args) throws IOException {
        userScenarioFirst();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Duration.class, new DurationAdapter());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
        Gson gson = gsonBuilder.create();

        HttpServer httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(PORT), 0);
        TaskManager taskManager = Managers.getDefault();
        httpServer.createContext("/tasks",new TasksHttpHandler(taskManager, gson));
        httpServer.start();
    }
}
