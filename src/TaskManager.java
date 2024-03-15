import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class TaskManager {
    HashMap<Integer,Task> tasks = new HashMap<>();
    HashMap<Integer,Epic> epics = new HashMap<>();
    HashMap<Integer,SubTask> subtasks = new HashMap<>();

    //вспомогательная структра для ускорения поиска

    HashMap<Integer,TaskType> taskTypes = new HashMap<>();

    public static int getCountTasks() {
        return countTasks;
    }

    private static int countTasks;



    public void createTask (Task task) {
        tasks.put(++countTasks,task);
        taskTypes.put(countTasks, TaskType.TASK);
    }

    public void createEpic (Epic epic) {
        epics.put(++countTasks, epic);
        taskTypes.put(countTasks, TaskType.EPIC);
    }

    public void createSubtask (SubTask subtask) {
        subtasks.put(++countTasks,subtask);
        taskTypes.put(countTasks, TaskType.SUBTASK);
    }

    public void deleteTask (int id) {
        /*if ()
        if(tasks.get(id).getStatus().name().equals("EPIC")) {
            for (int key: tasks.keySet()) {
                if (tasks.get(key).getStatus().name().equals("SUBTASK") && (SubTask)tasks.get(key).getEpicId())
            }
        }
        tasks.remove(id);
*/
        countTasks--;
    }



}
