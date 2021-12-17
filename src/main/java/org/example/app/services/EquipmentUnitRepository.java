package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Equipment;
import org.example.web.dto.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@ComponentScan(value = "jdbc_template")
public class EquipmentUnitRepository implements ApplicationContextAware {

    private Logger logger = Logger.getLogger(EquipmentUnitRepository.class);
    private ApplicationContext applicationContext;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public EquipmentUnitRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public Map<Equipment, Boolean> findEquipmentByName(String name, @NotNull HttpServletRequest request) {
        logger.info("findEquipmentByName():");
        User user = (User) request.getSession().getAttribute("login_user");
        if (user == null) {
            user = new User();
            logger.info("user is null");
            return null;
        }
        logger.info("user is not null");

        final Equipment[] searchedEquipment = {null};

        Map<Equipment, Boolean> map = new HashMap<>();

        for (Equipment equipment : user.getUserEquipment().getLeaseHistory()) {
            if (equipment.getName().equals(name)) {
                map.put(equipment, true); // isLease = true
                logger.info("equipment is in lease history: " + equipment);
                return map;
            }
        }

        jdbcTemplate.query("SELECT * FROM equipment", (ResultSet rs, int rowNum) -> {
            if (rs.getString("name").equals(name)) {
                searchedEquipment[0] = new Equipment();
                searchedEquipment[0].setId(rs.getInt("id"));
                searchedEquipment[0].setName(rs.getString("name"));
                searchedEquipment[0].setFirmName(rs.getString("firm_name"));
                searchedEquipment[0].setCost(rs.getString("cost"));
                searchedEquipment[0].setDescription(rs.getString("description"));
                searchedEquipment[0].setAvailable(rs.getBoolean("available"));
                searchedEquipment[0].setAvailableLeft(rs.getInt("available_left"));
                map.put(searchedEquipment[0], false);
                logger.info("equipment is in db: " + searchedEquipment[0]);
            }
            return searchedEquipment[0];
        });

        if (searchedEquipment[0] != null) {
            return map;
        }

        logger.info("equipment is null");
        map.put(null, false);
        return null;
    }
}