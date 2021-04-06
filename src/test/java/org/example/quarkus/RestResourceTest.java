package org.example.quarkus;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class RestResourceTest {

    @Test
    @TestSecurity(user = "user")
    public void withTestSecurityAnnotation() {
        given().when().get("/rest").then()
                .statusCode(200)
                .body("roles.size()", is(1), "roles[0]", equalTo("Extra"));
    }

    @Test
    public void withoutAnnotation() {
        given().when().get("/rest").then()
                .statusCode(200)
                .body("roles.size()", is(1), "roles[0]", equalTo("Extra"));
    }
}