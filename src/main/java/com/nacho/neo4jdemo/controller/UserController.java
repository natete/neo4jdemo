package com.nacho.neo4jdemo.controller;

import com.nacho.neo4jdemo.controller.request.CreateUserRequest;
import com.nacho.neo4jdemo.controller.request.RelationshipRequest;
import com.nacho.neo4jdemo.model.User;
import com.nacho.neo4jdemo.service.GameService;
import com.nacho.neo4jdemo.service.UserService;
import com.nacho.neo4jdemo.utils.OptimisticLockingRetryableExecutor;
import com.nacho.neo4jdemo.utils.OptimisticLockingRetryableExecutor.OptimisticLockingRetryableAction;
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
    private final GameService gameService;

    @PostMapping
    public void createUser(@RequestBody CreateUserRequest request) {
        userService.createUser(request.getName());
    }

    @PostMapping(path = "/likes")
    public void likes(@RequestBody RelationshipRequest request) {
        OptimisticLockingRetryableAction<Void> action = () -> {
            userService.setLikes(request.getUserA(), request.getUserB());
            return null;
        };

        OptimisticLockingRetryableExecutor<Void> executor = new OptimisticLockingRetryableExecutor<>(action, 5);
        executor.execute();
    }

    @PostMapping(path = "/likes_alt")
    public void  likesAlt(@RequestBody RelationshipRequest request) {
        userService.setLikesAlt(request.getUserA(), request.getUserB());
    }

    @PostMapping(path = "/hates_alt")
    public void  hatesAlt(@RequestBody RelationshipRequest request) {
        userService.setHatesAlt(request.getUserA(), request.getUserB());
    }

    @GetMapping(path = "/{name}")
    public User getUser(@PathVariable("name") String name) {
        return userService.getUser(name);
    }

    @PostMapping(path= "/users")
    public void createUsers() {
        gameService.createRandomUsers();
    }
}
