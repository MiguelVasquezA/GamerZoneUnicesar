package com.gamezone.exceptions;

/**
 * Exception thrown when input data fails validation criteria.
 *
 * @author Miguel Vasquez
 * @version 1.0
 */
public class InvalidDataException extends GameZoneException {

    /**
     * Constructs an InvalidDataException for a specific field.
     *
     * @param fieldName   name of the invalid input field
     * @param description validation failure details
     */
    public InvalidDataException(String fieldName, String description) {
        super("The field '" + fieldName + "' is invalid: " + description, "GZ-ERR-422");
    }
}
