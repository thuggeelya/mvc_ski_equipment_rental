package org.example.app.services;

public interface ProjectLeaseRepository<T, X> {
    X newLeaseEquipment(T user, X equipment);
}