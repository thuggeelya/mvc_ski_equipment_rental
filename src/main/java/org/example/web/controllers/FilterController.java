package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.FilterService;
import org.example.web.dto.Equipment;
import org.example.web.dto.Message;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

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
    public String equipmentFound(@NotNull HttpServletRequest request, @NotNull Model model) {
        logger.info("got equipment found");

        model.addAttribute("message", new Message());

        Enumeration<String> attributesSession = request.getSession().getAttributeNames();
        logger.info("ATTRIBUTES IN SESSION:");
        while (attributesSession.hasMoreElements()) {
            String attribute = attributesSession.nextElement();
            logger.info("Session attribute: " + attribute);
        }

        return "view_results_page";
    }
}