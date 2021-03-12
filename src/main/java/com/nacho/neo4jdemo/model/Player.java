package com.nacho.neo4jdemo.model;

import com.nacho.neo4jdemo.utils.StringNormalizer;
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
import java.util.HashSet;
import java.util.Set;

@Node("Player")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"followedPlayers", "blockedPlayers"})
public class Player {

    @Id
    private String userId;

    private String name;

    private String sanitizedName;

    private String email;

    private String gender;

    private String picture;

    @Relationship(type = "FOLLOWS")
    private Set<Player> followedPlayers;

    @Relationship(type = "BLOCKS")
    private Set<Player> blockedPlayers;

    @Version
    private Long version;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant lastModified;

    public Player(String userId,
                  String name,
                  String email,
                  String gender,
                  String picture,
                  Set<Player> followedPlayers,
                  Set<Player> blockedPlayers) {
        this.userId = userId;
        this.name = name;
        this.sanitizedName = getSanitizedName(name);
        this.email = email;
        this.gender = gender;
        this.picture = picture;
        this.followedPlayers = followedPlayers != null ? followedPlayers : new HashSet<>();
        this.blockedPlayers = blockedPlayers != null ? blockedPlayers : new HashSet<>();
    }

    private static String getSanitizedName(String name) {
        return StringNormalizer.stripDiacritics(name).toLowerCase();
    }
}
