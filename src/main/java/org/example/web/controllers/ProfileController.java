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

import javax.servlet.http.HttpServletRequest;
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
    public String openProfile(Model model, @NotNull HttpServletRequest request) {
        User user = getSessionUser(request);

        if (user == null) {
            logger.info("redirect to /login for authorization");
            return "redirect:/login";
        }
        logger.info("(USER): " + user);

        Enumeration<String> attributesSession = request.getSession().getAttributeNames();
        logger.info("\nATTRIBUTES IN SESSION");
        while (attributesSession.hasMoreElements()) {
            String attribute = attributesSession.nextElement();
            logger.info("Session attribute: " + attribute);
        }

        model.addAttribute("person", user.getPerson());
        model.addAttribute("rent_equipment", user.getUserEquipment().getRentHistory());
        model.addAttribute("lease_equipment", user.getUserEquipment().getLeaseHistory());
        model.addAttribute("user", user);

        Equipment newLeaseEquipment = (Equipment) request.getSession().getAttribute("equipment_to_lease");

        if (newLeaseEquipment == null) {
            // get 1st tab checked
            model.addAttribute("isFirstTabCheckedAsDefault", true);
            profilePageVisitingCause = ProfilePageVisitingCause.FILL_IN_PERSONAL_DETAILS;
            return "profile";
        }

        profilePageVisitingCause = ProfilePageVisitingCause.SEE_EQUIPMENT;

        user.getUserEquipment().addToLeaseHistory(newLeaseEquipment);
        logger.info("USER: " + user);
        logger.info("PERSON: " + user.getPerson() + "\n" + user.getUserEquipment().getLeaseHistory());

        request.getSession().removeAttribute("equipment_to_lease");
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
        if (profilePageVisitingCause == ProfilePageVisitingCause.SEE_EQUIPMENT) {
            logger.info("updating profile page");
            return "redirect:/profile";
        } else {
            logger.info("leaving profile page");
            profilePageVisitingCause = ProfilePageVisitingCause.SEE_EQUIPMENT; // default state
            return "redirect:/lease";
        }
    }

//    @PostMapping("/equipment_unit")
//    public void equipmentPage() {
//        logger.info("redirect to equipment page");
//    }
}