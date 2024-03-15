import java.util.ArrayList;

public class Epic extends Task{
    private ArrayList<Integer> subtasks;
    public Epic(String name, String description, Status status) {
        super(name, description, status, TaskType.EPIC);
        subtasks = new ArrayList<>();
    }

    public ArrayList<Integer> getSubtasks() {
        return subtasks;
    }

    public void addSubtasks(int taskId) {
        this.subtasks.add(taskId);
    }
}
