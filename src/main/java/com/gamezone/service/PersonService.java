package com.gamezone.service;

import com.gamezone.exceptions.BusinessRuleException;
import com.gamezone.exceptions.ResourceNotFoundException;
import com.gamezone.model.Person;
import com.gamezone.persistence.PersonFileHandler;
import com.gamezone.validation.PersonValidator;

import java.util.List;

/**
 * Provides business logic services for managing persons,
 * coordinating operations between the data layer and application workflow.
 */
public class PersonService {

    private final PersonFileHandler fileHandler;

    /**
     * Creates a new person service with the specified file handler.
     *
     * @param fileHandler the file handler used for data persistence
     */
    public PersonService(PersonFileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    /**
     * Adds a new person to the persistent storage list.
     *
     * @param person the person object to be added
     */
    public void addPerson(Person person) {
        if (person == null) {
            throw new IllegalArgumentException("Person cannot be null.");
        }

        // Delegar validación de datos primitivos al validador
        PersonValidator.validatePersonData(person.getIdentification(), person.getName(), person.getPhoneNumber());

        List<Person> persons = fileHandler.loadPersons();

        // Delegar validación de unicidad al validador
        PersonValidator.validateUniquePerson(persons, person.getIdentification());

        persons.add(person);
        fileHandler.savePersons(persons);
    }

    /**
     * Searches for a person by their identification number.
     *
     * @param id the identification number to search for
     * @return the person object if found
     */
    public Person searchPerson(String id) {
        List<Person> persons = fileHandler.loadPersons();
        Person person = persons.stream()
                .filter(p -> p.getIdentification().equals(id))
                .findFirst()
                .orElse(null);

        PersonValidator.validatePersonExists(person, id);
        return person;
    }
}