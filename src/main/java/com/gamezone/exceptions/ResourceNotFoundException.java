package com.gamezone.exceptions;

/**
 * Exception thrown when a requested system resource cannot be found by its identifier.
 *
 * @author Miguel Vasquez
 * @version 1.0
 */
public class ResourceNotFoundException extends GameZoneException {

    /**
     * Constructs a ResourceNotFoundException with formatted entity details.
     *
     * @param resourceType name of the missing resource entity
     * @param resourceId   identifier searched
     */
    public ResourceNotFoundException(String resourceType, String resourceId) {
        super("The resource '" + resourceType + "' with ID '" + resourceId + "' was not found.", "GZ-ERR-404");
    }
}
