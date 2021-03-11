package com.nacho.neo4jdemo.service;

import com.nacho.neo4jdemo.model.User;
import com.nacho.neo4jdemo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void createUser(String name) {
        final User user = new User(name);

        userRepository.save(user);
    }

    @Transactional
    public void setLikes(String userA, String userB) {
        final User userThatKnows = userRepository.findById(userA).orElseThrow();
        final User userKnown = userRepository.findById(userB).orElseThrow();

        userThatKnows.knows(userKnown);

        userRepository.save(userThatKnows);
    }

    @Transactional
    public void setLikesAlt(String userA, String userB) {
        userRepository.addLikes(userA, userB);
    }

    @Transactional
    public void setHatesAlt(String userA, String userB) {
        userRepository.addHates(userA, userB);
    }

    public User getUser(String name) {
        final User user = userRepository.findById(name).orElseThrow();
        if (user.getLikes() != null) {
            user.getLikes().forEach(liked -> {
                liked.setLikes(null);
                liked.setHates(null);
            });
        }

        if (user.getHates() != null) {
            user.getHates().forEach(hated -> {
                hated.setLikes(null);
                hated.setHates(null);
            });
        }
        return user;
    }
}
