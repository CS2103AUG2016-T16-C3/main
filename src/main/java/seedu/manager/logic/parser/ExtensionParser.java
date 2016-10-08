package seedu.manager.logic.parser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.manager.commons.exceptions.IllegalValueException;
import seedu.manager.model.task.Desc;
import seedu.manager.model.task.EndTime;
import seedu.manager.model.task.Priority;
import seedu.manager.model.task.StartTime;
import seedu.manager.model.task.Task;
import seedu.manager.model.task.Task.TaskProperties;
import seedu.manager.model.task.TaskProperty;
import seedu.manager.model.task.Venue;

/**
 * Used to parse extensions in the user input
 * @author varungupta
 *
 */
public class ExtensionParser {
    
    private static enum ExtensionCmds {
        VENUE("venue"), BEFORE("before"), AT("at"), AFTER("after"), PRIORITY("priority");
        
        private String value;
        
        private ExtensionCmds(String value) {
            this.value = value;
        }
    };
    private static final String EXTENSION_REGEX_OPTIONS;
    private static final Pattern EXTENSIONS_DESC_FORMAT;
    private static final Pattern EXTENSIONS_ARGS_FORMAT;
    private static final Pattern EXTENSION_ARGS_FORMAT = 
            Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final String EXTENSION_INVALID_FORMAT = "Extensions should have the form <extension> <arguments>"; 
    
    static {
        EXTENSION_REGEX_OPTIONS = String.join("|", Arrays.stream(ExtensionCmds.values()).map(ex -> ex.value).toArray(size -> new String[size]));
        EXTENSIONS_DESC_FORMAT = 
                Pattern.compile("(^.+?(?=(?:(?:\\s(?:"
                        + EXTENSION_REGEX_OPTIONS
                        + ")\\s)|$)))");
        EXTENSIONS_ARGS_FORMAT =
                Pattern.compile("((?:"
                        + EXTENSION_REGEX_OPTIONS
                        + ").+?(?=(?:(?:\\s(?:"
                        + EXTENSION_REGEX_OPTIONS
                        + ")\\s)|$)))");
    }
    
    public ExtensionParser() {}
    
    /**
     * Build task from extensions
     */
    public HashMap<Task.TaskProperties, Optional<TaskProperty>> getTask(String extensionsStr) throws IllegalValueException {
        HashMap<Task.TaskProperties, Optional<TaskProperty>> properties = new HashMap<>();
        extensionsStr = extensionsStr.trim();
        
        for (Task.TaskProperties property : Task.TaskProperties.values()) {
            properties.put(property, Optional.empty());
        }
        
        Matcher descMatcher = EXTENSIONS_DESC_FORMAT.matcher(extensionsStr);
        if (descMatcher.find()) {
            properties.put(TaskProperties.DESC, Optional.of(parseDesc(descMatcher.group())));
            if (descMatcher.find()) {
                throw new IllegalValueException(EXTENSION_INVALID_FORMAT);
            }
        }
        
        Matcher extMatcher = EXTENSIONS_ARGS_FORMAT.matcher(extensionsStr);
        while (extMatcher.find()) {
            parseSingleExtension(extMatcher.group(), properties);
        }
        
        return properties;
    }
    
    /**
     * Parses a single extension
     * @throws IllegalValueException
     */
    private void parseSingleExtension(String extension, HashMap<Task.TaskProperties, Optional<TaskProperty>> properties) 
            throws IllegalValueException{
        Matcher matcher = EXTENSION_ARGS_FORMAT.matcher(extension);
        if (matcher.matches()) {
            String extensionCommand = matcher.group("commandWord");
            String arguments = matcher.group("arguments").trim();
            ExtensionCmds matchedCommand = null;
            
            for (ExtensionCmds ex : ExtensionCmds.values()) {
                if (ex.value.equals(extensionCommand)) {
                    matchedCommand = ex;
                    break;
                }
            }
            
            if (matchedCommand == null) {
                throw new IllegalValueException(EXTENSION_INVALID_FORMAT);
            }
            
            switch (matchedCommand) {
            case VENUE:
                properties.put(TaskProperties.VENUE, Optional.of(parseVenue(arguments)));
                break;
            case BEFORE:
                properties.put(TaskProperties.ENDTIME, Optional.of(parseEndTime(arguments)));
                break;
            case AFTER:
                properties.put(TaskProperties.STARTTIME, Optional.of(parseStartTime(arguments)));
                break;
            case AT:
                properties.put(TaskProperties.STARTTIME, Optional.of(parseStartTime(arguments)));
                break;
            case PRIORITY:
                properties.put(TaskProperties.PRIORITY, Optional.of(parsePriority(arguments)));
                break;
            default:
                throw new IllegalValueException(EXTENSION_INVALID_FORMAT);
            }
        } else {
            throw new IllegalValueException(EXTENSION_INVALID_FORMAT);
        }
    }
    
    private TaskProperty parseDesc(String desc) throws IllegalValueException {
        return new Desc(desc);
    }
    
    private TaskProperty parseVenue(String venue) throws IllegalValueException {
        return new Venue(venue);
    }
    
    private TaskProperty parsePriority(String priority) throws IllegalValueException {
        return new Priority(priority);
    }
    
    private TaskProperty parseStartTime(String time) throws IllegalValueException {
        return new StartTime(time);
    }
    
    private TaskProperty parseEndTime(String time) throws IllegalValueException {
        return new EndTime(time);
    }
}
