package org.example.app.services;

import java.util.List;

public interface ProjectRepositorySearch<T> {
    List<T> retrieveAll();

    void setFave(T equipment);
}