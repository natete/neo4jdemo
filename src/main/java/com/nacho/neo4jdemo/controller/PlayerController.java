package com.nacho.neo4jdemo.controller;

import com.nacho.neo4jdemo.model.Player;
import com.nacho.neo4jdemo.model.User;
import com.nacho.neo4jdemo.service.PlayerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/player")
@AllArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

        @GetMapping(path = "/{id}")
    public Player getPlayer(@PathVariable("id") String id) {
        return playerService.getPlayer(id);
    }
}
