package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.ProfileService;
import org.example.web.dto.Equipment;
import org.example.web.dto.Person;
import org.example.web.dto.ProfilePageVisitingCause;
import org.example.web.dto.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Controller
public class ProfileController {

    private final Logger logger = Logger.getLogger(ProfileController.class);
    private ProfileService profileService;

    private static ProfilePageVisitingCause profilePageVisitingCause = ProfilePageVisitingCause.SEE_EQUIPMENT; // default state

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/profile")
    public String openProfile(Model model, @NotNull HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("login_user");

        if (user == null) {
            logger.info("redirect to /login for authorization");
            return "redirect:/login";
        }
        logger.info("(USER): " + user);

        Enumeration<String> attributesSession = request.getSession().getAttributeNames();
        while (attributesSession.hasMoreElements()) {
            String attribute = attributesSession.nextElement();
            logger.info("Session attribute: " + attribute);
        }

        model.addAttribute("person", user.getPerson());
        model.addAttribute("user", user);
        Equipment newLeaseEquipment = (Equipment) request.getSession().getAttribute("equipment_to_lease");

        if (newLeaseEquipment == null) {
            model.addAttribute("isFirstTabCheckedAsDefault", true);
            profilePageVisitingCause = ProfilePageVisitingCause.FILL_IN_PERSONAL_DETAILS;
            return "profile";
        }

        profilePageVisitingCause = ProfilePageVisitingCause.SEE_EQUIPMENT;

        user.getUserEquipment().addToLeaseHistory(newLeaseEquipment);
        logger.info("PERSON: " + user.getPerson() + " " + user.getUserEquipment().getLeaseHistory());

        request.getSession().removeAttribute("equipment_to_lease");
        request.getSession().setAttribute("login_user", user);

        // get 2nd tab checked
        model.addAttribute("isFirstTabCheckedAsDefault", false);

        return "profile";
    }

    @PostMapping("/save")
    public String saveProfileChanges(Person person, @NotNull HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("login_user");
        user.setPerson(person);
        request.getSession().setAttribute("login_user", user);
        if (profilePageVisitingCause == ProfilePageVisitingCause.SEE_EQUIPMENT) {
            return "profile";
        } else {
            return "redirect:/lease";
        }
    }
}