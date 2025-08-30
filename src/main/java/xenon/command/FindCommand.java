package xenon.command;

import xenon.exception.XenonException;
import xenon.storage.Storage;
import xenon.tasklist.TaskList;
import xenon.ui.Ui;

/**
 * Represents a command that finds all tasks containing a specific phrase
 * within their descriptions from a TaskList.
 */
public class FindCommand extends Command {

    private String phrase;

    /**
     * Constructs a FindCommand object to search for tasks containing a specific phrase.
     *
     * @param phrase The phrase to search for within task descriptions.
     */
    public FindCommand(String phrase) {
        super(false);
        this.phrase = phrase;
    }

    /**
     * Executes the command to search for tasks in the task list that
     * contain the specified phrase and displays the results to the user.
     *
     * @inheritDoc
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws XenonException {

        if (this.phrase.isBlank()) {
            throw new XenonException("Include a word/phrase to search for");
        }

        TaskList results = taskList.findTasksContaining(this.phrase);

        if (results.size() == 0) {
            ui.showResponse("No matching tasks in your list");
        } else {
            ui.showResponse("Here are the matching tasks in your list\n" + results);
        }
    }
}
