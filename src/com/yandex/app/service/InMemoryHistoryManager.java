package com.yandex.app.service;

import com.yandex.app.model.*;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{
    static List<Task> viewedTasks = new ArrayList<>();
    @Override
    public void add(Task task) {
        viewedTasks.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return viewedTasks;
    }

}
