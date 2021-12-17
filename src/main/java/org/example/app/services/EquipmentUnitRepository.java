package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Equipment;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

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

    public Equipment findEquipmentByName(String name) {
        AtomicReference<Equipment> equipmentToFind = new AtomicReference<>(new Equipment());
        logger.info("DB:");
        List<Equipment> equipmentList = jdbcTemplate.query("SELECT * FROM equipment", (ResultSet rs, int rowNum) -> {
            Equipment equipment = new Equipment();
            equipment.setId(rs.getInt("id"));
            equipment.setName(rs.getString("name"));
            equipment.setFirmName(rs.getString("firm_name"));
            equipment.setCost(rs.getString("cost"));
            if (equipment.getName().equals(name)) {
                equipmentToFind.set(equipment);
            }
            logger.info("got equipment: " + equipment);
            return equipment;
        });

        return (equipmentToFind.get().getName() == null) ? null : equipmentToFind.get();
    }
}