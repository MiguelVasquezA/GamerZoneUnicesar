package com.gamezone.persistence;

import com.gamezone.model.Person;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * handles file persistence operations for person objects,
 * allowing them to be saved and loaded using object serialization.
 */
public class PersonFileHandler {
    private final String filePath;

    /**
     * creates a new person file handler with the specified file path
     *
     * @param filePath the path of the file where persons are stored
     */
    public PersonFileHandler(String filePath) {
        this.filePath = filePath;
    }

    /**
     * saves the list of persons to the file using object serialization
     *
     * @param persons the list of persons to save
     */
    public void savePersons(List<Person> persons) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(persons);
        } catch (IOException e) {
            System.err.println("Error al guardar las personas en el archivo: " + e.getMessage());
        }
    }

    /**
     * loads the list of persons from the file.
     * if the file does not exist or is empty, returns an empty list to prevent errors.
     *
     * @return the list of loaded persons, or an empty list if not found or empty
     */
    @SuppressWarnings("unchecked")
    public List<Person> loadPersons() {
        File file = new File(filePath);
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Person>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar las personas desde el archivo: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}