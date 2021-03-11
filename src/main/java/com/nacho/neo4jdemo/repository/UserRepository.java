package com.nacho.neo4jdemo.repository;

import com.nacho.neo4jdemo.model.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends Neo4jRepository<User, String> {

    @Query(value = "" +
            "MATCH (userA:User) " +
            "MATCH (userB:User) " +
            "WHERE userA.name = $userA AND userB.name = $userB " +
            "MERGE (userA)-[:KNOWS]->(userB) " +
            "SET userA.version = userA.version + 1 " +
            "RETURN userA")
    User addKnows(@Param("userA") String userA, @Param("userB") String userB);
}
