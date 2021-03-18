package com.nacho.neo4jdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.Instant;
import java.util.Set;

@Node("Game")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"users"})
public class Game {

    @Id
    private String gameId;

    @Relationship(type = "WAS_IN")
    private Platform wasIn;

    @Relationship(type = "IS_OF")
    private Sport isOf;

    @Relationship(type = "HAS_USERS")
    private Set<GameUserRelationship> users;

    @Version
    private Long version;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant lastModified;

    public Game(String gameId, Platform wasIn, Sport isOf,
                Set<GameUserRelationship> users) {
        this.gameId = gameId;
        this.wasIn = wasIn;
        this.isOf = isOf;
        this.users = users;
    }
}
