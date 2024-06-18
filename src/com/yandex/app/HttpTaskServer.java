package com.yandex.app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpServer;
import com.yandex.app.model.DurationAdapter;
import com.yandex.app.model.LocalDateTimeAdapter;
import com.yandex.app.service.*;


import java.time.Duration;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;

public class HttpTaskServer {
    public static final int PORT = 8080;

    public static void main(String[] args) throws IOException {

        InMemoryTaskManager inMemoryTaskManager = (InMemoryTaskManager) Managers.getDefault();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Duration.class, new DurationAdapter());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());

        Gson gson = gsonBuilder.create();

        HttpServer httpServer = HttpServer.create();
        try {

            httpServer.bind(new InetSocketAddress(PORT), 0);
            httpServer.createContext("/tasks", new TasksHttpHandler(inMemoryTaskManager, gson));
            httpServer.createContext("/subtasks", new SubtasksHttpHandler(inMemoryTaskManager, gson));
            httpServer.createContext("/epics", new EpicsHttpHadler(inMemoryTaskManager, gson));
            httpServer.createContext("/prioritized", new PrioritizedHttpHandler(inMemoryTaskManager, gson));
            httpServer.createContext("/history", new HistoryHttpHandler(inMemoryTaskManager, gson));
            httpServer.start();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpServer.stop(3600);
        }
    }

}
