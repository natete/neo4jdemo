package com.nacho.neo4jdemo.controller;

import com.nacho.neo4jdemo.model.Game;
import com.nacho.neo4jdemo.service.GameService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/games")
@AllArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping
    public void createGames() {
        gameService.createRandomGames();
    }

    @GetMapping(path = "/{name}")
    public Game getGame(@PathVariable("name") String name) {
        return gameService.getGame(name);
    }

}
