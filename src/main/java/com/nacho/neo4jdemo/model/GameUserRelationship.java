package com.nacho.neo4jdemo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
@AllArgsConstructor
@Getter
@Setter
public class GameUserRelationship {

    @Id @GeneratedValue
    private Long id;

    @TargetNode
    private User user;

    private String teamName;

}