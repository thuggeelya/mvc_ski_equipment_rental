package org.example.web.dto;

import org.example.app.comparators.Comparators;

import java.util.*;

public class UserEquipment {

    private int id;
    private Map<Equipment, Integer> rentHistory = new TreeMap<>(Comparators.equipmentCostComparator);
    private Set<Equipment> leaseHistory = new TreeSet<>(Comparators.equipmentCostComparator);
    private Map<Equipment, Integer> onRentNow = new TreeMap<>(Comparators.equipmentCostComparator);
    private Set<Equipment> onLeaseNow = new TreeSet<>(Comparators.equipmentCostComparator);

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<Equipment, Integer> getRentHistory() {
        return rentHistory == null ? new HashMap<>() : rentHistory;
    }

    public void setRentHistory(Map<Equipment, Integer> rentHistory) {
        this.rentHistory = rentHistory;
    }
    public void addToRentHistory(Equipment equipment, int i) {
        rentHistory.put(equipment, i);
    }
    public void removeFromRentHistory(Equipment equipment) {
        rentHistory.remove(equipment);
    }

    public Set<Equipment> getLeaseHistory() {
        return leaseHistory;
    }

    public void setLeaseHistory(Set<Equipment> leaseHistory) {
        this.leaseHistory = leaseHistory;
    }
    public void addToLeaseHistory(Equipment equipment) {
        leaseHistory.add(equipment);
    }
    public void removeFromLeaseHistory(Equipment equipment) {
        leaseHistory.remove(equipment);
    }

    public Map<Equipment, Integer> getOnRentNow() {
        return onRentNow == null ? new HashMap<>() : onRentNow;
    }

    public void setOnRentNow(Map<Equipment, Integer> onRentNow) {
        this.onRentNow = onRentNow;
    }
    public void addToRentNow(Equipment equipment, int i) {
        onRentNow.put(equipment, i);
    }
    public void removeFromRentNow(Equipment equipment) {
        onRentNow.remove(equipment);
    }

    public Set<Equipment> getOnLeaseNow() {
        return onLeaseNow;
    }

    public void setOnLeaseNow(Set<Equipment> onLeaseNow) {
        this.onLeaseNow = onLeaseNow;
    }
    public void addToLeaseNow(Equipment equipment) {
        onLeaseNow.add(equipment);
    }
    public void removeFromLeaseNow(Equipment equipment) {
        onLeaseNow.remove(equipment);
    }

    @Override
    public String toString() {
        return "UserEquipment{" +
                "id=" + id +
                ", rentHistory=" + rentHistory +
                ", leaseHistory=" + leaseHistory +
                ", onRentNow=" + onRentNow +
                ", onLeaseNow=" + onLeaseNow +
                '}';
    }
}
