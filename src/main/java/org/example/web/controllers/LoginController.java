package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.exceptions.MyLoginException;
import org.example.app.services.LoginService;
import org.example.web.dto.LoginForm;
import org.example.web.dto.Person;
import org.example.web.dto.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/login")
// @SessionAttributes(value = "login_user")
public class LoginController {

    private final Logger logger = Logger.getLogger(LoginController.class);
    private LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService){
        this.loginService = loginService;
    }

    @GetMapping
    public String login(@NotNull Model model) {
        logger.info("GET /login returns login_page.html");
        model.addAttribute("user", new User());
        return "login_page";
    }

    @PostMapping("/auth")
    public String authenticate(@ModelAttribute("login_user") User user, HttpServletRequest request) throws MyLoginException {
        if (loginService.authenticate(user)) {
            request.getSession().setAttribute("login_user", user);
            logger.info("login OK redirect to rent");
            return "redirect:/equipment/rent";
        } else {
            logger.info("login FAIL redirect back to login");
            throw new MyLoginException("Invalid username or password");
        }
    }
}