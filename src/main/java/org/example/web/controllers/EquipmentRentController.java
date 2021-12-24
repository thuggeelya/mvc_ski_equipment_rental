package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.EquipmentRentService;
import org.example.web.dto.Equipment;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Controller
@RequestMapping(value = "equipment")
public class EquipmentRentController {

    private final Logger logger = Logger.getLogger(EquipmentRentController.class);
    private EquipmentRentService equipmentRentService;

    @Autowired
    public EquipmentRentController(EquipmentRentService equipmentService) {
        this.equipmentRentService = equipmentService;
    }

    @GetMapping("/rent")
    public String equipment(@NotNull Model model, @NotNull HttpServletRequest request) {
        logger.info("got equipment");

        Enumeration<String> attributesSession = request.getSession().getAttributeNames();
        logger.info("ATTRIBUTES IN SESSION:");
        while (attributesSession.hasMoreElements()) {
            String attribute = attributesSession.nextElement();
            logger.info("Session attribute: " + attribute);
        }

        model.addAttribute("equipment", new Equipment());
        model.addAttribute("equipmentList", equipmentRentService.getAllEquipment());
        return "equipment_rent";
    }

    @PostMapping("/results")
    public ModelAndView findEquipment(@RequestParam(value = "equipmentNameToFind") String equipmentNameToFind, Model model){
        logger.info(equipmentNameToFind);
            if (equipmentRentService.findEquipmentByName(equipmentNameToFind)) {
                ModelAndView modelAndView = new ModelAndView();
                modelAndView.addObject("equipmentFound", equipmentRentService.getFoundEquipment()); // <- List
                modelAndView.setViewName("view_results_page"); // redirect:/filtered_equipment/found

                for (Equipment equipment : equipmentRentService.getFoundEquipment()) {
                    logger.info(equipment);
                }
                return modelAndView;
            } else {
                model.addAttribute("equipment", new Equipment());
                model.addAttribute("equipmentList", equipmentRentService.getAllEquipment());
                return new ModelAndView("equipment_rent");
            }
    }

    @PostMapping("/set_fave")
    public void setFave(Equipment equipment) {
        equipmentRentService.setFave(equipment);
        // return "equipment_rent";
    }
}