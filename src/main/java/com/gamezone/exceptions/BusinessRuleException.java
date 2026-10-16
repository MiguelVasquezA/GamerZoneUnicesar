package com.gamezone.exceptions;

/**
 * Exception thrown when an operation violates a domain business rule.
 *
 * @author Miguel Vasquez
 * @version 1.0
 */
public class BusinessRuleException extends GameZoneException {

    /**
     * Constructs a BusinessRuleException with a specific violation message.
     *
     * @param message description of the violated business rule
     */
    public BusinessRuleException(String message) {
        super(message, "GZ-ERR-400");
    }
}
