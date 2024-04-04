package com.yandex.app.service;

public class Managers {
    private static TaskManager taskManager;
    private static HistoryManager getDefaultHistory;

    public static TaskManager getDefault() {
        if (taskManager == null) {
            return new InMemoryTaskManager();
        } else {
            return taskManager;
        }
    }
    public static HistoryManager getDefaultHistory() {
        if (getDefaultHistory == null) {
            return new InMemoryHistoryManager();
        } else {
            return getDefaultHistory;
        }
    }
}
