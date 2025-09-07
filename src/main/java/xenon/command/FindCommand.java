package xenon.command;

import xenon.exception.XenonException;
import xenon.storage.Storage;
import xenon.tasklist.TaskList;

/**
 * Represents a command that finds all tasks containing a specific phrase
 * within their descriptions from a TaskList.
 */
public class FindCommand extends Command {

    private String searchPhrase;

    /**
     * Constructs a FindCommand object to search for tasks containing a specific phrase.
     *
     * @param searchPhrase The phrase to search for within task descriptions.
     */
    public FindCommand(String searchPhrase) {
        super(false);
        this.searchPhrase = searchPhrase;
    }

    /**
     * Executes the command to search for tasks in the task list that
     * contain the specified phrase and displays the results to the user.
     *
     * @inheritDoc
     */
    @Override
    public String execute(TaskList taskList, Storage storage) throws XenonException {

        if (this.searchPhrase.isBlank()) {
            throw new XenonException("Include a word/phrase to search for");
        }

        TaskList results = taskList.findTasksContaining(this.searchPhrase);

        return results.size() == 0
                ? "No matching tasks in your list"
                : "Here are the matching tasks in your list\n" + results;

    }
}
