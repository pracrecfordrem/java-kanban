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

    public void updateTask (int taskId, Task updatedtask) {
        if (taskTypes.containsKey(taskId)) {
            tasks.put(taskId,updatedtask);
        } else {
            System.out.println("Изменяемая задача не найдена");
        }
    }

    public void createEpic (Epic epic) {
        epics.put(++countTasks, epic);
        taskTypes.put(countTasks, TaskType.EPIC);
    }

    public void createSubtask (SubTask subtask) {
        subtasks.put(++countTasks,subtask);
        taskTypes.put(countTasks, TaskType.SUBTASK);
        epics.get(subtask.getEpicId()).addSubtasks(subtask);
        calculateEpicStatus(subtask.getEpicId());
    }

    public void updateSubtask (int subTaskId, SubTask subtask) {
        if (taskTypes.containsKey(subTaskId)) {
            subtasks.put(subTaskId, subtask);
            epics.get(subtask.getEpicId()).getSubtasks().put(subTaskId, subtask);
            calculateEpicStatus(subtasks.get(subTaskId).getEpicId());
            //пересчитать статус по эпику этой подзадачи
        } else {
            System.out.println("Изменяемая задача не найдена");
        }
    }

    public void deleteTask (int id) {
        if (taskTypes.get(id) == TaskType.TASK){
            tasks.remove(id);
        } else if (taskTypes.get(id) == TaskType.SUBTASK) {
            SubTask delSubTask = subtasks.get(id);
            subtasks.remove(id);
            epics.get(delSubTask.getEpicId()).getSubtasks().remove(id);
            calculateEpicStatus(delSubTask.getEpicId());
            //пересчитать статус по эпику этой подзадачи ---- сделано
        } else if (taskTypes.get(id) == TaskType.EPIC) {
            for (int key: subtasks.keySet()) {
                if (subtasks.get(key).getEpicId() == id) {
                    subtasks.remove(key);
                    taskTypes.remove(key);
                }
            }
            epics.remove(id);
        } else {
            System.out.println("Удаляемая задача не найдена");
            return;
        }
        taskTypes.remove(id);
    }

    public void showTasks() {
        System.out.println("Список задач (id, task)");
        for (int key:tasks.keySet()) {
            System.out.println(key + " " + tasks.get(key).toString());
        }
        System.out.println("Список эпиков (id, epic)");
        for (int epic:epics.keySet()) {
            System.out.println(epic + " " + epics.get(epic).toString());
        }
        System.out.println("Список подзадач (id, subtask)");
        for (int subtask:subtasks.keySet()) {
            System.out.println(subtask + " " + subtasks.get(subtask).toString());
        }
    }

    public void getById(int id) {
        if (taskTypes.get(id) == TaskType.TASK){
            System.out.println(tasks.get(id));
        } else if (taskTypes.get(id) == TaskType.SUBTASK) {
            System.out.println(subtasks.get(id));
        } else {
            System.out.println(epics.get(id));
        }
    }

    public void calculateEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        int newStatus = 0;
        int inProgressStatus = 0;
        int doneStatus = 0;
        for (SubTask subtask: epic.getSubtasks().values()) {
            if (subtask.getStatus() == Status.NEW) {
                newStatus++;
            } else if (subtask.getStatus() == Status.IN_PROGRESS){
                inProgressStatus++;
                epics.get(epicId).setStatus(Status.IN_PROGRESS);
                return;
            } else if (subtask.getStatus() == Status.DONE) {
                doneStatus++;
            }
        }
        if (doneStatus == epic.getSubtasks().size() && newStatus == 0) {
            epics.get(epicId).setStatus(Status.DONE);
            return;
        } else if (newStatus == epic.getSubtasks().size() && doneStatus == 0) {
            epics.get(epicId).setStatus(Status.NEW);
        }
    }
}
