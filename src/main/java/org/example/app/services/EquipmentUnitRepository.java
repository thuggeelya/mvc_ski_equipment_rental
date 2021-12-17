package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Equipment;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

        if (equipmentToFind.get().getName() == null) {
            equipmentToFind.get().setId(equipmentToFind.get().hashCode());
            equipmentToFind.get().setName(name);
            equipmentToFind.get().setFirmName("CUSTOM");
            equipmentToFind.get().setCost("0");
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("id", equipmentToFind.get().getId());
            paramMap.put ("name", name);
            paramMap.put ("firm_name", "CUSTOM");
            paramMap.put("cost", "0");
            jdbcTemplate.update("INSERT INTO equipment(id,name,firm_name,cost) VALUES (:id,:name,:firm_name,:cost)", paramMap);
        }

        return (equipmentToFind.get().getName() == null) ? null : equipmentToFind.get();
    }
}