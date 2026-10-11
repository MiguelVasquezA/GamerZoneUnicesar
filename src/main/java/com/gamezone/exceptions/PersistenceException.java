package com.gamezone.exceptions;

/**
 * Exception thrown when file reading/writing errors occur in the persistence layer.
 *
 * @author Miguel Vasquez
 * @version 1.0
 */
public class PersistenceException extends GameZoneException {

    /**
     * Constructs a PersistenceException wrapping an underlying file I/O exception.
     *
     * @param message contextual error description
     * @param cause   underlying IOException or error
     */
    public PersistenceException(String message, Throwable cause) {
        super(message, "GZ-ERR-503", cause);
    }
}
