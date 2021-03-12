package com.nacho.neo4jdemo.repository;

import com.nacho.neo4jdemo.model.Player;
import com.nacho.neo4jdemo.model.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends Neo4jRepository<Player, String> {


}
