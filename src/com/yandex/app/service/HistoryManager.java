package com.yandex.app.service;

import com.yandex.app.model.*;

import java.util.ArrayList;
import java.util.List;

public interface HistoryManager {
    void add(Task task);
    List<Task> getHistory();
}