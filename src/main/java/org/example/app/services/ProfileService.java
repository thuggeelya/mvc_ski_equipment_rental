package org.example.app.services;

import org.example.web.dto.Equipment;
import org.example.web.dto.Person;
import org.example.web.dto.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ProfileService {
    private ProfileRepository profileRepository;

    @Autowired
    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public void saveProfileChanges(User user) {
        profileRepository.saveProfileChanges(user);
    }

    public Person getPerson(User user) {
        return profileRepository.getPerson(user);
    }

    public Set<Equipment> getUserEquipment(@NotNull User user) {
        Set<Equipment> equipment = profileRepository.getUserEquipment(user);
        return equipment;
    }

    public boolean exit() {
        return profileRepository.exit();
    }
}