package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.LeaseService;
import org.example.web.dto.Equipment;
import org.example.web.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LeaseController {

    private final Logger logger = Logger.getLogger(LeaseController.class);
    private LeaseService leaseService;

    @Autowired
    public LeaseController(LeaseService leaseService) {
        this.leaseService = leaseService;
    }

    @GetMapping("/lease")
    public String lease(Model model) {
        model.addAttribute("equipment", new Equipment());
        model.addAttribute("user", new User());
        logger.info("got leasing");
        return "leasing";
    }

    @PostMapping("/account")
    public ModelAndView submitLeasing(User user, Equipment equipment) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("equipmentToLease", leaseService.newLeaseEquipment(user, equipment)); // <- List
        modelAndView.setViewName("profile");
        return modelAndView;
    }
}