package org.example.app.services;

import org.example.web.dto.Equipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentRentService {
    private final ProjectRepository<Equipment> equipmentRepo;

    @Autowired
    public EquipmentRentService(EquipmentRentRepository equipmentRepo) {
        this.equipmentRepo = equipmentRepo;
    }

    public List<Equipment> getAllEquipment() {
        return equipmentRepo.retrieveAll();
    }

    public List<Equipment> getFoundEquipment() {
        return equipmentRepo.retrieveFound();
    }

    public boolean findEquipmentByName(String equipmentNameToFind) {
        return equipmentRepo.findItemByName(equipmentNameToFind);
    }

    public void setFave(Equipment equipment) {
        equipmentRepo.setFave(equipment);
    }
}