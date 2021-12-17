package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.RentService;
import org.example.web.dto.Equipment;
import org.example.web.dto.Person;
import org.example.web.dto.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
        Equipment equipment = (Equipment) request.getSession().getAttribute("new_equipment");

        if (equipment == null) {
            equipment = (Equipment) rentService.findEquipmentByName(name, request).keySet().toArray()[0];
        }

        model.addAttribute("equipment", equipment);
        model.addAttribute("user", user);
        model.addAttribute("person", user.getPerson());
        return "rent_page";
    }

    @PostMapping("/commit")
    public String commitRent(@NotNull HttpServletRequest request, @NotNull Person person) {
        User loginUser = (User) request.getSession().getAttribute("login_user");
        if (person.getName() != null) {
            loginUser.getPerson().setName(person.getName());
        }
        if (person.getLastName() != null) {
            loginUser.getPerson().setLastName(person.getLastName());
        }
        if (person.getPhone() != null) {
            loginUser.getPerson().setPhone(person.getPhone());
        }
        request.getSession().setAttribute("login_user", loginUser);
        return "commit_rent_page";
    }
}