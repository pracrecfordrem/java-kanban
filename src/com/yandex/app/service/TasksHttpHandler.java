package com.yandex.app.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.yandex.app.model.Status;
import com.yandex.app.model.Task;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class TasksHttpHandler extends BaseHttpHandler implements HttpHandler {
    TaskManager taskManager;
    Gson gson;
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm dd.MM.yy");

    public TasksHttpHandler(TaskManager taskManager, Gson gson) {
        this.taskManager = taskManager;
        this.gson = gson;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String path = exchange.getRequestURI().getPath();
            String requestMethod = exchange.getRequestMethod();
            switch (requestMethod) {
                case "GET": {
                    if (Pattern.matches("^/tasks$", path)) {
                        String response = gson.toJson(taskManager.getOnlyTasks());
                        sendText(exchange, response);
                        return;
                    }
                    if (Pattern.matches("^/tasks/\\d+$", path)) {
                        int id = parsePathId(path.replace("/tasks/", ""));
                        if (taskManager.getTaskById(id) == null) {
                            sendNotFound(exchange, "Задача не найдена");
                            return;
                        }
                        if (id != -1) {
                            String response = gson.toJson(taskManager.getTaskById(id));
                            sendText(exchange, response);
                        } else {
                            System.out.println("Получен некорректный ид:" + id);
                            exchange.sendResponseHeaders(405, 0);
                        }
                    }
                    break;
                }
                case "POST": {
                    if (Pattern.matches("^/tasks$", path)) {
                        InputStream inputStream = exchange.getRequestBody();
                        String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                        JsonElement jsonElement = JsonParser.parseString(body);
                        JsonObject jsonObject = jsonElement.getAsJsonObject();
                        String name = jsonObject.get("name").getAsString();
                        String description = jsonObject.get("description").getAsString();
                        String status = jsonObject.get("status").getAsString();
                        int duration = jsonObject.get("duration").getAsInt();
                        LocalDateTime startTime = LocalDateTime.parse(jsonObject.get("startTime").getAsString(), DATE_TIME_FORMATTER);
                        if (jsonObject.has("id")) {
                            int taskId = jsonObject.get("id").getAsInt();
                            int res = taskManager.updateTask(taskId, new Task(name, description, Status.valueOf(status), duration, startTime, taskId));
                            if (res == 1) {
                                sendText(exchange, "Задача успешно обновлена");
                            } else if (res == 0) {
                                sendNotFound(exchange, "Задача не найдена");
                            } else if (res == -1) {
                                sendHasInteractions(exchange, "Обновляемая задача имеет пересечения");
                            }
                        } else {
                            int res = taskManager.createTask(new Task(name, description, Status.valueOf(status), duration, startTime, taskManager.getCountTasks()));
                            if (res == -1) {
                                sendHasInteractions(exchange, "Добавляемая задача имеет пересечения");
                            } else {
                                sendText(exchange, "Задача успешно добавлена");
                            }
                        }
                    }
                    break;
                }
                case "DELETE": {
                    if (Pattern.matches("^/tasks/\\d+$", path)) {
                        int taskId = parsePathId(path.replace("/tasks/", ""));
                        taskManager.deleteTask(taskId);
                        sendText(exchange, "Задача с ид " + taskId + " была удалена");
                    }
                    break;
                }
                default: {
                    System.out.println("Ожидается GET, POST или DELETE запрос, вместо чего получен - " + requestMethod);
                    exchange.sendResponseHeaders(405, 0);
                }

            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            exchange.close();

        }
    }

}
