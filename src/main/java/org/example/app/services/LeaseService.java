package org.example.app.services;

import org.example.web.dto.Equipment;
import org.example.web.dto.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaseService {
    private final ProjectLeaseRepository<User, Equipment> leaseRepo;

    @Autowired
    public LeaseService(LeaseRepository leaseRepo) {
        this.leaseRepo = leaseRepo;
    }

    public Equipment newLeaseEquipment(User user, @NotNull Equipment equipment) {
        return leaseRepo.newLeaseEquipment(user, equipment);
    }
}