package org.example.app.services;

import org.example.web.dto.Equipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentService {

    private final EquipmentUnitRepository equipmentUnitRepository;

    @Autowired
    public RentService(EquipmentUnitRepository equipmentUnitRepository) {
        this.equipmentUnitRepository = equipmentUnitRepository;
    }

    public Equipment findEquipmentByName(String name) {
        return equipmentUnitRepository.findEquipmentByName(name);
    }
}
