package org.example.web.dto;

import java.util.Set;
import java.util.TreeSet;

public class Person {

    private int id;
    private String name;
    private String lastName;
    private int age;
    private String phone;
    private Set<Equipment> rentHistory = new TreeSet<>();
    private Set<Equipment> leaseHistory = new TreeSet<>();
    private Set<Equipment> onRentNow = new TreeSet<>();
    private Set<Equipment> onLeaseNow = new TreeSet<>();

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                '}';
    }
}