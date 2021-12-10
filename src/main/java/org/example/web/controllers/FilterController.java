package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.FilterService;
import org.example.web.dto.Equipment;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "filtered_equipment")
public class FilterController {

    private Logger logger = Logger.getLogger(FilterController.class);
    private FilterService filterService;

    @Autowired
    public FilterController(FilterService filterService) {
        this.filterService = filterService;
    }

    @GetMapping("/found")
    public String equipmentFound() {
        logger.info("got equipment found");
        return "view_results_page";
    }

    @PostMapping("/see_results")
    public String seeEquipment(@NotNull Equipment equipment) {
        return "redirect:/equipment/rent/" + equipment.getId();
    }
}