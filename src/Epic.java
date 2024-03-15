import java.util.ArrayList;
import java.util.HashMap;

public class Epic extends Task{
    private HashMap<Integer, SubTask> subtasks;
    public Epic(String name, String description) {
        super(name, description, Status.NEW, TaskType.EPIC);
        subtasks = new HashMap<>();
    }

    public HashMap<Integer, SubTask> getSubtasks() {
        return subtasks;
    }

    public void addSubtasks(SubTask subTask) {
        this.subtasks.put(subTask.getId(),subTask);
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        if (subtasks.isEmpty()) {
            return "Epic{" +
                    "name='" + name + '\'' +
                    ", description='" + description + '\'' +
                    ", id=" + id +
                    ", status=" + status +
                    ", tasktype=" + taskType +
                    "}";
        }
        return "Epic{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                ", tasktype=" + taskType +
                ", subtasks=" + subtasks +
                '}';
    }
}
