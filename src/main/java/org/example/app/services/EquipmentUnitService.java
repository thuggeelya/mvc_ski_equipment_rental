package org.example.app.services;

import org.example.web.dto.Equipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public class EquipmentUnitService {

    private final EquipmentUnitRepository equipmentUnitRepository;

    @Autowired
    public EquipmentUnitService(EquipmentUnitRepository equipmentUnitRepository) {
        this.equipmentUnitRepository = equipmentUnitRepository;
    }

    public Map<Equipment, Boolean> findEquipmentByName(String name, HttpServletRequest request) {
        return equipmentUnitRepository.findEquipmentByName(name, request);
    }
}