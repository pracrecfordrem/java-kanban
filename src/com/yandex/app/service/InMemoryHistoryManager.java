package com.yandex.app.service;

import com.yandex.app.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{


    HashMap<Integer,Node> viewedTasks = new HashMap<>();
    CustomLinkedList customLinkedList = new CustomLinkedList();

    @Override
    public void add(Task task) {
        if (task != null) {
            if (viewedTasks.containsKey(task.getId())) {
                customLinkedList.removeNode(viewedTasks.get(task.getId()));
                customLinkedList.linkLast(task);

            } else {
                viewedTasks.put(task.getId(), new Node(task));
                customLinkedList.linkLast(task);
            }
        }
    }

    @Override
    public void remove(int id) {
        customLinkedList.removeNode(viewedTasks.get(id));
    }

    @Override
    public List<Task> getHistory() {
        ArrayList<Task> tasks = customLinkedList.getTasks();
        return List.copyOf(tasks);
    }

    public class CustomLinkedList {
        private Node head;
        private Node tail;
        private static int size = 0;

        public void linkLast(Task task){
            if (head == null) {
                head = new Node(task);
                tail = new Node(null);
                tail.prev = head;
            } else {
                tail.data = task;
                tail.next = new Node(null);
                tail = tail.next;
            }
            size++;
        }

        public ArrayList<Task> getTasks() {
            ArrayList<Task> tasks = new ArrayList<>();
            ArrayList<Node> nodeTasks = new ArrayList<>(viewedTasks.values());
            for (int i = 0; i < nodeTasks.size(); i++) {
                tasks.add(nodeTasks.get(i).data);
            }
            return tasks;
        }

        public void removeNode(Node node) {
            Node prev = node.prev;
            Node next = node.next;

            if (prev == null) {//head is being deleted
                head = next;
                head.prev = null;
            } else if (next == null) {//tail is being deleted
                prev.next = null;
                tail = prev;
            } else {
                prev.next = next;
                next.prev = prev;
            }
        }
    }

}
