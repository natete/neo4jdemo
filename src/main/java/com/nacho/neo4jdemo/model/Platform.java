package com.nacho.neo4jdemo.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("Platform")
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Platform {

    @Id
    private final String platformName;
}
