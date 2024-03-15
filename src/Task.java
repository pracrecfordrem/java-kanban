import java.lang.reflect.Type;
import java.util.Objects;

public class Task {

    private String name;
    private String description;
    private int id;
    private Status status;
    private TaskType taskType;



    public Task(String name, String description, Status status, TaskType taskType) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.id = TaskManager.getCountTasks() + 1;
        this.taskType = taskType;
    }

    public Task(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.id = TaskManager.getCountTasks() + 1;
        this.taskType = TaskType.TASK;
    }

    public Status getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task task)) return false;
        return id == task.id && Objects.equals(name, task.name) && Objects.equals(description, task.description) && status == task.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, id, status);
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                ", tasktype=" + taskType +
                '}';
    }
}
