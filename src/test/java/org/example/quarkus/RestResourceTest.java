package org.example.quarkus;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.oidc.server.OidcWiremockTestResource;
import io.smallrye.jwt.build.Jwt;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@QuarkusTestResource(OidcWiremockTestResource.class)
public class RestResourceTest {

    @Test
    public void testExtraRole() {
        given().auth().oauth2(getAccessToken("alice"))
                .when().get("/rest").then()
                .statusCode(200)
                .body("roles.size()", is(1), "roles[0]", equalTo("Extra"));
    }

    @Test
    public void testNoExtraRole() {
        given().auth().oauth2(getAccessToken("bob"))
                .when().get("/rest").then()
                .statusCode(403);
    }

    private String getAccessToken(String userName) {
        return Jwt.preferredUserName(userName)
                .groups(new HashSet<>())
                .issuer("https://server.example.com")
                .audience("https://service.example.com")
                .jws()
                .keyId("1")
                .sign();
    }
}