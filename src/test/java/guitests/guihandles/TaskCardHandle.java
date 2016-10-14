package guitests.guihandles;

import java.util.Optional;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.manager.model.task.ReadOnlyTask;
import seedu.manager.model.task.TaskProperty;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String DESC_FIELD_ID = "#desc";
    private static final String PRIORITY_FIELD_ID = "#priority";
    private static final String VENUE_FIELD_ID = "#venue";
	private static final String STARTTIME_FIELD_ID = "#startTime";
	private static final String ENDTIME_FIELD_ID = "#endTime";

    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getFullDesc() {
        return getTextFromLabel(DESC_FIELD_ID);
    }

    public String getPriority() {
        return getTextFromLabel(PRIORITY_FIELD_ID);
    }

    public String getVenue() {
        return getTextFromLabel(VENUE_FIELD_ID);
    }
    
    public String getStartTime() {
    	return getTextFromLabel(STARTTIME_FIELD_ID);
    }

    public String getEndTime() {
    	return getTextFromLabel(ENDTIME_FIELD_ID);
    }

    public boolean isSameTask(ReadOnlyTask task){
        return compareStringandOptional(getFullDesc(), task.getDesc()) &&
                compareStringandOptional(getVenue(), task.getVenue()) &&
                compareStringandOptional(getPriority(), task.getPriority()) &&
                compareStringandOptional(getStartTime(), task.getStartTime()) &&
                compareStringandOptional(getEndTime(), task.getEndTime());
    }
    
    private boolean compareStringandOptional(String string, Optional<TaskProperty> optional) {
        if (string == "" && !optional.isPresent()) {
            return true;
        } else if (string == "" || !optional.isPresent()) {
            return false;
        } else {
            return string.equals(optional.get().getValue());
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getFullDesc().equals(handle.getFullDesc())
                    && getPriority().equals(handle.getPriority()); //TODO: compare the rest
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getFullDesc() + " " + getPriority();
    }
}