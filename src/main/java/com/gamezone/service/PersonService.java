package com.gamezone.service;
import com.gamezone.model.Person;
import com.gamezone.persistence.PersonFileHandler;
import java.util.List;

    public class PersonService {
        private PersonFileHandler fileHandler;

        public PersonService(PersonFileHandler fileHandler) {
            this.fileHandler = fileHandler;
        }

        public void addPerson(Person person){
            List<Person> persons = fileHandler.loadPersons();
            persons.add(person);
            fileHandler.savePersons(persons);
        }

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

