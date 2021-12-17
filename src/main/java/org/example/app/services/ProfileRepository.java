package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Equipment;
import org.example.web.dto.User;
import org.example.web.dto.UserEquipment;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;

@Repository
public class ProfileRepository implements ApplicationContextAware {

    private final Logger logger = Logger.getLogger(ProfileRepository.class);
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public boolean exit() {
        return false;
    }

    public Equipment equipmentPage(Equipment equipment_rent, @NotNull User user, HttpServletRequest request) {
        UserEquipment userEquipment = user.getUserEquipment();

        for (Equipment e : userEquipment.getRentHistory()) {
            if (e.getName().equals(equipment_rent.getName())) {
                request.getSession().setAttribute("equipmentToGoTo", e);
                return e;
            }
        }

        for (Equipment e : userEquipment.getLeaseHistory()) {
            if (e.getName().equals(equipment_rent.getName())) {
                request.getSession().setAttribute("equipmentToGoTo", e);
                return e;
            }
        }

        request.getSession().setAttribute("equipmentToGoTo", equipment_rent);
        return equipment_rent;
    }
}