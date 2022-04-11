package org.example.web.controllers;

import org.example.app.services.MessagesService;
import org.example.web.dto.Message;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MessagesController {

    private MessagesService messagesService;

    @Autowired
    public MessagesController(MessagesService messagesService) {
        this.messagesService = messagesService;
    }

    @GetMapping("/messages")
    public String getMessages(@NotNull Model model) {
        List<Message> messageList = messagesService.getMessages();
        model.addAttribute("message", new Message());
        model.addAttribute("messageList", messageList);
        return "messages";
    }
}