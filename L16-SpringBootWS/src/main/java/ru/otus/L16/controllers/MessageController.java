package ru.otus.L16.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.HtmlUtils;
import ru.otus.L16.messageSystem.entity.messages.Message;
import ru.otus.L16.services.UserService;
import ru.otus.l11.entity.MyUser;

import java.util.List;
import java.util.concurrent.ExecutionException;


@Controller
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);


    private final UserService userService;

    public MessageController(UserService userService){
        this.userService=userService;
    }


    @MessageMapping("/message")
    @SendTo("/topic/response")
    public List<MyUser> getUsers() throws ExecutionException, InterruptedException {
       return  userService.getUsers();
    }

    @GetMapping("/addUser")
    public void addUser(){

    }


}
