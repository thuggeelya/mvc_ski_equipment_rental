package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.ProfileService;
import org.example.web.dto.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Enumeration;

@Controller
public class ProfileController {

    private final Logger logger = Logger.getLogger(ProfileController.class);
    private ProfileService profileService;

    private static ProfilePageVisitingCause profilePageVisitingCause =
            ProfilePageVisitingCause.SEE_EQUIPMENT; // default state

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    private static User getSessionUser(@NotNull HttpServletRequest request) {
        return (User) request.getSession().getAttribute("login_user");
    }

    @GetMapping("/profile")
    public String openProfile(@NotNull Model model, @NotNull HttpServletRequest request) {
        User user = getSessionUser(request);
        if (user == null) {
            logger.info("redirect to /login for authorization");
            return "redirect:/login";
        }

        model.addAttribute("message", new Message());
        request.getSession().setAttribute("isLease", false);

        logger.info("(USER): " + user);

        Enumeration<String> attributesSession = request.getSession().getAttributeNames();
        logger.info("\nATTRIBUTES IN SESSION");
        while (attributesSession.hasMoreElements()) {
            String attribute = attributesSession.nextElement();
            logger.info("Session attribute: " + attribute);
        }

        model.addAttribute("person", user.getPerson());
        model.addAttribute("rent_equipment", user.getUserEquipment().getRentHistory().keySet());
        model.addAttribute("lease_equipment", user.getUserEquipment().getLeaseHistory());
        model.addAttribute("user", user);
        model.addAttribute("isLease", true);

        Equipment newLeaseEquipment = (Equipment) request.getSession().getAttribute("equipment_to_lease");
        Equipment newRentEquipment = (Equipment) request.getSession().getAttribute("new_rent_equipment");

        if (newLeaseEquipment == null && newRentEquipment == null) {
            // get 1st tab checked
            model.addAttribute("isFirstTabCheckedAsDefault", true);
            return "profile";
        }

        logger.info("USER: " + user);

        if (newLeaseEquipment != null) { // then it's already in lease history (LeaseController.class)
            request.getSession().removeAttribute("equipment_to_lease");
        }
        if (newRentEquipment != null) {
            user.getUserEquipment().addToRentHistory(newRentEquipment, 1);
            user.getUserEquipment().addToRentNow(newRentEquipment, 1);
            request.getSession().setAttribute("time_rent_" + newRentEquipment.getId(), LocalDateTime.now());
            request.getSession().removeAttribute("new_rent_equipment");
        }
        request.getSession().setAttribute("login_user", user);

        // get 2nd tab checked
        model.addAttribute("isFirstTabCheckedAsDefault", false);

        return "profile";
    }

    @PostMapping("/save")
    public String saveProfileChanges(Person person, @NotNull HttpServletRequest request) {
        User user = getSessionUser(request);
        user.setPerson(person);
        request.getSession().setAttribute("login_user", user);
        profileService.saveProfileChanges(user);
        logger.info("updating profile page");
        return "redirect:/profile";
    }
}