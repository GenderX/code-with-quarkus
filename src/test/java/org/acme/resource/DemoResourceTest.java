package org.acme.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import org.acme.entity.Demo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DemoResourceTest {

    private static Long createdDemoId;

    @Test
    @Order(1)
    void testCreateDemo() {
        Demo demo = new Demo("Test Demo", "Test Description");
        
        Response response = given()
            .contentType("application/json")
            .body(demo)
            .when()
            .post("/demo")
            .then()
            .statusCode(201)
            .extract().response();
            
        Demo createdDemo = response.as(Demo.class);
        assertNotNull(createdDemo.id);
        createdDemoId = createdDemo.id;
    }

    @Test
    @Order(2)
    void testGetAllDemos() {
        given()
            .when()
            .get("/demo")
            .then()
            .statusCode(200)
            .body("$.size()", greaterThan(0));
    }

    @Test
    @Order(3)
    void testGetDemoById() {
        given()
            .when()
            .get("/demo/" + createdDemoId)
            .then()
            .statusCode(200)
            .body("name", is("Test Demo"))
            .body("description", is("Test Description"));
    }

    @Test
    @Order(4)
    void testUpdateDemo() {
        Demo updatedDemo = new Demo("Updated Demo", "Updated Description");
        
        given()
            .contentType("application/json")
            .body(updatedDemo)
            .when()
            .put("/demo/" + createdDemoId)
            .then()
            .statusCode(200)
            .body("name", is("Updated Demo"))
            .body("description", is("Updated Description"));
    }

    @Test
    @Order(5)
    void testDeleteDemo() {
        given()
            .when()
            .delete("/demo/" + createdDemoId)
            .then()
            .statusCode(204);
    }

    @Test
    @Order(6)
    void testGetDeletedDemo() {
        given()
            .when()
            .get("/demo/" + createdDemoId)
            .then()
            .statusCode(404);
    }
}