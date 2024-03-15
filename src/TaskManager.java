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
    public void updateTask (int taskId, Task updatedtask){
        tasks.put(taskId,updatedtask);
    }
    public void createEpic (Epic epic) {
        epics.put(++countTasks, epic);
        taskTypes.put(countTasks, TaskType.EPIC);
    }

    public void createSubtask (SubTask subtask) {
        subtasks.put(++countTasks,subtask);
        taskTypes.put(countTasks, TaskType.SUBTASK);
        epics.get(subtask.getEpicId()).addSubtasks(subtask);
    }

    public void updateSubtask (int subTaskId, SubTask subTask) {
        subtasks.put(subTaskId,subTask);
        //пересчитать статус по эпику этой подзадачи
    }

    public void deleteTask (int id) {
        if (taskTypes.get(id) == TaskType.TASK){
            tasks.remove(id);
        } else if (taskTypes.get(id) == TaskType.SUBTASK) {
            subtasks.remove(id);
            //пересчитать статус по эпику этой подзадачи
        } else {
            for (int key: subtasks.keySet()) {
                if (subtasks.get(key).getEpicId() == id) {
                    subtasks.remove(key);
                    countTasks--;
                }
            }
            epics.remove(id);
        }
        countTasks--;
    }

//    public void changeEpicStatus(int epicId, Status newStatus) {
//        Status status = Status.NEW;
//        for (SubTask subTask: subtasks.values()) {
//            if (subTask.epicId == epicId) {
//                if subTask.getStatus() == newStatus
//            }
//        }
//    }
}
