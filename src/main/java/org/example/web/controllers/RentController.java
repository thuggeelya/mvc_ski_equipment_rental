package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.RentService;
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
@RequestMapping(value = "equipment/rent")
public class RentController {
    private final Logger logger = Logger.getLogger(EquipmentUnitController.class);
    private RentService rentService;

    @Autowired
    public RentController(RentService rentService) {
        this.rentService = rentService;
    }

    @GetMapping("/{name}")
    public String rent(@PathVariable String name, @NotNull HttpServletRequest request, @NotNull Model model) {
        logger.info("renting process of " + name);
        User user = (User) request.getSession().getAttribute("login_user");
        Equipment equipment = rentService.findEquipmentByName(name);
        model.addAttribute("equipment", equipment);
        model.addAttribute("user", user);
        model.addAttribute("person", user.getPerson());
        return "rent_page";
    }
}