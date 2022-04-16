package org.example.app.services;

import org.example.web.dto.Equipment;
import org.example.web.dto.Person;
import org.example.web.dto.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

@Service
public class AuthorizedUser {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public AuthorizedUser(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void getAuthorizedUser(@NotNull User user) {
        user.setId(getUserByEmail(user.getEmail()).getId());
        user.setPerson(getPerson(user));
        getUserEquipment(user).forEach(e -> user.getUserEquipment().addToRentHistory(e, 1));
    }

    public User getUserByEmail(String email) {
        User user = new User();
        jdbcTemplate.query("SELECT * FROM users_table", (ResultSet rs, int rowNum) -> {
            if (rs.getString("email").equals(email)) {
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
            }
            return user;
        });
        return user;
    }

    public Person getPerson(User user) {
        Person person = new Person();
        jdbcTemplate.query("SELECT * FROM person", (ResultSet rs, int rowNum) -> {
            if (rs.getInt("id") == user.getId()) {
                person.setName(rs.getString("name"));
                person.setLastName(rs.getString("lastname"));
                person.setAge(String.valueOf(rs.getInt("age")));
                person.setPhone(rs.getString("phone"));
            }
            return person;
        });
        return person;
    }

    public Set<Equipment> getUserEquipment(User user) {
        Set<Equipment> equipmentSet = new HashSet<>();
        jdbcTemplate.query("SELECT * FROM users_equipment", (ResultSet rs, int rowNum) -> {
            Equipment e = new Equipment();
            if (rs.getInt("id") == user.getId()) {
                e = getEquipmentById(rs.getInt("eq_id"));
                equipmentSet.add(e);
            }
            return e;
        });
        return equipmentSet;
    }

    public Equipment getEquipmentById(int id) {
        Equipment e = new Equipment();
        jdbcTemplate.query("SELECT * FROM equipment", (ResultSet rs, int rowNum) -> {
            if (rs.getInt("id") == id) {
                e.setId(id);
                e.setName(rs.getString("name"));
                e.setFirmName(rs.getString("firm_name"));
                e.setCost(String.valueOf(rs.getInt("cost")));
                e.setDescription(rs.getString("description"));
                e.setAvailable(rs.getBoolean("available"));
                e.setAvailableLeft(rs.getInt("available_left"));
            }
            return e;
        });
        return e;
    }
}
