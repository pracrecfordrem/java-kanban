package com.yandex.app.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.yandex.app.model.Task;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class TasksHttpHandler extends BaseHttpHandler implements HttpHandler {
    TaskManager taskManager;
    Gson gson;

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
                case "GET" : {
                    if (Pattern.matches("^/tasks$",path)) {
                        String response = gson.toJson(taskManager.getOnlyTasks().toString());
                        sendText(exchange,response);
                        return;
                    }
                    if (Pattern.matches("^/tasks/\\d+$",path)) {
                        int id = parsePathId(path.replace("/tasks/",""));
                        if (taskManager.getTaskById(id) == null) {
                            sendNotFound(exchange,"Задача не найдена");
                            return;
                        }
                        if (id != -1) {
                            String response = gson.toJson(taskManager.getTaskById(id));
                            sendText(exchange,response);
                        } else {
                            System.out.println("Получен некорректный ид:" + id);
                            exchange.sendResponseHeaders(405,0);
                        }
                    }
                    break;
                }
                case "POST": {
                    HttpClient client = HttpClient.newHttpClient();
                    URI url = URI.create("http://localhost:8080" + exchange.getRequestURI());
                    System.out.println(url);
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(url)
                            .GET()
                            .build();
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    if  (Pattern.matches("^/tasks$",path)) {
                        JsonElement jsonElement = JsonParser.parseString(response.body());
                        JsonObject jsonObject = jsonElement.getAsJsonObject();
                        System.out.println(jsonObject);
                        sendText(exchange,"текст");
                        //JsonElement jsonElement = exchange.п;
                    }
                    break;
                }
                case "DELETE": {

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
