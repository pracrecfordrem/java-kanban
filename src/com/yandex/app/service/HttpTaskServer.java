package com.yandex.app.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpServer;
import com.yandex.app.model.DurationAdapter;
import com.yandex.app.model.LocalDateTimeAdapter;
import com.yandex.app.model.Task;

import java.time.Duration;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;

public class HttpTaskServer {
    public static final int PORT = 8080;
    //static String filePath = "C:\\Users\\rbarinov\\Desktop\\temp\\Tasks.txt";
    //static com.yandex.app.service.FileBackedTaskManager fileBackedTaskManager = (FileBackedTaskManager) com.yandex.app.service.Managers.getDefaultFiledBacked(filePath);

    public static void main(String[] args) throws IOException {

        InMemoryTaskManager inMemoryTaskManager = (InMemoryTaskManager) Managers.getDefault();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Duration.class, new DurationAdapter());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());

        Gson gson = gsonBuilder.create();

        HttpServer httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks",new TasksHttpHandler(inMemoryTaskManager, gson));
        httpServer.createContext("/subtasks",new SubtasksHttpHandler(inMemoryTaskManager, gson));
        httpServer.createContext("/epics",new EpicsHttpHadler(inMemoryTaskManager, gson));
        httpServer.createContext("/prioritized",new PrioritizedHttpHandler(inMemoryTaskManager, gson));
        httpServer.createContext("/history",new HistoryHttpHandler(inMemoryTaskManager, gson));
        httpServer.start();
    }

//    public static InMemoryTaskManager userScenarioFirst() {
//        com.yandex.app.service.InMemoryTaskManager inMemoryTaskManager = (InMemoryTaskManager) Managers.getDefault();
//
//        LocalDateTime firstTaskStartTime = LocalDateTime.now();
//        LocalDateTime secondTaskStartTime = firstTaskStartTime.plusMinutes(200);
////        Пользовательский сценарий
//
//        inMemoryTaskManager.createTask(new com.yandex.app.model.Task("Встать с постели","Просто встать с постели",com.yandex.app.model.Status.NEW, 100, firstTaskStartTime,inMemoryTaskManager.getCountTasks()));
//        inMemoryTaskManager.createTask(new com.yandex.app.model.Task("Задача № 2","Описание задачи",com.yandex.app.model.Status.NEW, 15, firstTaskStartTime.minusMinutes(1000),inMemoryTaskManager.getCountTasks()));
//        inMemoryTaskManager.createEpic(new com.yandex.app.model.Epic("Собраться на работу", "Долго и мучительно",inMemoryTaskManager.getCountTasks()));
//        inMemoryTaskManager.createSubtask(new com.yandex.app.model.SubTask("Собраться на работу", "Долго и мучительно",com.yandex.app.model.Status.NEW,150, secondTaskStartTime,3,inMemoryTaskManager.getCountTasks()));
//        inMemoryTaskManager.updateTask(1,new com.yandex.app.model.Task("Встать с постели","Просто встать с постели",com.yandex.app.model.Status.IN_PROGRESS,100, firstTaskStartTime.plusMinutes(1),0));
//
//        return inMemoryTaskManager;
//    }
}
