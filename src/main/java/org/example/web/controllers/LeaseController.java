package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.LeaseService;
import org.example.web.dto.Equipment;
import org.example.web.dto.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LeaseController {

    private final Logger logger = Logger.getLogger(LeaseController.class);
    private LeaseService leaseService;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public LeaseController(LeaseService leaseService, NamedParameterJdbcTemplate jdbcTemplate) {
        this.leaseService = leaseService;
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/lease")
    public String lease(@NotNull Model model, @NotNull HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("login_user");
        logger.info("USER: " + user);

        if (    user == null ||
                user.getPerson().getName() == null ||
                user.getPerson().getLastName() == null ||
                user.getPerson().getPhone() == null    ) { // means that no info at PROFILE - need to fill it in or to auth
            return "redirect:/profile";
        }

        model.addAttribute("equipment", new Equipment());
        logger.info("got leasing");
        return "leasing";
    }

    @PostMapping("/leasing_form")
    public String submitLeasing(Equipment equipment, @NotNull HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("login_user");

        Enumeration<String> attributesSession = request.getSession().getAttributeNames();
        while (attributesSession.hasMoreElements()) {
            String attribute = attributesSession.nextElement();
            logger.info("Session attribute: " + attribute);
        }

        Equipment equipmentToLease = leaseService.newLeaseEquipment(user, equipment); // sets as its owner - session user
        equipmentToLease.setId(equipmentToLease.hashCode());
        equipmentToLease.setAvailable(true);
        equipmentToLease.setAvailableLeft(1);

        user.getUserEquipment().addToLeaseHistory(equipmentToLease);
        user.getUserEquipment().addToLeaseNow(equipmentToLease);

        request.getSession().setAttribute("equipment_to_lease", equipmentToLease);
        logger.info("got new leasing equipment: " + equipmentToLease);

        // to db
        Map<String, Object> params = new HashMap<>();
        params.put("id", equipmentToLease.getId());
        params.put ("name", equipmentToLease.getName());
        params.put ("firm_name", equipmentToLease.getFirmName());
        params.put("cost", equipmentToLease.getCost());
        params.put("description", equipmentToLease.getDescription());
        params.put("owner", equipmentToLease.getOwner().getId());
        params.put("available", equipmentToLease.isAvailable());
        params.put("available_left", equipmentToLease.getAvailableLeft());

        String exp = "INSERT INTO equipment(id,name,firm_name,cost,description,owner,available,available_left)" +
                " VALUES(:id,:name,:firm_name,:cost,:description,:owner,:available,:available_left)";
        jdbcTemplate.update(exp, params);

        return "redirect:/profile";
    }
}