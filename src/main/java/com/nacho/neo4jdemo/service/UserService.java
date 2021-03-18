package com.nacho.neo4jdemo.service;

import com.nacho.neo4jdemo.model.User;
import com.nacho.neo4jdemo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserService {

    private static final int USERS_NUM = 7;

    private final UserRepository userRepository;

    @Transactional
    public void createUser(String name) {
        final User user = new User(name);
        userRepository.save(user);
    }

    @Transactional
    public void createRandomUsers() {
        for (int i = 0; i < USERS_NUM; i++) {
            final User user = new User("user_" + i);
            userRepository.save(user);
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

//    @Retry(name = "neo4j")
    @Transactional
    public void setLikesSDN(String userA, String userB) {
        final User userThatKnows = userRepository.findById(userA).orElseThrow();
        final User userKnown = userRepository.findById(userB).orElseThrow();

        userThatKnows.liking(userKnown);

        userRepository.save(userThatKnows);
    }

    @Transactional
    public void setLikesNative(String userA, String userB) {
        userRepository.addLikes(userA, userB);
    }

    @Transactional
    public void setHatesNative(String userA, String userB) {
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
