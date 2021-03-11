package com.nacho.neo4jdemo.controller;

import com.nacho.neo4jdemo.DemoApplication;
import com.nacho.neo4jdemo.controller.request.CreateUserRequest;
import com.nacho.neo4jdemo.controller.request.KnowsRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.neo4j.configuration.connectors.BoltConnector;
import org.neo4j.configuration.helpers.SocketAddress;
import org.neo4j.harness.Neo4j;
import org.neo4j.harness.Neo4jBuilders;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DemoApplication.class)
@ExtendWith(SpringExtension.class)
public class UserControllerTest {

    private final static String HOSTNAME = "localhost";

    private final static int PORT = 6666;

    protected static Neo4j embeddedDatabaseServer;

    @BeforeAll
    static void initializeNeo4j(@LocalServerPort int port) {
        embeddedDatabaseServer = Neo4jBuilders
                .newInProcessBuilder()
                .withDisabledServer()
                .withConfig(BoltConnector.listen_address, new SocketAddress(HOSTNAME, PORT))
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        RestAssured.port = port;
        RestAssured.config = RestAssuredConfig.config()
                .objectMapperConfig(
                        new ObjectMapperConfig()
                                .jackson2ObjectMapperFactory((type, s) -> objectMapper));
    }

    @AfterAll
    static void closeNeo4j() {
        embeddedDatabaseServer.close();
    }

    @Test
    public void should_create_two_relationships() {
        final String userA = "alice";
        final String userB = "bob";

        // This one causes problems when saving the second relationship.

        RestAssured
                .given()
                .contentType("application/json")
                .body(new CreateUserRequest(userA))
                .post("/user")
                .then()
                .statusCode(HttpStatus.OK.value());

        RestAssured
                .given()
                .contentType("application/json")
                .body(new CreateUserRequest(userB))
                .post("/user")
                .then()
                .statusCode(HttpStatus.OK.value());

        RestAssured
                .given()
                .contentType("application/json")
                .get("/user/" + userA)
                .then()
                .statusCode(HttpStatus.OK.value());

        RestAssured
                .given()
                .contentType("application/json")
                .get("/user/" + userB)
                .then()
                .statusCode(HttpStatus.OK.value());

        RestAssured
                .given()
                .contentType("application/json")
                .body(new KnowsRequest(userA, userB))
                .post("/user/knows")
                .then()
                .statusCode(HttpStatus.OK.value());


        RestAssured
                .given()
                .contentType("application/json")
                .body(new KnowsRequest(userB, userA))
                .post("/user/knows")
                .then()
                .statusCode(HttpStatus.OK.value());


        RestAssured
                .given()
                .contentType("application/json")
                .get("/user/" + userA)
                .then()
                .statusCode(HttpStatus.OK.value());

        RestAssured
                .given()
                .contentType("application/json")
                .get("/user/" + userB)
                .then()
                .statusCode(HttpStatus.OK.value());

    }

    @Test
    public void should_create_two_relationships_alt() {
        final String userA = "alice";
        final String userB = "bob";

        // This one causes StackOverflow when retrieving an entity with bidirectional relationship

        RestAssured
                .given()
                .contentType("application/json")
                .body(new CreateUserRequest(userA))
                .post("/user")
                .then()
                .statusCode(HttpStatus.OK.value());

        RestAssured
                .given()
                .contentType("application/json")
                .body(new CreateUserRequest(userB))
                .post("/user")
                .then()
                .statusCode(HttpStatus.OK.value());

        RestAssured
                .given()
                .contentType("application/json")
                .get("/user/" + userA)
                .then()
                .statusCode(HttpStatus.OK.value());

        RestAssured
                .given()
                .contentType("application/json")
                .get("/user/" + userB)
                .then()
                .statusCode(HttpStatus.OK.value());

        RestAssured
                .given()
                .contentType("application/json")
                .body(new KnowsRequest(userA, userB))
                .post("/user/knows_alt")
                .then()
                .statusCode(HttpStatus.OK.value());


        RestAssured
                .given()
                .contentType("application/json")
                .body(new KnowsRequest(userB, userA))
                .post("/user/knows_alt")
                .then()
                .statusCode(HttpStatus.OK.value());


        RestAssured
                .given()
                .contentType("application/json")
                .get("/user/" + userA)
                .then()
                .statusCode(HttpStatus.OK.value());

        RestAssured
                .given()
                .contentType("application/json")
                .get("/user/" + userB)
                .then()
                .statusCode(HttpStatus.OK.value());

    }
}
