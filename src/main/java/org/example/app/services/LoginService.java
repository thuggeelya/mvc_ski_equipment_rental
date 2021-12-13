package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private Logger logger = Logger.getLogger(LoginService.class);

    public boolean authenticate(@NotNull User loginForm) {
        logger.info("try auth with user-form: " + loginForm);
        return loginForm.getEmail().equals("thuggeelya@mail.ru") && loginForm.getPassword().equals("123");
    }
}
