package org.example.app.services;

import java.util.List;

public interface ProjectRepository<T> {
    List<T> retrieveAll();

    List<T> retrieveFound();

    void store(T equipment);

    boolean findItemByName(String name);

    void setFave(T equipment);
}