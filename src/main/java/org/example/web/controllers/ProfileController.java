package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.ProfileService;
import org.example.web.dto.Equipment;
import org.example.web.dto.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Controller
// @RequestMapping(value = "/profile")
public class ProfileController {

    private final Logger logger = Logger.getLogger(ProfileController.class);
    private ProfileService profileService;
    private User user;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/profile")
    public String openProfile(Model model, @NotNull HttpServletRequest request) {
        Enumeration<String> attributesSession = request.getSession().getAttributeNames();
        while (attributesSession.hasMoreElements()) {
            String attribute = attributesSession.nextElement();
            logger.info("Session attribute: " + attribute);
        }
        this.user = (User) request.getSession().getAttribute("login_user");
        logger.info("USER: " + this.user);
        if (user == null) {
            return "redirect:/login";
        }
        Equipment newLeaseEquipment = (Equipment) request.getSession().getAttribute("equipment_to_lease");
        this.user.getPerson().addToLeaseHistory(newLeaseEquipment);
        logger.info("PERSON: " + this.user.getPerson() + " "+ this.user.getPerson().getLeaseHistory());

        return "profile";
    }
}