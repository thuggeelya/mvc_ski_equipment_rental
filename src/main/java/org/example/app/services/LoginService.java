package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Service
@ComponentScan(value = "jdbc_template")
public class LoginService {

    private Logger logger = Logger.getLogger(LoginService.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public LoginService(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private User isRegistered(User loginForm) {
        AtomicReference<User> regUser = new AtomicReference<>(loginForm);

        jdbcTemplate.query("SELECT * FROM users_table", (ResultSet rs, int rowNum) -> {
            User user = new User();
            if (rs.getString("email").equals(loginForm.getEmail())) {
                logger.info("user " + loginForm.getEmail() + " is registered");
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

        User user = isRegistered(loginForm);

        return loginForm.getPassword().equals(user.getPassword());
    }

    public boolean register(@NotNull User loginForm) {
        logger.info("register with user-form: " + loginForm);

        User user = isRegistered(loginForm);
        if (loginForm.getEmail().equals(user.getEmail()) && loginForm.getPassword().equals(user.getPassword())) {
            return true;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("id", loginForm.hashCode());
        params.put("email", loginForm.getEmail());
        params.put("password", loginForm.getPassword());
        String exp = "INSERT INTO users_table(id,email,password) VALUES (:id,:email,:password)";
        jdbcTemplate.update(exp, params);

        return false;
    }
}
