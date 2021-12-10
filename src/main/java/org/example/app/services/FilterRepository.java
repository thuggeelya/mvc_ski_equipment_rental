package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Equipment;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FilterRepository implements ProjectRepositorySearch<Equipment>, ApplicationContextAware {

    private final Logger logger = Logger.getLogger(FilterRepository.class);
    private final List<Equipment> repo = new ArrayList<>();
    private ApplicationContext applicationContext;

    @Override
    public List<Equipment> retrieveAll() {
        return new ArrayList<>(repo);
    }

    @Override
    public void setFave(@NotNull Equipment equipment) {
        equipment.setFave();
        logger.info("set fave equipment: " + equipment);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}