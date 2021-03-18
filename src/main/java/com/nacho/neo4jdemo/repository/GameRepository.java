package com.nacho.neo4jdemo.repository;

import com.nacho.neo4jdemo.model.Game;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends Neo4jRepository<Game, String> {
}
