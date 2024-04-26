package com.yandex.app.service;

public class Managers {
    private static InMemoryTaskManager taskManager;
    private static InMemoryHistoryManager inMemoryHistoryManager;

    public static TaskManager getDefault() {
        if (taskManager == null) {
            return new InMemoryTaskManager();
        } else {
            return taskManager;
        }
    }
    public static InMemoryHistoryManager getDefaultHistory() {
        if (inMemoryHistoryManager == null) {
            return new InMemoryHistoryManager();
        } else {
            return inMemoryHistoryManager;
        }
    }
}
