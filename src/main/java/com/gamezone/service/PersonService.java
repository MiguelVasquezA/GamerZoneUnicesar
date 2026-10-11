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
        PersonValidator.validatePerson(person);

        List<Person> persons = fileHandler.loadPersons();

        // Validar si ya existe una persona con el mismo ID
        boolean exists = persons.stream()
                .anyMatch(p -> p.getIdentification().equals(person.getIdentification()));

        if (exists) {
            throw new BusinessRuleException("A person with identification " + person.getIdentification() + " already exists.");
        }

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
        PersonValidator.validateId(id);

        List<Person> persons = fileHandler.loadPersons();
        return persons.stream()
                .filter(p -> p.getIdentification().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with identification: " + id));
    }
}