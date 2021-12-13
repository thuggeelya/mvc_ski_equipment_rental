package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Equipment;
import org.example.web.dto.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;

@Repository
public class LeaseRepository implements ProjectLeaseRepository<User, Equipment>, ApplicationContextAware {

    private final Logger logger = Logger.getLogger(LeaseRepository.class);
    private ApplicationContext applicationContext;

    @Override
    public Equipment newLeaseEquipment(User user, @NotNull Equipment equipment) {
        equipment.setOwner(user);
        logger.info("got new leasing equipment: " + equipment);
        // void add equipment to list
        return equipment;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}