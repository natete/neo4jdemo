package com.nacho.neo4jdemo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Node("User")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, exclude = {"likes", "hates"})
public class User extends UpdatableNode {

    @Id
    private String userName;

    @Relationship(type = "LIKES")
    private Set<User> likes;

    @Relationship(type = "HATES")
    private Set<User> hates;

    public User(String name) {
        this.userName = name;
        this.likes = new HashSet<>();
        this.hates = new HashSet<>();
    }

    public void liking(User user) {
        if (likes == null) {
            this.likes = new HashSet<>();
        }
        this.likes.add(user);
    }
}
