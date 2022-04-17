package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.app.comparators.Comparators;
import org.example.web.dto.Equipment;
import org.example.web.dto.Message;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public class EquipmentRentRepository implements ProjectRepository<Equipment>, ApplicationContextAware {

    private final Logger logger = Logger.getLogger(EquipmentRentRepository.class);
    private final Set<Equipment> repo = new TreeSet<>(Comparators.equipmentCostComparator);
    private final Set<Equipment> filter = new TreeSet<>(Comparators.equipmentCostComparator);
    private ApplicationContext applicationContext;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public EquipmentRentRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Equipment> retrieveAll() {
        List<Equipment> equipmentList = jdbcTemplate.query("SELECT * FROM equipment", (ResultSet rs, int rowNum) -> {
            Equipment equipment = new Equipment();
            equipment.setId(rs.getInt("id"));
            equipment.setName(rs.getString("name"));
            equipment.setFirmName(rs.getString("firm_name"));
            equipment.setCost(rs.getString("cost"));
            equipment.setDescription(rs.getString("description"));
            equipment.setAvailable(rs.getBoolean("available"));
            equipment.setAvailableLeft(rs.getInt("available_left"));
            store(equipment);
            return equipment;
        });
        return new ArrayList<>(equipmentList);
    }

    @Override
    public List<Equipment> retrieveFound() {
        return new ArrayList<>(filter);
    }

    @Override
    public void store(@NotNull Equipment equipment) {
        logger.info("store new equipment: " + equipment);
        repo.add(equipment);
    }

    public Equipment getEquipmentByName(String name) {
        Equipment equipment = new Equipment();
        jdbcTemplate.query("SELECT * FROM equipment", (ResultSet rs, int rowNum) -> {
            if (rs.getString("name").equals(name)) {
                equipment.setName(rs.getString("name"));
                equipment.setFirmName(rs.getString("firm_name"));
                equipment.setCost(rs.getString("cost"));
                equipment.setDescription(rs.getString("description"));
            }
            return equipment;
        });
        return equipment;
    }

    @Override
    public boolean findItemByName(@NotNull String equipmentNameToFind) {
        filter.clear();

        if (equipmentNameToFind.isEmpty()) {
            return false;
        } else {
            logger.info("searching for " + equipmentNameToFind);
        }

        String regexp = "\\S*(" + equipmentNameToFind.toLowerCase() + ")\\S*";
        Pattern p = Pattern.compile(regexp);
        for (Equipment equipment : new ArrayList<>(repo)) {
            String name = equipment.getName().toLowerCase();
            Matcher m = p.matcher(name);
            if (m.find()) {
                logger.info("found equipment: " + equipment);
                filter.add(equipment);
            }
            String firmName = equipment.getFirmName().toLowerCase();
            m = p.matcher(firmName);
            if (m.find()) {
                logger.info("found equipment: " + equipment);
                filter.add(equipment);
            }
        }
        return !filter.isEmpty(); // add '!', go next if filter is NOT empty
    }

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setFave(@NotNull Equipment equipment) {
        equipment.setFave();
        logger.info("set fave equipment: " + equipment);
    }

    @Override
    public void lease() {
        logger.info("go for leasing");
    }

    @Override
    public void addMessage(@NotNull Message message) {}

    private void defaultInit() {
        logger.info("default INIT in equipment repo bean");
    }

    private void defaultDestroy() {
        logger.info("default DESTROY in equipment repo bean");
    }


}