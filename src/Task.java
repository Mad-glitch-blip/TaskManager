public class Task {

    private String name;
    private String priority;
    private boolean completed;

    public Task(String name, String priority) {
        this.name = name;
        this.priority = priority;
        this.completed = false;
    }

    public String getName() {
        return name;
    }

    public String getPriority() {
        return priority;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void markCompleted() {
        this.completed = true;
    }

    public void markIncomplete() {
        this.completed = false;
    }

    @Override
    public String toString() {
        String status;
        if (completed) {
            status = "[X]";
        } else {
            status = "[ ]";
        }

        return status + " " + name + " (" + priority + ")";

    }

}