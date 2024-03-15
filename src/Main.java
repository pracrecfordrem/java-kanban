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

        taskManager.showTasks();
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        taskManager.updateTask(1,new Task("Встать с постели","Просто встать с постели",Status.IN_PROGRESS,1));
        taskManager.updateSubtask(3,new SubTask("Собраться на работу", "Долго и мучительно",Status.IN_PROGRESS,2,3));

        taskManager.showTasks();
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        taskManager.deleteTask(3);
        taskManager.createSubtask(new SubTask("Пора уже выходить", "БЫСТРО",Status.NEW,2));
        taskManager.showTasks();

    }
}
