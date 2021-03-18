package com.nacho.neo4jdemo.service;

import com.nacho.neo4jdemo.model.Game;
import com.nacho.neo4jdemo.model.GameUserRelationship;
import com.nacho.neo4jdemo.model.Platform;
import com.nacho.neo4jdemo.model.Sport;
import com.nacho.neo4jdemo.model.User;
import com.nacho.neo4jdemo.repository.GameRepository;
import com.nacho.neo4jdemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    private static final int SPORTS_NUM = 3;
    private static final int PLATFORMS_NUM = 5;
    private static final int USERS_NUM = 7;

    Map<Integer, Platform> platforms = new HashMap<>();
    Map<Integer, Sport> sports = new HashMap<>();
    Map<Integer, User> users = new HashMap<>();

    @Transactional
    public void createRandomGames() {
        createRandomPlatforms(PLATFORMS_NUM);
        createRandomSports(SPORTS_NUM);

        for (int i = 0; i < 10; i++) {
            int numUsers = getRandomNumber(1, 3);
            Set<GameUserRelationship> usersGame = new HashSet<>();
            for (int u = 0; u < numUsers; u++) {
                usersGame.add(new GameUserRelationship(null, users.get(getRandomNumber(0, USERS_NUM - 1)), "team_"+ getRandomNumber(0, 100)));
            }

            Game game = new Game("game_" + i, platforms.get(getRandomNumber(0, PLATFORMS_NUM - 1)),sports.get(getRandomNumber(0, SPORTS_NUM - 1)), usersGame);

            gameRepository.save(game);
        }

    }
    @Transactional
    public void createRandomUsers() {
        for (int i = 0; i < USERS_NUM; i++) {
            User user = new User("user_" + i);
            user = userRepository.save(user);
            users.put(i, user);
        }
//        {
//            User user = new User("AAAA");
//            user = userRepository.save(user);
//        }
//        {
//            User user = new User("BBBB");
//            user = userRepository.save(user);
//        }
    }

    private void createRandomPlatforms(int num) {
        for (int i = 0; i < num; i++) {
            platforms.put(i, new Platform("platform_" + i));
        }
    }

    private void createRandomSports(int num) {
        for (int i = 0; i < num; i++) {
            sports.put(i, new Sport("sport_" + i));
        }
    }

    public int getRandomNumber(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    @Transactional(readOnly = true)
    public Game getGame(String name) {
        return gameRepository.findById(name).orElseThrow();
    }
}
