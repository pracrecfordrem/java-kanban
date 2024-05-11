package com.yandex.app.service;

import com.yandex.app.model.Task;

class Node {

    private Task data;
    private Node next;
    private Node prev;

    public Node(Task data, Node prev, Node next) {
        this.data = data;
        this.next = next;
        this.prev = prev;
    }

    public Task getData() {
        return data;
    }

    public Node getNext() {
        return next;
    }

    public Node getPrev() {
        return prev;
    }

    public void setData(Task data) {
        this.data = data;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }
}