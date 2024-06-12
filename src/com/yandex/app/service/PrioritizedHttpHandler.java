package com.yandex.app.service;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.regex.Pattern;

public class PrioritizedHttpHandler extends BaseHttpHandler implements HttpHandler {

    TaskManager taskManager;
    Gson gson;

    public PrioritizedHttpHandler(TaskManager taskManager, Gson gson) {
        this.taskManager = taskManager;
        this.gson = gson;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String path = exchange.getRequestURI().getPath();
            String requestMethod = exchange.getRequestMethod();
            if (requestMethod.equals("GET")) {
                if  (Pattern.matches("^/prioritized$",path)) {
                    String response = gson.toJson(taskManager.getPrioritizedTasks());
                    sendText(exchange,response);
                }
            } else {
                System.out.println("Ожидается GET запрос, вместо чего получен - " + requestMethod);
                exchange.sendResponseHeaders(405,0);
            }
        }  catch (Exception exception) {
        exception.printStackTrace();
    } finally {
        exchange.close();

    }
    }
}
