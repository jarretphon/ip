package xenon;

/**
 * A utility class that tracks the state of command operations, such as
 * whether editing is in progress and the index of the last modified command.
 */
public class CommandTracker {

    private static boolean isEditing = false;
    private static int lastModifiedIndex;

    public static boolean isEditing() {
        return isEditing;
    }

    public static void setEditing(boolean isEditing) {
        CommandTracker.isEditing = isEditing;
    }

    public static int getLastModifiedIndex() {
        return lastModifiedIndex;
    }

    public static void setLastModifiedIndex(int lastModifiedIndex) {
        CommandTracker.lastModifiedIndex = lastModifiedIndex;
    }
}
