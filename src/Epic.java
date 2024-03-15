import java.util.ArrayList;

public class Epic extends Task{
    private ArrayList<SubTask> subtasks;
    public Epic(String name, String description) {
        super(name, description, Status.NEW, TaskType.EPIC);
        subtasks = new ArrayList<>();
    }

    public ArrayList<SubTask> getSubtasks() {
        return subtasks;
    }

    public void addSubtasks(SubTask subTask) {
        this.subtasks.add(subTask);
    }
}
