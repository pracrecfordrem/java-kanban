package com.yandex.app.service;

import com.yandex.app.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private CustomLinkedList customLinkedList = new CustomLinkedList();

    public CustomLinkedList getCustomLinkedList() {
        return customLinkedList;
    }

    @Override
    public void add(Task task) {
        if (task != null) {
            if (customLinkedList.viewedTasks.containsKey(task.getId())) {
                customLinkedList.removeNode(customLinkedList.viewedTasks.get(task.getId()));
                customLinkedList.linkLast(task);
                customLinkedList.viewedTasks.put(task.getId(), customLinkedList.tail);
            } else {
                customLinkedList.linkLast(task);
                customLinkedList.viewedTasks.put(task.getId(), customLinkedList.tail);
            }
        }
    }

    @Override
    public List<Task> getHistory() {
        ArrayList<Task> tasks = customLinkedList.getTasks();
        return List.copyOf(tasks);
    }

    @Override
    public void remove(int id) {
        if (customLinkedList.viewedTasks.get(id) != null) {
            customLinkedList.removeNode(customLinkedList.viewedTasks.get(id));
        }
    }

    public class CustomLinkedList {


        public HashMap<Integer, Node> getViewedTasks() {
            return viewedTasks;
        }

        private static HashMap<Integer, Node> viewedTasks = new HashMap<>();

        private static Node head;
        private static Node tail;
        private static int size = 0;

        public static void linkLast(Task task) {
            if (size == 0) {
                head = new Node(task, null, null);
                tail = head;
            } else {
                tail.setNext(new Node(task, tail, null));
                tail = tail.getNext();
            }
            size++;
        }

        public ArrayList<Task> getTasks() {
            ArrayList<Task> tasks = new ArrayList<>();
            ArrayList<Node> nodeTasks = new ArrayList<>(viewedTasks.values());
            for (int i = 0; i < nodeTasks.size(); i++) {
                tasks.add(nodeTasks.get(i).getData());
            }
            return tasks;
        }

        public static void removeNode(Node node) {
            Node prev = node.getPrev();
            Node next = node.getNext();

            if (size == 0) { //move on
                return;
            } else if (size == 1) { //tail and head are being deleted
                head = null;
                tail = null;
            } else {
                if (next == null) {
                    tail = tail.getPrev();
                    tail.setNext(null);
                } else if (prev == null) {
                    head = head.getNext();
                    head.setPrev(null);
                } else {
                    prev.setNext(next);
                    next.setPrev(prev);
                }
            }
            node.setPrev(null);
            node.setNext(null);
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