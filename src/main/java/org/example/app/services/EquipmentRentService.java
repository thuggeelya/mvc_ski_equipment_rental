package org.example.app.services;

import org.example.web.dto.Equipment;
import org.example.web.dto.Message;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class EquipmentRentService {
    private final ProjectRepository<Equipment> equipmentRepo;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public EquipmentRentService(EquipmentRentRepository equipmentRepo, NamedParameterJdbcTemplate jdbcTemplate) {
        this.equipmentRepo = equipmentRepo;
        this.jdbcTemplate = jdbcTemplate;
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

    public Equipment getEquipmentByName(String equipmentNameToFind) {
        return equipmentRepo.getEquipmentByName(equipmentNameToFind);
    }

    public void addMessage(@NotNull Message message) {
        Map<String, Object> params = new HashMap<>();

        params.put("id", new Random().nextInt());
        params.put("name", message.getName());
        params.put("email", message.getEmail());
        params.put("topic", message.getTopic());
        params.put("text", message.getText());

        String exp = "INSERT INTO messages(id,name,email,topic,text)" +
                " VALUES (:id,:name,:email,:topic,:text)";
        jdbcTemplate.update(exp, params);
    }

    public void setFave(Equipment equipment) {
        equipmentRepo.setFave(equipment);
    }
}