package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Person;
import org.example.web.dto.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Repository
public class ProfileRepository implements ApplicationContextAware {

    private final Logger logger = Logger.getLogger(ProfileRepository.class);
    private ApplicationContext applicationContext;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ProfileRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void saveProfileChanges(@NotNull User user) {
        Person person = user.getPerson();
        String exp = "UPDATE person SET name=:name, lastName=:lastname, age=:age, phone=:phone WHERE id=:id";
        jdbcTemplate.update(exp, new MapSqlParameterSource()
                .addValue("name", person.getName())
                .addValue("lastName", person.getLastName())
                .addValue("age", person.getAge())
                .addValue("phone", person.getPhone()));
    }

    public boolean exit() {
        return false;
    }
}