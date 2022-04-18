package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.controllers.EquipmentRentController;
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
    private final Logger logger = Logger.getLogger(EquipmentRentService.class);

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

    public List<Equipment> saveUserEquipment(@NotNull User user, @NotNull List<String> cartList) {
        Set<Equipment> equipmentSet = user.getUserEquipment().getRentHistory().keySet();
        equipmentSet.addAll(user.getUserEquipment().getOnRentNow().keySet());
        Set<Equipment> cartSet = cartList.stream().map(this::getEquipmentByName).collect(Collectors.toSet());

        logger.info("cart set is");
        for (Equipment e : cartSet) {
            logger.info(e);
        }

        Set<String> equipmentNames = new HashSet<>();
        equipmentSet.forEach(e -> equipmentNames.add(e.getName()));
        logger.info("user equipment is");
        for (String e : equipmentNames) {
            logger.info(e);
        }

        List<Equipment> result = new ArrayList<>();

        cartSet.forEach(e -> {
            Map<String, Object> params = new HashMap<>();
            params.put("id", new Random().nextInt());
            params.put("u_id", user.getId());
            params.put("eq_id", e.getId());
            logger.info("is equipment " + e + " in equipment set?");
            boolean isInSet = equipmentNames.contains(e.getName());
            logger.info(isInSet);
            if (!isInSet) {
                String exp = "INSERT INTO users_equipment(id,u_id,eq_id) VALUES (:id,:u_id,:eq_id)";
                result.add(e);
                logger.info("user equipment updated:");
                boolean put = user.getUserEquipment().addToRentHistory(e, 1);
                logger.info(e.getName() + " has been put - " + put);
//                user.getUserEquipment().getRentHistory().keySet().forEach(logger::info);
                jdbcTemplate.update(exp, params);
            }
        });

        return result;
    }

    public void setFave(Equipment equipment) {
        equipmentRepo.setFave(equipment);
    }
}