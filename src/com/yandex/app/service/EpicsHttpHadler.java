package com.yandex.app.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.yandex.app.model.Epic;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class EpicsHttpHadler extends BaseHttpHandler implements HttpHandler {
    TaskManager taskManager;
    Gson gson;
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm dd.MM.yy");

    public EpicsHttpHadler(TaskManager taskManager, Gson gson) {
        this.taskManager = taskManager;
        this.gson = gson;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String path = exchange.getRequestURI().getPath();
            String requestMethod = exchange.getRequestMethod();
            switch (requestMethod) {
                case "GET" : {
                    if (Pattern.matches("^/epics$",path)) {
                        String response = gson.toJson(taskManager.getOnlyEpics());
                        sendText(exchange,response);
                    }
                    if (Pattern.matches("^/epics/\\d+$",path)) {
                        int id = parsePathId(path.replace("/epics/",""));
                        if (taskManager.getEpicById(id) == null) {
                            sendNotFound(exchange,"Эпик не найден");
                        }
                        if (id != -1) {
                            String response = gson.toJson(taskManager.getEpicById(id));
                            sendText(exchange,response);
                        } else {
                            System.out.println("Получен некорректный ид:" + id);
                            exchange.sendResponseHeaders(405,0);
                        }
                    }
                    if (Pattern.matches("^/epics/\\d+/subtasks",path)) {
                        int id = parsePathId(path.replace("/epics/","").replace("/subtasks",""));
                        if (taskManager.getEpicById(id) == null) {
                            sendNotFound(exchange,"Эпик не найден");
                        }
                        if (id != -1) {
                            String response = gson.toJson(taskManager.getEpicById(id).getSubTaskIds());
                            sendText(exchange,response);
                        } else {
                            System.out.println("Получен некорректный ид:" + id);
                            exchange.sendResponseHeaders(405,0);
                        }
                    }
                    break;
                }
                case "POST": {
                    if  (Pattern.matches("^/epics$",path)) {
                        InputStream inputStream = exchange.getRequestBody();
                        String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                        JsonElement jsonElement = JsonParser.parseString(body);
                        JsonObject jsonObject = jsonElement.getAsJsonObject();
                        String name = jsonObject.get("name").getAsString();
                        String description = jsonObject.get("description").getAsString();
                        taskManager.createEpic(new Epic(name,description,taskManager.getCountTasks()));
                        sendText(exchange,"Эпик успешно добавлен");
                    }
                    break;
                }
                case "DELETE": {
                    if (Pattern.matches("^/epics/\\d+$",path)) {
                        int epicId = parsePathId(path.replace("/epics/",""));
                        taskManager.deleteSubTask(epicId);
                        sendText(exchange,"Эпик с ид " + epicId + " был удален");
                    }
                    break;
                }
                default: {
                    System.out.println("Ожидается GET, POST или DELETE запрос, вместо чего получен - " + requestMethod);
                    exchange.sendResponseHeaders(405,0);
                }

            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            exchange.close();

        }
    }

}
