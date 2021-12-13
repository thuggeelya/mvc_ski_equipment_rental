package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;

@Repository
public class ProfileRepository implements ApplicationContextAware {

    private final Logger logger = Logger.getLogger(ProfileRepository.class);
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public boolean exit() {
        return false;
    }
}