package org.example.app.services;

import org.example.web.dto.Equipment;
import org.example.web.dto.Message;
import org.example.web.dto.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

    public void saveUserEquipment(@NotNull User user, @NotNull List<String> cartList) {
        Set<Equipment> equipmentSet = user.getUserEquipment().getRentHistory().keySet();
        equipmentSet.addAll(user.getUserEquipment().getOnRentNow().keySet());
        Set<Equipment> cartSet = cartList.stream().map(this::getEquipmentByName).collect(Collectors.toSet());

        for (Equipment e : cartSet) {
            if (!equipmentSet.contains(e)) {
                Map<String, Object> params = new HashMap<>();
                params.put("id", user.getId());
                params.put("eq_id", e.getId());
                String exp = "INSERT INTO users_equipment(id,eq_id) VALUES (:id,:eq_id)";
                jdbcTemplate.update(exp, params);
            }
        }
    }

    public void setFave(Equipment equipment) {
        equipmentRepo.setFave(equipment);
    }
}