public class SubTask extends Task {
    int epicId;

    public SubTask(String name, String description, Status status, int epicId) {
        super(name, description, status, TaskType.SUBTASK);
        this.epicId = epicId;
    }
    public SubTask(String name, String description, Status status, int epicId, int id) {
        super(name, description, status, TaskType.SUBTASK, id);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                ", tasktype=" + taskType +
                ", epicId=" + epicId +
                '}';
    }
}
