package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.LeaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class LeaseController {

    private final Logger logger = Logger.getLogger(LeaseController.class);
    private LeaseService leaseService;
    //private Model model;

    @Autowired
    public LeaseController(LeaseService leaseService) {
        this.leaseService = leaseService;
    }

    //
}