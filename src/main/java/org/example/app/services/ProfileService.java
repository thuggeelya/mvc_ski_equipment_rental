package org.example.app.services;

import org.example.web.dto.Equipment;
import org.example.web.dto.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class ProfileService {
    private ProfileRepository profileRepository = new ProfileRepository();

    @Autowired
    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public boolean exit() {
        return profileRepository.exit();
    }

    public Equipment equipmentPage(Equipment equipment_rent, @NotNull User user, HttpServletRequest request) {
        return profileRepository.equipmentPage(equipment_rent, user, request);
    }
}