package seedu.manager.model.task;

import seedu.manager.commons.exceptions.IllegalValueException;

public abstract class TaskProperty {
    private static String MESSAGE_CONSTRAINTS;
    private static String VALIDATION_REGEX;
    
    /**
     * Create a TaskProperty given a string representing its value, a validation regex and a constraints message
     * 
     * @param property Value of the property. 
     * @param validationRegex
     * @param messageConstraints
     * @throws IllegalValueException
     */
    public TaskProperty(String property, String validationRegex, String messageConstraints) throws IllegalValueException {
        assert property != null;
        MESSAGE_CONSTRAINTS = messageConstraints;
        VALIDATION_REGEX = validationRegex;
        if (!isValid(property, VALIDATION_REGEX)) {
            throw new IllegalValueException(this.getMessageConstraints());
        }
    }
    
    /**
     * Tests a string against the given regex
     * 
     * @param test
     * @param validationRegex
     */
    public boolean isValid(String test, String validationRegex) {
        return test.matches(validationRegex);
    }
    
    /**
     * Gets value of property as a string
     */
    public String getValue() {
        return this.toString();
    }
    
    public int hashCode() {
        return this.toString().hashCode();
    };
    
    public String getMessageConstraints() {
        return MESSAGE_CONSTRAINTS;
    };
    
    public abstract String toString();
    
    public abstract boolean equals(Object other);
}
