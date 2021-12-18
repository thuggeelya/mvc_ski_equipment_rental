package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.EquipmentUnitService;
import org.example.web.dto.Equipment;
import org.example.web.dto.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "equipment")
public class EquipmentUnitController {

    private final Logger logger = Logger.getLogger(EquipmentUnitController.class);
    private EquipmentUnitService equipmentUnitService;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public EquipmentUnitController(EquipmentUnitService equipmentUnitService, NamedParameterJdbcTemplate jdbcTemplate) {
        this.equipmentUnitService = equipmentUnitService;
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/{name}")
    public String equipmentInfo(@PathVariable String name, @NotNull HttpServletRequest request, Model model) {
        User user = (User) request.getSession().getAttribute("login_user");
        logger.info("name is " + name);
        Map<Equipment, Boolean> map = equipmentUnitService.findEquipmentByName(name, request);
        Equipment equipment = (Equipment) map.keySet().toArray()[0];

        logger.info("this equipment is " + equipment);
        // if equipment comes from lease history (is_lease = true)
        if (map.get(equipment)) {
            equipment.setOwner(user);
            user.getUserEquipment().addToLeaseHistory(equipment);
            logger.info("adding to lease history: " + equipment);
            request.getSession().setAttribute("old_lease_equipment", equipment);
            request.getSession().setAttribute("is_lease", true);
            model.addAttribute("is_lease", true);
            model.addAttribute("equipment", equipment);

            return "equipment_unit";
        }

        boolean isOnRentNow = user.getUserEquipment().getRentHistory().contains(equipment);

        model.addAttribute("is_on_rent_now", isOnRentNow);
        if (isOnRentNow) {
            Integer hours = (Integer) request.getSession().getAttribute("rent_hours");
            model.addAttribute("hours", hours);
            LocalDateTime timeRentCurrentEquipment = (LocalDateTime) request.getSession().getAttribute("time_rent_" + equipment.getId());
            model.addAttribute("time_rent", timeRentCurrentEquipment.toString());
            model.addAttribute("is_rent_time_passed", LocalDateTime.now().isAfter(timeRentCurrentEquipment.plusHours(hours)));
        }
        request.getSession().setAttribute("old_lease_equipment", equipment); // not lease, but I need this to be old_lease_equipment
        request.getSession().setAttribute("is_lease", false);                                                       //
        model.addAttribute("is_lease", false);                                                    //
        model.addAttribute("equipment", equipment);                                                          //
                                                                                                                        //
        return "equipment_unit";                                                                                        //
    }                                                                                                                   //
                                                                                                                        //
    @PostMapping("/save_lease")                                                                                      //
    public String saveLease(Equipment equipment, @NotNull HttpServletRequest request) {                                 //
        User user = (User) request.getSession().getAttribute("login_user");                                          //
                                                                                                                        //
        boolean isLease = false;                                                                                        //
        Enumeration<String> attributesSession = request.getSession().getAttributeNames();                               //
        while (attributesSession.hasMoreElements()) {                                                                   //
            String attribute = attributesSession.nextElement();                                                         //
            if (attribute.equals("is_lease")) {                                                                         //
                isLease = (boolean) request.getSession().getAttribute("is_lease");                                   //
                logger.info("isLease = " + isLease);                                                                   //
            }                                                                                                         //
            logger.info("Session attribute: " + attribute);                                                          //
        }                                                                                                           //
                                                                                                                   //
        Equipment newEquipment = (Equipment) request.getSession().getAttribute("old_lease_equipment");          // that's it
        // re-save only cost, firmName, description if not null
        if (equipment.getCost() != null) {
            newEquipment.setCost(equipment.getCost());
        }
        if (equipment.getFirmName() != null) {
            newEquipment.setFirmName(equipment.getFirmName());
        }
        if (equipment.getDescription() != null) {
            newEquipment.setDescription(equipment.getDescription());
        }
        request.getSession().setAttribute("new_equipment", newEquipment);

        if (isLease) {

            logger.info("Lease History:");
            for (Equipment e : user.getUserEquipment().getLeaseHistory()) {
                logger.info(e);
            }

            user.getUserEquipment().removeFromLeaseHistory((Equipment) request.getSession().getAttribute("old_lease_equipment"));
            logger.info("removing " + request.getSession().getAttribute("old_lease_equipment"));

            // to lease history
            user.getUserEquipment().addToLeaseHistory(newEquipment);
            logger.info("adding " + newEquipment);

            logger.info("Lease History:");
            for (Equipment e : user.getUserEquipment().getLeaseHistory()) {
                logger.info(e);
            }

            // to db by id
            Map<String, Object> params = new HashMap<>();
            params.put("id", newEquipment.getId());
            params.put("firm_name", newEquipment.getFirmName());
            params.put("cost", newEquipment.getCost());
            params.put("description", newEquipment.getDescription());
            String exp = "UPDATE equipment SET firm_name = :firm_name, cost = :cost, description = :description WHERE id = :id";
            jdbcTemplate.update(exp, params);
//        jdbcTemplate.update("INSERT INTO equipment(id,name,firm_name,cost,description) VALUES (:id,:name,:firm_name,:cost,:description)", params);

            return "redirect:/profile";
        } else {
            logger.info("go rent " + newEquipment.getName());
            request.getSession().setAttribute("new_rent_equipment", newEquipment);
            return "redirect:/equipment/rent/" + newEquipment.getName();
        }
    }

    @GetMapping("/back")
    public String backToProfile() {
        return "redirect:/profile";
    }
}