package com.gamezone.service;

import com.gamezone.model.Person;
import com.gamezone.persistence.PersonFileHandler;
import java.util.List;

/**
 * provides business logic services for managing persons,
 * coordinating operations between the data layer and application workflow.
 */
public class PersonService {
    private PersonFileHandler fileHandler;

    /**
     * creates a new person service with the specified file handler
     *
     * @param fileHandler the file handler used for data persistence
     */
    public PersonService(PersonFileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    /**
     * adds a new person to the persistent storage list
     *
     * @param person the person object to be added
     */
    public void addPerson(Person person) {
        List<Person> persons = fileHandler.loadPersons();
        persons.add(person);
        fileHandler.savePersons(persons);
    }

    /**
     * searches for a person by their identification number
     *
     * @param id the identification number to search for
     * @return the person object if found, or null otherwise
     */
    public Person searchPerson(String id) {
        List<Person> persons = fileHandler.loadPersons();
        for (Person p : persons) {
            if (p.getIdentification().equals(id)) {
                return p;
            }
        }
        return null;
    }
}


