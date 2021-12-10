package org.example.app.services;

import org.example.web.dto.Equipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilterService {
    private final ProjectRepositorySearch<Equipment> filterRepo;

    @Autowired
    public FilterService(ProjectRepositorySearch<Equipment> filterRepo) {
        this.filterRepo = filterRepo;
    }

    public void setFave(Equipment equipment) {
        filterRepo.setFave(equipment);
    }

    public List<Equipment> getAllEquipment() {
        return filterRepo.retrieveAll();
    }
}