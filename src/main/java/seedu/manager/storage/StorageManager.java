package seedu.manager.storage;

import com.google.common.eventbus.Subscribe;

import seedu.manager.commons.core.ComponentManager;
import seedu.manager.commons.core.LogsCenter;
import seedu.manager.commons.events.model.TaskManagerChangedEvent;
import seedu.manager.commons.events.storage.DataSavingExceptionEvent;
import seedu.manager.commons.events.storage.StorageLocationChangedEvent;
import seedu.manager.commons.events.storage.UserPrefsChangedEvent;
import seedu.manager.commons.exceptions.DataConversionException;
import seedu.manager.model.ReadOnlyTaskManager;
import seedu.manager.model.UserPrefs;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Manages storage of TaskManager data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private TaskManagerStorage taskManagerStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(TaskManagerStorage taskManagerStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.taskManagerStorage = taskManagerStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    public StorageManager(String taskManagerFilePath, String userPrefsFilePath) {
        this(new XmlTaskManagerStorage(taskManagerFilePath), new JsonUserPrefsStorage(userPrefsFilePath));
    }
    
    // ================ UserPrefs methods ==============================

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }
    
    // @@author A0147924X
    @Subscribe
    /**
     * Save user preferences when they are changed
     * @param event Event indicating that the user preferences have changed
     * @throws IOException
     */
    public void handleUserPrefsChangedEvent(UserPrefsChangedEvent event) throws IOException {
    	saveUserPrefs(event.getUserPrefs());
    }

    // @@author
    // ================ TaskManager methods ==============================
    
    @Override
    public String getTaskManagerFilePath() {
        return taskManagerStorage.getTaskManagerFilePath();
    }

    @Override
    public Optional<ReadOnlyTaskManager> readTaskManager() throws DataConversionException, IOException {
        return readTaskManager(taskManagerStorage.getTaskManagerFilePath());
    }

    @Override
    public Optional<ReadOnlyTaskManager> readTaskManager(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return taskManagerStorage.readTaskManager(filePath);
    }

    @Override
    public void saveTaskManager(ReadOnlyTaskManager taskManager) throws IOException {
        saveTaskManager(taskManager, taskManagerStorage.getTaskManagerFilePath());
    }

    @Override
    public void saveTaskManager(ReadOnlyTaskManager taskManager, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        taskManagerStorage.saveTaskManager(taskManager, filePath);
    }
    
    public void setFilePath(String filePath) {
    	taskManagerStorage.setFilePath(filePath);
    };
    
    
    // @@author A0147924X
    @Subscribe
    /**
     * Sets new file path when user wants to change the path
     * @param event Event indicating that the file path has changed
     */
    public void handleStorageLocationChangedEvent(StorageLocationChangedEvent event) {
    	logger.info(LogsCenter.getEventHandlingLogMessage(event, "Storage location changed, altering filepaths"));
    	setFilePath(event.getFilePath());
    }
    
    // @@author
    @Override
    @Subscribe
    public void handleTaskManagerChangedEvent(TaskManagerChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveTaskManager(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }
}
