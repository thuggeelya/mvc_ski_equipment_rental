package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Equipment;
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
import java.util.HashSet;
import java.util.Set;

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
        logger.info("person to update: " + person);
        String exp = "UPDATE person SET name=:name,lastname=:lastname,age=:age,phone=:phone WHERE id=:id";
        jdbcTemplate.update(exp, new MapSqlParameterSource()
                .addValue("id", person.getId())
                .addValue("name", person.getName())
                .addValue("lastname", person.getLastName())
                .addValue("age", person.getAge())
                .addValue("phone", person.getPhone()));
    }

    public boolean exit() {
        return false;
    }
}