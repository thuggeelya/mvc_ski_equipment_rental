package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Equipment;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public class EquipmentRepository implements ProjectRepository<Equipment>, ApplicationContextAware {

    private final Logger logger = Logger.getLogger(EquipmentRepository.class);
    private final Set<Equipment> repo = new TreeSet<>();
    private final Set<Equipment> filter = new TreeSet<>();
    private ApplicationContext applicationContext;

    @Override
    public List<Equipment> retrieveAll() {
        Equipment equipment1 = new Equipment();
        equipment1.setName("Ski Bounce");
        equipment1.setCost("300");
        equipment1.setFirmName("ATOM");
        store(equipment1);
        Equipment equipment2 = new Equipment();
        equipment2.setName("Snowboard Beepoo");
        equipment2.setCost("350");
        equipment2.setFirmName("IDK");
        store(equipment2);
        Equipment equipment3 = new Equipment();
        equipment3.setName("Sticks Pro");
        equipment3.setCost("50");
        equipment3.setFirmName("ATOM");
        store(equipment3);
        Equipment equipment4 = new Equipment();
        equipment4.setName("Ski Pro");
        equipment4.setCost("400");
        equipment4.setFirmName("SMTH");
        store(equipment4);
        return new ArrayList<>(repo);
    }

    @Override
    public List<Equipment> retrieveFound() {
        return new ArrayList<>(filter);
    }

    @Override
    public void store(@NotNull Equipment equipment) {
        equipment.setId(equipment.hashCode());
        logger.info("store new equipment: " + equipment);
        repo.add(equipment);
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
        for (Equipment equipment : new ArrayList<>(repo)) { // do db instead of retrieveAll()
            String name = equipment.getName().toLowerCase();
            Matcher m = p.matcher(name);
            if (m.find()) {
                logger.info("found equipment: " + equipment);
                filter.add(equipment);
            }
        }
        return !filter.isEmpty(); // add '!': go next if filter is NOT empty
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

    private void defaultInit() {
        logger.info("default INIT in equipment repo bean");
    }

    private void defaultDestroy() {
        logger.info("default DESTROY in equipment repo bean");
    }


}