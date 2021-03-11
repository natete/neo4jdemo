package com.nacho.neo4jdemo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Node("User")
@Data
@NoArgsConstructor
public class User {

    @Id
    private String name;

    @Relationship(type = "KNOWS")
    private Set<User> knows;

    @Version
    private Long version;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant lastModified;

    public User(String name) {
        this.name = name;
        this.knows = new HashSet<>();
    }

    public void knows(User user) {
        if (knows == null) {
            this.knows = new HashSet<>();
        }
        this.knows.add(user);
    }
}
