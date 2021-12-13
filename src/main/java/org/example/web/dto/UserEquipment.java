package org.example.web.dto;

import org.example.app.comparators.Comparators;

import java.util.Set;
import java.util.TreeSet;

public class UserEquipment {

    private int id;
    private Set<Equipment> rentHistory = new TreeSet<>(Comparators.equipmentCostComparator);
    private Set<Equipment> leaseHistory = new TreeSet<>(Comparators.equipmentCostComparator);
    private Set<Equipment> onRentNow = new TreeSet<>(Comparators.equipmentCostComparator);
    private Set<Equipment> onLeaseNow = new TreeSet<>(Comparators.equipmentCostComparator);

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Equipment> getRentHistory() {
        return rentHistory;
    }

    public void setRentHistory(Set<Equipment> rentHistory) {
        this.rentHistory = rentHistory;
    }
    public void addToRentHistory(Equipment equipment) {
        rentHistory.add(equipment);
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

    public Set<Equipment> getOnRentNow() {
        return onRentNow;
    }

    public void setOnRentNow(Set<Equipment> onRentNow) {
        this.onRentNow = onRentNow;
    }
    public void addToRentNow(Equipment equipment) {
        onRentNow.add(equipment);
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
