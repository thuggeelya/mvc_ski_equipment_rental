package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.EquipmentUnitService;
import org.example.web.dto.Equipment;
import org.example.web.dto.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "equipment")
public class EquipmentUnitController {

    private final Logger logger = Logger.getLogger(EquipmentUnitController.class);
    private EquipmentUnitService equipmentUnitService;
    private Equipment equipment;

    @Autowired
    public EquipmentUnitController (EquipmentUnitService equipmentUnitService) {
        this.equipmentUnitService = equipmentUnitService;
    }

    @GetMapping("/{name}")
    public String equipmentInfo(@PathVariable String name, @NotNull HttpServletRequest request, Model model) {
        User user = (User) request.getSession().getAttribute("login_user");
        equipment = equipmentUnitService.findEquipmentByName(name);
        if (equipment.getOwner() == null) {
            equipment.setOwner(user);
            user.getUserEquipment().addToLeaseHistory(equipment);
        }
        logger.info("this equipment is " + equipment);

        model.addAttribute("equipment", equipment);

        return "equipment_unit";
    }
}