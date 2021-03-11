package com.nacho.neo4jdemo.controller;

import com.nacho.neo4jdemo.controller.request.CreateUserRequest;
import com.nacho.neo4jdemo.controller.request.KnowsRequest;
import com.nacho.neo4jdemo.model.User;
import com.nacho.neo4jdemo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public void createUser(@RequestBody CreateUserRequest request) {
        userService.createUser(request.getName());
    }

    @PostMapping(path = "/knows")
    public void  knows(@RequestBody KnowsRequest request) {
        userService.setKnowledge(request.getUserA(), request.getUserB());
    }

    @PostMapping(path = "/knows_alt")
    public void  knowsAlt(@RequestBody KnowsRequest request) {
        userService.setKnowledgeAlt(request.getUserA(), request.getUserB());
    }

    @GetMapping(path = "/{name}")
    public User getUser(@PathVariable("name") String name) {
        return userService.getUser(name);
    }
}
