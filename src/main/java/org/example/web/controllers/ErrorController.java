package org.example.web.controllers;

import org.example.app.exceptions.MyLoginException;
import org.jetbrains.annotations.NotNull;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

@ControllerAdvice
public class ErrorController {

    @GetMapping("/404")
    public String notFoundError() {
        return "errors/404";
    }

    @ExceptionHandler(MyLoginException.class)
    public String handleError(@NotNull Model model, @NotNull MyLoginException myLoginException) {
        model.addAttribute("errorMessage", myLoginException.getMessage());
        return "errors/404";
    }
}