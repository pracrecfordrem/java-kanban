import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        HashMap<Integer,Task> tasks = taskManager.tasks;
        HashMap<Integer,Epic> epics = taskManager.epics;
        HashMap<Integer,SubTask> subtasks = taskManager.subtasks;

        taskManager.createTask(new Task("Встать с постели","Просто встать с постели",Status.NEW));
        taskManager.createEpic(new Epic("Собраться на работу", "Долго и мучительно"));
        taskManager.createSubtask(new SubTask("Собраться на работу", "Долго и мучительно",Status.NEW,2));

        for (int key:tasks.keySet()) {
            System.out.println(key + " " + tasks.get(key).toString());
        }
        for (int epic:epics.keySet()) {
            System.out.println(epic + " " + epics.get(epic).toString());
            System.out.println("Subtasks of current epic are: " + epics.get(epic).getSubtasks());
        }
        for (int subtask:subtasks.keySet()) {
            System.out.println(subtask + " " + subtasks.get(subtask).toString());
        }
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        taskManager.updateTask(1,new Task("Встать с постели","Просто встать с постели",Status.IN_PROGRESS,1));
        taskManager.updateSubtask(3,new SubTask("Собраться на работу", "Долго и мучительно",Status.IN_PROGRESS,2,3));

        for (int key:tasks.keySet()) {
            System.out.println(key + " " + tasks.get(key).toString());
        }
        for (int epic:epics.keySet()) {
            System.out.println(epic + " " + epics.get(epic).toString());
            System.out.println("Subtasks of current epic are: " + epics.get(epic).getSubtasks());
        }
        for (int subtask:subtasks.keySet()) {
            System.out.println(subtask + " " + subtasks.get(subtask).toString());
        }

    }
}
