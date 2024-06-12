package com.yandex.app.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.yandex.app.model.Status;
import com.yandex.app.model.SubTask;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class SubtasksHttpHandler extends BaseHttpHandler implements HttpHandler {
    TaskManager taskManager;
    Gson gson;
    DateTimeFormatter DATE_TIME_FORMATTER;
    public SubtasksHttpHandler(TaskManager taskManager, Gson gson, DateTimeFormatter DATE_TIME_FORMATTER) {
        this.taskManager = taskManager;
        this.gson = gson;
        this.DATE_TIME_FORMATTER = DATE_TIME_FORMATTER;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String path = exchange.getRequestURI().getPath();
            String requestMethod = exchange.getRequestMethod();
            switch (requestMethod) {
                case "GET" : {
                    if (Pattern.matches("^/subtasks$",path)) {
                        String response = gson.toJson(taskManager.getOnlySubtasks());
                        sendText(exchange,response);
                        return;
                    }
                    if (Pattern.matches("^/subtasks/\\d+$",path)) {
                        int id = parsePathId(path.replace("/subtasks/",""));
                        if (taskManager.getSubTaskById(id) == null) {
                            sendNotFound(exchange,"Подзадача не найдена");
                            return;
                        }
                        if (id != -1) {
                            String response = gson.toJson(taskManager.getSubTaskById(id));
                            sendText(exchange,response);
                        } else {
                            System.out.println("Получен некорректный ид:" + id);
                            exchange.sendResponseHeaders(405,0);
                        }
                    }
                    break;
                }
                case "POST": {
                    if  (Pattern.matches("^/subtasks$",path)) {
                        InputStream inputStream = exchange.getRequestBody();
                        String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                        JsonElement jsonElement = JsonParser.parseString(body);
                        JsonObject jsonObject = jsonElement.getAsJsonObject();
                        String name = jsonObject.get("name").getAsString();
                        String description = jsonObject.get("description").getAsString();
                        String status = jsonObject.get("status").getAsString();
                        int duration = jsonObject.get("duration").getAsInt();
                        LocalDateTime startTime = LocalDateTime.parse(jsonObject.get("startTime").getAsString(),DATE_TIME_FORMATTER);
                        int epicId = jsonObject.get("epicId").getAsInt();
                        if (jsonObject.has("id")) {
                            int subtaskId = jsonObject.get("id").getAsInt();
                            int res = taskManager.updateSubtask(subtaskId,new SubTask(name,description,Status.valueOf(status),duration,startTime,epicId,subtaskId));
                            if (res == 1) {
                                sendText(exchange,"Подзадача успешно обновлена");
                            } else if (res == 0) {
                                sendNotFound(exchange, "Подзадача не найдена");
                            } else if (res == -1) {
                                sendHasInteractions(exchange, "Обновляемая Подзадача имеет пересечения");
                            }
                        } else {
                            int res = taskManager.createSubtask(new SubTask(name,description,Status.valueOf(status),duration,startTime,epicId,taskManager.getCountTasks()));
                            if (res == -1) {
                                sendHasInteractions(exchange, "Добавляемая Подзадача имеет пересечения");
                            } else {
                                sendText(exchange,"Подзадача успешно добавлена");
                            }
                        }
                    }
                    break;
                }
                case "DELETE": {
                    if (Pattern.matches("^/subtasks/\\d+$",path)) {
                        int subtaskId = parsePathId(path.replace("/subtasks/",""));
                        taskManager.deleteSubTask(subtaskId);
                        sendText(exchange,"Подзадача с ид " + subtaskId + " была удалена");
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
