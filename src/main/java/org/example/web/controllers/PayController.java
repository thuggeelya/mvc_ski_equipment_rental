package org.example.web.controllers;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "equipment/rent")
public class PayController {

//    private ProfileService profileService;
//
//    @Autowired
//    public PayController(ProfileService profileService) {
//        this.profileService = profileService;
//    }

    @GetMapping("/pay")
    public String pay(@NotNull Model model) {
//        model.addAttribute("equipment_name", name);
        return "commit_rent_page";
    }

    @GetMapping("/profile")
    public String seeRentHistoryAtProfile() {
        return "redirect:/profile";
    }
}
