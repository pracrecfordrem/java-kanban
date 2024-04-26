package com.yandex.app.service;

import com.yandex.app.model.Task;

class Node {

    public Task data;
    public Node next;
    public Node prev;

    public Node(Task data, Node prev, Node next) {
        this.data = data;
        this.next = next;
        this.prev = prev;
    }
}