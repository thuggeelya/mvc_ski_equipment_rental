package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.LeaseService;
import org.example.web.dto.Equipment;
import org.example.web.dto.Person;
import org.example.web.dto.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

@Controller
public class LeaseController {

    private final Logger logger = Logger.getLogger(LeaseController.class);
    private LeaseService leaseService;

    @Autowired
    public LeaseController(LeaseService leaseService) {
        this.leaseService = leaseService;
    }

    @GetMapping("/lease")
    public String lease(@NotNull Model model, @NotNull HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("login_user");
        logger.info("USER: " + user);

        if (user.getPerson().getId() == -1) { // means that no info at PROFILE - need to fill in
            return "redirect:/profile";
        }

        model.addAttribute("equipment", new Equipment());
        logger.info("got leasing");
        return "leasing";
    }

    @PostMapping("/account")
    public String submitLeasing(Equipment equipment, @NotNull HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("login_user");

        Enumeration<String> attributesSession = request.getSession().getAttributeNames();
        while (attributesSession.hasMoreElements()) {
            String attribute = attributesSession.nextElement();
            logger.info("Session attribute: " + attribute);
        }

        Equipment equipmentToLease = leaseService.newLeaseEquipment(user, equipment);
        equipmentToLease.setId(equipmentToLease.hashCode());
        request.getSession().setAttribute("equipment_to_lease", equipmentToLease);
        logger.info("got new leasing equipment: " + equipmentToLease);
        return "redirect:/profile";
    }
}