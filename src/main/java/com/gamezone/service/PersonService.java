package com.gamezone.service;
<<<<<<< HEAD
=======

>>>>>>> 2c390879eb9d5780b4bc9ad07d592f473b574c69
import com.gamezone.model.Person;
import com.gamezone.persistence.PersonFileHandler;
import java.util.List;

<<<<<<< HEAD


public class PersonService {
    private PersonFileHandler fileHandler;

=======
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
>>>>>>> 2c390879eb9d5780b4bc9ad07d592f473b574c69
    public PersonService(PersonFileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

<<<<<<< HEAD
    public void addPerson(Person person){
        List<Person> persons = fileHandler.loadPersons();
            persons.add(person);
            fileHandler.savePersons(persons);
    }

=======
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
>>>>>>> 2c390879eb9d5780b4bc9ad07d592f473b574c69
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


