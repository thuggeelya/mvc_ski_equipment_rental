package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Equipment;
import org.example.web.dto.Person;
import org.example.web.dto.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Service
@ComponentScan(value = "jdbc_template")
public class LoginService {

    private Logger logger = Logger.getLogger(LoginService.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public LoginService(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private User isRegistered(String email) {
        AtomicReference<User> regUser = new AtomicReference<>(null);

        jdbcTemplate.query("SELECT * FROM users_table", (ResultSet rs, int rowNum) -> {
            User user = new User();
            if (rs.getString("email").equals(email)) {
                logger.info("user " + email + " is registered");
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                regUser.set(user);
            }
            return user;
        });

        return regUser.get();
    }

    public boolean authenticate(@NotNull User loginForm) {
        logger.info("try auth with user-form: " + loginForm);

        User user = isRegistered(loginForm.getEmail());
        if (user == null) {
            return false;
        }

        return loginForm.getPassword().equals(user.getPassword());
    }

    public boolean register(@NotNull User loginForm) {
        logger.info("register with user-form: " + loginForm);

        User user = isRegistered(loginForm.getEmail());
        if (user == null) {
            Map<String, Object> params = new HashMap<>();
            params.put("id", loginForm.hashCode());
            params.put("email", loginForm.getEmail());
            params.put("password", loginForm.getPassword());
            String exp = "INSERT INTO users_table(id,email,password) VALUES (:id,:email,:password)";
            jdbcTemplate.update(exp, params);
            exp = "INSERT INTO person(id,name,lastname,age,phone) VALUES (:id,null,null,null,null)";
            jdbcTemplate.update(exp, params);
            return false;
        }
        return true;
    }

    public User getAuthorizedUser(@NotNull User loginForm) {
        User user = isRegistered(loginForm.getEmail());
        logger.info("id: " + user.getId());
        user.setPerson(getPerson(user));
        getUserEquipment(user).forEach(e -> user.getUserEquipment().addToRentHistory(e, 1));
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
