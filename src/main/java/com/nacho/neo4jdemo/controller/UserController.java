package com.nacho.neo4jdemo.controller;

import com.nacho.neo4jdemo.controller.request.CreateUserRequest;
import com.nacho.neo4jdemo.controller.request.RelationshipRequest;
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
@RequestMapping(path = "/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public void createUser(@RequestBody CreateUserRequest request) {
        userService.createUser(request.getName());
    }

    @PostMapping(path= "/random")
    public void createUsers() {
        userService.createRandomUsers();
    }

    @PostMapping(path = "/likes")
    public void likes(@RequestBody RelationshipRequest request) {
        userService.setLikesSDN(request.getUserA(), request.getUserB());
    }

    @PostMapping(path = "/likes_nat")
    public void  likesAlt(@RequestBody RelationshipRequest request) {
        userService.setLikesNative(request.getUserA(), request.getUserB());
    }

    @PostMapping(path = "/hates_nat")
    public void  hatesAlt(@RequestBody RelationshipRequest request) {
        userService.setHatesNative(request.getUserA(), request.getUserB());
    }

    @GetMapping(path = "/{name}")
    public User getUser(@PathVariable("name") String name) {
        return userService.getUser(name);
    }

}
