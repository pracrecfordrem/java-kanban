package com.yandex.app.service;

import com.yandex.app.model.Task;

class Node {

    public Task data;
    public Node next;
    public Node prev;

    public Node(Task data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}