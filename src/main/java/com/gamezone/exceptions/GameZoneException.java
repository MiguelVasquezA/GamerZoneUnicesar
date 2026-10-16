package com.gamezone.exceptions;

/**
 * Root abstract exception class for all custom GameZone system errors.
 * Extends RuntimeException to represent unchecked business and technical exceptions.
 *
 * @author Miguel Vasquez
 * @version 1.0
 */
public class GameZoneException extends RuntimeException {
    private final String errorCode;

    /**
     * Constructs a GameZoneException with a message.
     *
     * @param message error description
     */
    public GameZoneException(String message) {
        super(message);
        this.errorCode = "GZ-ERR-500";
    }

    /**
     * Constructs a GameZoneException with a message and error code.
     *
     * @param message   error description
     * @param errorCode specific error code identifier
     */
    public GameZoneException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * Constructs a GameZoneException with a message, error code, and cause.
     *
     * @param message   error description
     * @param errorCode specific error code identifier
     * @param cause     original cause exception
     */
    public GameZoneException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
