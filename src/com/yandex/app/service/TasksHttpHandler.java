package com.yandex.app.service;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.yandex.app.model.Task;

import java.io.IOException;
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
                        String response = gson.toJson(taskManager.getOnlyTasks(), Task.class);
                        sendText(exchange,response);
                        return;
                    }
                    if (Pattern.matches("^/tasks/\\d+$",path)) {
                        int id = parsePathId(path.replace("/tasks/",""));
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
