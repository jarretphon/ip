public class ToDoTask extends Task {

    public ToDoTask(String description) {
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
