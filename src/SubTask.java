public class SubTask extends Task {
    int epicId;

    public SubTask(String name, String description, Status status, int epicId) {
        super(name, description, status, TaskType.SUBTASK);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }
}
