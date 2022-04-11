package org.example.app.services;

import org.example.web.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public boolean exit() {
        return profileRepository.exit();
    }
}