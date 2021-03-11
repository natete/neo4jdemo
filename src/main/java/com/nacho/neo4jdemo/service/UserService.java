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
    public void setKnowledge(String userA, String userB) {
        final User userThatKnows = userRepository.findById(userA).orElseThrow();
        final User userKnown = userRepository.findById(userB).orElseThrow();

        userThatKnows.knows(userKnown);

        userRepository.save(userThatKnows);
    }

    @Transactional
    public void setKnowledgeAlt(String userA, String userB) {
        final User userThatKnows = userRepository.findById(userA).orElseThrow();
        final User userKnown = userRepository.findById(userB).orElseThrow();

        userRepository.addKnows(userA, userB);
    }

    public User getUser(String name) {
        return userRepository.findById(name).orElseThrow();
    }
}
