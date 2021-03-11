package com.nacho.neo4jdemo.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelationshipRequest {

    private String userA;
    private String userB;
}
