package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.EquipmentService;
import org.example.web.dto.Equipment;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "equipment")
public class EquipmentRentController {

    private final Logger logger = Logger.getLogger(EquipmentRentController.class);
    private EquipmentService equipmentService;
    //private User user;

    @Autowired
    public EquipmentRentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @GetMapping("/rent")
    public String equipment(@NotNull Model model) {
        logger.info("got equipment");
        model.addAttribute("equipment", new Equipment());
        model.addAttribute("equipmentList", equipmentService.getAllEquipment());
        return "equipment_rent";
    }

    @PostMapping("/results")
    public ModelAndView findEquipment(@RequestParam(value = "equipmentNameToFind") String equipmentNameToFind, Model model){
            if (equipmentService.findEquipmentByName(equipmentNameToFind)) {
                ModelAndView modelAndView = new ModelAndView();
                modelAndView.addObject("equipmentFound", equipmentService.getFoundEquipment()); // <- List
                modelAndView.setViewName("view_results_page"); // redirect:/filtered_equipment/found
                logger.info(modelAndView.getView());
                logger.info(modelAndView.toString());
                for (Equipment equipment : equipmentService.getFoundEquipment()) {
                    logger.info(equipment);
                }
                return modelAndView;
            } else {
                model.addAttribute("equipment", new Equipment());
                model.addAttribute("equipmentList", equipmentService.getAllEquipment());
                return new ModelAndView("equipment_rent");
            }
    }

    @PostMapping("/see_equipment")
    public String seeEquipment(@NotNull Equipment equipment) {
        return "redirect:/equipment/rent/" + equipment.getId();
    }

    @PostMapping("/set_fave")
    public void setFave(Equipment equipment) {
        equipmentService.setFave(equipment);
        // return "equipment_rent";
    }

    @GetMapping("/leasing")
    public String lease() {
        return "redirect:/lease";
    }
}