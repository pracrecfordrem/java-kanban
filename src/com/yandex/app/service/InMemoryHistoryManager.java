package com.yandex.app.service;

import com.yandex.app.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {


    private static HashMap<Integer, Node> viewedTasks = new HashMap<>();

    private static CustomLinkedList customLinkedList = new CustomLinkedList();

    public static HashMap<Integer, Node> getViewedTasks() {
        return viewedTasks;
    }

    public static CustomLinkedList getCustomLinkedList() {
        return customLinkedList;
    }

    @Override
    public void add(Task task) {
        if (task != null) {
            if (viewedTasks.containsKey(task.getId())) {
                CustomLinkedList.removeNode(viewedTasks.get(task.getId()));
                CustomLinkedList.linkLast(task);
                viewedTasks.put(task.getId(), CustomLinkedList.tail);
            } else {
                CustomLinkedList.linkLast(task);
                viewedTasks.put(task.getId(), CustomLinkedList.tail);
            }
        }
    }

    @Override
    public void remove(int id) {
        CustomLinkedList.removeNode(viewedTasks.get(id));
    }

    @Override
    public List<Task> getHistory() {
        ArrayList<Task> tasks = CustomLinkedList.getTasks();
        return List.copyOf(tasks);
    }

    public static class CustomLinkedList {

        private static Node head;
        private static Node tail;
        private static int size = 0;

        public static void linkLast(Task task) {
            if (size == 0) {
                head = new Node(task, null, null);
                tail = head;
            } else {
                tail.next = new Node(task, tail, null);
                tail = tail.next;
            }
            size++;
        }

        public static ArrayList<Task> getTasks() {
            ArrayList<Task> tasks = new ArrayList<>();
            ArrayList<Node> nodeTasks = new ArrayList<>(viewedTasks.values());
            for (int i = 0; i < nodeTasks.size(); i++) {
                tasks.add(nodeTasks.get(i).data);
            }
            return tasks;
        }

        public static void removeNode(Node node) {
            Node prev = node.prev;
            Node next = node.next;

            if (size == 0) { //move on
                return;
            } else if (size == 1) { //tail and head are being deleted
                head = null;
                tail = null;
            } else {
                if (next == null) {
                    tail = tail.prev;
                    tail.next = null;
                } else if (prev == null) {
                    head = head.next;
                    head.prev = null;
                } else {
                    prev.next = next;
                    next.prev = prev;
                }
            }
            size--;
        }

        public static Node getHead() {
            return head;
        }

        public static Node getTail() {
            return tail;
        }

        public static int getSize() {
            return size;
        }
    }

}
