package seedu.manager.logic.commands;

import java.util.LinkedList;

import seedu.manager.commons.core.EventsCenter;
import seedu.manager.commons.core.Messages;
import seedu.manager.commons.events.ui.IncorrectCommandAttemptedEvent;
import seedu.manager.commons.events.ui.JumpToTaskListRequestEvent;
import seedu.manager.model.Model;
import seedu.manager.model.task.ReadOnlyTask;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    protected Model model;
    protected static LinkedList<UndoableCommand> undoList = new LinkedList<UndoableCommand>();
    /**
     * Constructs a feedback message to summarize an operation that displayed a listing of tasks.
     *
     * @param displaySize used to generate summary
     * @return summary message for tasks displayed
     */
    public static String getMessageForTaskListShownSummary(int displaySize) {
        return String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, displaySize);
    }

    /**
     * Executes the command and returns the result message.
     *
     * @return feedback message of the operation result for display
     */
    public abstract CommandResult execute();

    /**
     * Provides any needed dependencies to the command.
     * Commands making use of any of these should override this method to gain
     * access to the dependencies.
     */
    public void setData(Model model) {
        this.model = model;
    }

    /**
     * Raises an event to indicate an attempt to execute an incorrect command
     */
    protected void indicateAttemptToExecuteIncorrectCommand() {
        EventsCenter.getInstance().post(new IncorrectCommandAttemptedEvent(this));
    }
    
    // @@author A0147924X
    /**
     * Jumps to the given task
     * @param task Task to jump to
     */
    protected void jumpToTask(ReadOnlyTask task) {
    	int taskIndex = model.getIndexOfTask(task);
        assert taskIndex != -1;
        
        EventsCenter.getInstance().post(new JumpToTaskListRequestEvent(taskIndex));
    }
    
    // @@ author Jiang Yisong.
    
    /**
     * add undoable command to the undoList.
     */
    
    public void addUndo(UndoableCommand newCommand) {
    	if (newCommand != null)
    	undoList.add(newCommand);
    }
    
    // @@ author Jiang Yisong.
    
    /**
     * remove an undoable command from the undoList.
     */
    
    public void removeUndone(){
    	undoList.removeLast();
    }
    
    public boolean isEmpty() {
    	return undoList == null;
    	}
    
    public Model getModel() {
    	return model;
    }
}
