package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.EquipmentRentService;
import org.example.web.dto.Equipment;
import org.example.web.dto.Message;
import org.example.web.dto.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping(value = "equipment")
public class EquipmentRentController {

    private final Logger logger = Logger.getLogger(EquipmentRentController.class);
    private EquipmentRentService equipmentRentService;

    @Autowired
    public EquipmentRentController(EquipmentRentService equipmentService) {
        this.equipmentRentService = equipmentService;
    }

    @GetMapping("/rent")
    public String equipment(@NotNull Model model) {
        logger.info("got equipment");
        model.addAttribute("equipmentList", equipmentRentService.getAllEquipment());
        model.addAttribute("cartList", new ArrayList<String>());
        model.addAttribute("numList", new ArrayList<Integer>());
        model.addAttribute("message", new Message());
        return "equipment_rent";
    }

    @PostMapping("/results")
    public ModelAndView findEquipment(@RequestParam(value = "equipmentNameToFind") String equipmentNameToFind, Model model){
        logger.info(equipmentNameToFind);
            if (equipmentRentService.findEquipmentByName(equipmentNameToFind)) {
                ModelAndView modelAndView = new ModelAndView();
                modelAndView.addObject("equipmentFound", equipmentRentService.getFoundEquipment()); // <- List
                modelAndView.setViewName("view_results_page"); // redirect:/filtered_equipment/found
                model.addAttribute("message", new Message());

                for (Equipment equipment : equipmentRentService.getFoundEquipment()) {
                    logger.info(equipment);
                }
                return modelAndView;
            } else {
                model.addAttribute("equipment", new Equipment());
                model.addAttribute("equipmentList", equipmentRentService.getAllEquipment());
                model.addAttribute("cartList", new ArrayList<String>());
                model.addAttribute("numList", new ArrayList<Integer>());
                model.addAttribute("message", new Message());
                return new ModelAndView("equipment_rent");
            }
    }

    @PostMapping("/add_message")
    public String addMessage(@ModelAttribute("message") Message message) {
        logger.info("message: " + message);
        equipmentRentService.addMessage(message);
        return "redirect:/equipment/rent";
    }

    @PostMapping("/buy")
    public String buyNull() {
        return "redirect:/equipment/rent";
    }

    @GetMapping("/buy/{items}")
    public String buy(@NotNull @PathVariable String[] items, @NotNull HttpServletRequest request) {
        List<String> cartList = new ArrayList<>();
        List<Integer> numList = new ArrayList<>();

        for (int i = 0; i < items.length; i++) {
            items[i] = items[i].replace("%20", " ");
            if (i % 2 == 0) {
                cartList.add(items[i]);
            } else {
                numList.add(Integer.parseInt(items[i]));
            }
        }

        logger.info("cartList: " + cartList);
        logger.info("numList: " + numList);
        User user = (User) request.getSession().getAttribute("login_user");

        if (user == null) {
            return "redirect:/equipment/rent";
        }

        List<String> noZerosCartList = new ArrayList<>();
        for (int i = 0; i < Objects.requireNonNull(numList).size(); i++) {
            if(numList.get(i) > 0) {
                noZerosCartList.add(cartList.get(i));
                user.getUserEquipment()
                        .addToRentHistory(equipmentRentService.getEquipmentByName(cartList.get(i)), numList.get(i));
            }
        }

        if (noZerosCartList.isEmpty()) {
            return "redirect:/equipment/rent";
        }
        equipmentRentService.saveUserEquipment(user, noZerosCartList);
        return "commit_rent_page";
    }

    @PostMapping("/set_fave")
    public void setFave(Equipment equipment) {
        equipmentRentService.setFave(equipment);
        // return "equipment_rent";
    }
}