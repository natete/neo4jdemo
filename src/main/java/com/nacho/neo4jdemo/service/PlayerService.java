package com.nacho.neo4jdemo.service;

import com.nacho.neo4jdemo.model.Player;
import com.nacho.neo4jdemo.model.User;
import com.nacho.neo4jdemo.repository.PlayerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;


    public Player getPlayer(String id) {
        final Player player = playerRepository.findById(id).orElseThrow();
        if (player.getFollowedPlayers() != null) {
            player.getFollowedPlayers().forEach(liked -> {
                liked.setFollowedPlayers(null);
                liked.setBlockedPlayers(null);
            });
        }

        if (player.getBlockedPlayers() != null) {
            player.getBlockedPlayers().forEach(hated -> {
                hated.setFollowedPlayers(null);
                hated.setBlockedPlayers(null);
            });
        }
        return player;
    }
}
