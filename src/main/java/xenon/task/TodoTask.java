package xenon.task;

public class TodoTask extends Task {

    public TodoTask(String description) {
        super(description);
    }

    public String toStorageString() {
        return "T | " + super.toStorageString();
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
