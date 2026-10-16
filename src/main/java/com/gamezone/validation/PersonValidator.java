package com.gamezone.validation;

import com.gamezone.exceptions.BusinessRuleException;
import com.gamezone.exceptions.InvalidDataException;
import com.gamezone.exceptions.ResourceNotFoundException;
import com.gamezone.model.Person;

import java.util.List;

/**
 * Dedicated validator class for customer and seller domain rules.
 *
 * @author Miguel Vasquez
 * @version 1.3
 */
public class PersonValidator {

    /**
     * Validates personal contact and identification fields.
     *
     * @param id    person document ID
     * @param name  full name
     * @param phone phone number
     */
    public static void validatePersonData(String id, String name, String phone) {
        if (id == null || id.trim().isEmpty()) {
            throw new InvalidDataException("Identification", "Identification number cannot be empty.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidDataException("Name", "Full name cannot be empty.");
        }
        if (phone == null || phone.trim().isEmpty()) {
            throw new InvalidDataException("Phone", "Phone number cannot be empty.");
        }
    }

    /**
     * Validates that a person entity is not null.
     *
     * @param person   person instance
     * @param personId searched document identifier
     */
    public static void validatePersonExists(Person person, String personId) {
        if (person == null) {
            throw new ResourceNotFoundException("Person", personId);
        }
    }

    /**
     * Validates that a person identifier is unique across existing records.
     *
     * @param existing list of existing persons
     * @param newId    new document identifier to check
     */
    public static void validateUniquePerson(List<Person> existing, String newId) {
        if (existing != null) {
            boolean exists = existing.stream()
                    .anyMatch(p -> p.getIdentification() != null && p.getIdentification().equalsIgnoreCase(newId));
            if (exists) {
                throw new BusinessRuleException("A person with ID '" + newId + "' is already registered.");
            }
        }
    }
}
