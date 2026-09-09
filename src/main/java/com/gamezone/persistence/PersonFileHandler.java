package com.gamezone.persistence;

import com.gamezone.model.Person;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersonFileHandler {
    private final String filePath;

    public PersonFileHandler(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Guarda la lista de personas en el archivo mediante serialización.
     */
    public void savePersons(List<Person> persons) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(persons);
        } catch (IOException e) {
            System.err.println("Error al guardar las personas en el archivo: " + e.getMessage());
        }
    }

    /**
     * Carga la lista de personas desde el archivo.
     * Si el archivo no existe o está vacío, retorna una lista vacía para evitar errores.
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