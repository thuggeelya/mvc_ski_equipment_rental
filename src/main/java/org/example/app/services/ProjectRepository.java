package org.example.app.services;

import org.example.web.dto.Equipment;
import org.example.web.dto.Message;

import java.util.List;

public interface ProjectRepository<T> {
    List<T> retrieveAll();

    List<T> retrieveFound();

    void store(T equipment);

    boolean findItemByName(String name);

    Equipment getEquipmentByName(String equipmentNameToFind);

    void setFave(T equipment);

    void lease(); // for logs

    void addMessage(Message message);
}