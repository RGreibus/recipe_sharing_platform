import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class RegistrationTests {

    @BeforeEach
    void cleanUpDatabase() throws SQLException {
        Connection connection = DriverManager.getConnection(
                "jdbc:h2:tcp://localhost/~/recipe-sharing-platform/backend/rsp", "sa", "");
        String deleteQuery = "DELETE FROM users_roles; DELETE FROM users;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void whenVisitorRegistersSuccessfully_thenReturn201AndResponseBody() {
        given()
                .body(
                        """
                {
                    "lastName": "Rasiene",
                    "firstName": "Rasa",
                    "country": "Lithuania",
                    "password": "Testas1*",
                    "displayName": "RasaRasiene",
                    "gender": "Other",
                    "roles": [
                        {
                            "id": 1
                        }
                    ],
                    "dateOfBirth": "1980-09-25",
                    "email": "rasa@rasiene.lt"
                }
                """)
                .contentType(ContentType.JSON)
                .when()
                .request("POST", "/register")
                .then()
                .assertThat()
                .statusCode(201)
                .body(
                        "id",
                        not(equalTo(0)),
                        "firstName",
                        equalTo("Rasa"),
                        "lastName",
                        equalTo("Rasiene"),
                        "country",
                        equalTo("Lithuania"),
                        "password",
                        not(equalTo("Testas1*")),
                        "displayName",
                        equalTo("RasaRasiene"),
                        "gender",
                        equalTo("Other"),
                        "dateOfBirth",
                        equalTo("1980-09-25"),
                        "email",
                        equalTo("rasa@rasiene.lt"),
                        "roles",
                        hasSize(1),
                        "roles[0].id",
                        equalTo(1),
                        "authorities",
                        hasSize(1),
                        "authorities[0].id",
                        equalTo(1),
                        "username",
                        equalTo("rasa@rasiene.lt"),
                        "accountNonLocked",
                        equalTo(true),
                        "accountNonExpired",
                        equalTo(true),
                        "credentialsNonExpired",
                        equalTo(true),
                        "enabled",
                        equalTo(true));
    }

    @Test
    void whenVisitorRegistersWithTwoRoles_thenReturn201AndResponseBody() {
        given()
                .body(
                        """
                {
                    "lastName": "Rasiene",
                    "firstName": "Rasa",
                    "country": "Lithuania",
                    "password": "Testas1*",
                    "displayName": "RasaRasiene",
                    "gender": "Other",
                    "roles": [
                        {
                            "id": 1
                        },
                        {
                            "id": 2
                        }
                    ],
                    "dateOfBirth": "1980-09-25",
                    "email": "rasa@rasiene.lt"
                }
                """)
                .contentType(ContentType.JSON)
                .when()
                .request("POST", "/register")
                .then()
                .assertThat()
                .statusCode(201)
                .body(
                        "id",
                        not(equalTo(0)),
                        "firstName",
                        equalTo("Rasa"),
                        "lastName",
                        equalTo("Rasiene"),
                        "country",
                        equalTo("Lithuania"),
                        "password",
                        not(equalTo("Testas1*")),
                        "displayName",
                        equalTo("RasaRasiene"),
                        "gender",
                        equalTo("Other"),
                        "dateOfBirth",
                        equalTo("1980-09-25"),
                        "email",
                        equalTo("rasa@rasiene.lt"),
                        "roles",
                        hasSize(2),
                        "roles[0].id",
                        equalTo(1),
                        "roles[1].id",
                        equalTo(2),
                        "authorities",
                        hasSize(2),
                        "authorities[0].id",
                        equalTo(1),
                        "authorities[1].id",
                        equalTo(2),
                        "username",
                        equalTo("rasa@rasiene.lt"),
                        "accountNonLocked",
                        equalTo(true),
                        "accountNonExpired",
                        equalTo(true),
                        "credentialsNonExpired",
                        equalTo(true),
                        "enabled",
                        equalTo(true));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/genders.csv")
    void whenVisitorRegistersWithTwoRolesAndGender_thenReturn201AndResponseBody(String gender) {
        given()
                .body(
                        """
                {
                    "lastName": "Rasiene",
                    "firstName": "Rasa",
                    "country": "Lithuania",
                    "password": "Testas1*",
                    "displayName": "RasaRasiene",
                    "gender": "%s",
                    "roles": [
                        {"id": 1},
                        {"id": 2}
                    ],
                    "dateOfBirth": "1980-09-25",
                    "email": "rasa@rasiene.lt"
                }
                """
                                .formatted(gender))
                .contentType(ContentType.JSON)
                .when()
                .request("POST", "/register")
                .then()
                .assertThat()
                .statusCode(201)
                .body(
                        "id",
                        not(equalTo(0)),
                        "firstName",
                        equalTo("Rasa"),
                        "lastName",
                        equalTo("Rasiene"),
                        "country",
                        equalTo("Lithuania"),
                        "password",
                        not(equalTo("Testas1*")),
                        "displayName",
                        equalTo("RasaRasiene"),
                        "gender",
                        equalTo(gender),
                        "dateOfBirth",
                        equalTo("1980-09-25"),
                        "email",
                        equalTo("rasa@rasiene.lt"),
                        "roles",
                        hasSize(2),
                        "roles[0].id",
                        equalTo(1),
                        "roles[1].id",
                        equalTo(2),
                        "authorities",
                        hasSize(2),
                        "authorities[0].id",
                        equalTo(1),
                        "authorities[1].id",
                        equalTo(2),
                        "username",
                        equalTo("rasa@rasiene.lt"),
                        "accountNonLocked",
                        equalTo(true),
                        "accountNonExpired",
                        equalTo(true),
                        "credentialsNonExpired",
                        equalTo(true),
                        "enabled",
                        equalTo(true));
    }
    @Test
    void whenVisitorRegistersWithoutGenderSelection_thenReturn201AndResponseBody() {
        given()
                .body(
                        """
                {
                    "lastName": "Rasiene",
                    "firstName": "Rasa",
                    "country": "Lithuania",
                    "password": "Testas1*",
                    "displayName": "RasaRasiene",
                    "roles": [
                        {"id": 1}
                    ],
                    "dateOfBirth": "1980-09-25",
                    "email": "rasa@rasiene.lt"
                }
                """)
                .contentType(ContentType.JSON)
                .when()
                .request("POST", "/register")
                .then()
                .assertThat()
                .statusCode(201)
                .body(
                        "id",
                        not(equalTo(0)),
                        "firstName",
                        equalTo("Rasa"),
                        "lastName",
                        equalTo("Rasiene"),
                        "country",
                        equalTo("Lithuania"),
                        "password",
                        not(equalTo("Testas1*")),
                        "displayName",
                        equalTo("RasaRasiene"),
                        "dateOfBirth",
                        equalTo("1980-09-25"),
                        "email",
                        equalTo("rasa@rasiene.lt"),
                        "roles",
                        hasSize(1),
                        "roles[0].id",
                        equalTo(1),
                        "authorities",
                        hasSize(1),
                        "authorities[0].id",
                        equalTo(1),
                        "username",
                        equalTo("rasa@rasiene.lt"),
                        "accountNonLocked",
                        equalTo(true),
                        "accountNonExpired",
                        equalTo(true),
                        "credentialsNonExpired",
                        equalTo(true),
                        "enabled",
                        equalTo(true));
    }
//    @Test
//    void whenVisitorRegistersWithEmptyFields_thenReturn400AndValidationErrors() {
//        given()
//                .body(
//                        """
//                {
//                    "firstName": "",
//                    "lastName": "",
//                    "displayName": "",
//                    "password": "",
//                    "email": "",
//                    "roles": [
//                        {"id": 1}
//                    ],
//                    "dateOfBirth": "",
//                    "country": ""
//                }
//                """)
//                .contentType(ContentType.JSON)
//                .when()
//                .request("POST", "/register")
//                .then()
//                .assertThat()
//                .statusCode(400)
//                .body(
//                        "lastName",
//                        equalTo("Cannot be null or empty"),
//                        "firstName",
//                        equalTo("You can only enter letters. First letter must be capital. At least 2 characters long"),
//                        "country",
//                        equalTo("Cannot be null or empty"),
//                        "password",
//                        equalTo("Cannot be null or empty"),
//                        "displayName",
//                        equalTo("Cannot be null or empty"),
//                        "dateOfBirth",
//                        equalTo("Cannot be null or empty"),
//                        "email",
//                        equalTo("Minimum length 5 characters, maximum length 200 characters"));
//    }
@Test
void whenVisitorEntersNumberInFirstName_thenReturn400AndFirstNameValidationError() {
    given()
            .body(
                    """
            {
                "firstName": "Rasa1",
                "lastName": "Rasiene",
                "displayName": "rasosLasass",
                "password": "RasaRasiene123!",
                "email": "rasa0@gmail.com",
                "roles": [
                    {"id": 1}
                ],
                "dateOfBirth": "1980-09-25",
                "country": "Lithuania"
            }
            """)
            .contentType(ContentType.JSON)
            .when()
            .request("POST", "/register")
            .then()
            .assertThat()
            .statusCode(400)
            .body(
                    "size()", is(1),"firstName", equalTo("You can only enter letters. First letter must be capital. At least 2 characters long"));
     }
    @Test
    void whenVisitorRegistersWithFirstNameFirstLetterNotUppercase_thenReturn400AndFirstNameValidationError() {
        given()
                .body(
                        """
                {
                    "firstName": "rasa",
                    "lastName": "Rasiene",
                    "displayName": "rasosLasass",
                    "password": "RasaRasiene123!",
                    "email": "rasa0@gmail.com",
                    "roles": [
                        {"id": 1}
                    ],
                    "dateOfBirth": "1980-09-25",
                    "country": "Lithuania"
                }
                """)
                .contentType(ContentType.JSON)
                .when()
                .request("POST", "/register")
                .then()
                .assertThat()
                .statusCode(400)
                .body(
                        "firstName", equalTo("You can only enter letters. First letter must be capital. At least 2 characters long"));
    }
    @Test
    void whenVisitorRegistersWithInvalidLastName_thenReturn400AndLastNameValidationError() {
        given()
                .body(
                        """
                {
                    "firstName": "Rasa",
                    "lastName": "Rasiene1",
                    "displayName": "rasosLasass",
                    "password": "RasaRasiene123!",
                    "email": "rasa1@gmail.com",
                    "roles": [
                        {"id": 1}
                    ],
                    "dateOfBirth": "1980-09-25",
                    "country": "Lithuania"
                }
                """)
                .contentType(ContentType.JSON)
                .when()
                .request("POST", "/register")
                .then()
                .assertThat()
                .statusCode(400)
                .body(
                        "lastName", equalTo("You can only enter letters. First letter must be capital. At least 2 characters long"));
    }
    @Test
    void whenVisitorRegistersWithDisplayNameContainsTwoConsecutiveSpaces_thenReturn400AndDisplayNameValidationError() {
        given()
                .body(
                        """
                {
                    "firstName": "Rasa",
                    "lastName": "Rasiene",
                    "displayName": "rasos  Lasas",
                    "password": "RasaRasiene123!",
                    "email": "rasa1@gmail.com",
                    "roles": [
                        {"id": 1}
                    ],
                    "dateOfBirth": "1980-09-25",
                    "country": "Lithuania"
                }
                """)
                .contentType(ContentType.JSON)
                .when()
                .request("POST", "/register")
                .then()
                .assertThat()
                .statusCode(400)
                .body(
                        "displayName", equalTo("You can only enter letters or numbers. At least 1 character long. Cannot begin or end with a space. No more than one space between words"));
    }
    @Test
    void whenVisitorRegistersWithNotUniqueDisplayName_thenReturn400AndDisplayNameAlreadyExists() {
        String requestBody = """
        {
            "firstName": "Rasa",
            "lastName": "Rasiene",
            "displayName": "rasos Lasas",
            "password": "RasaRasiene123!",
            "email": "rasa3@gmail.com",
            "roles": [
                {"id": 1}
            ],
            "dateOfBirth": "1980-09-25",
            "country": "Lithuania"
        }
        """;

        given()
                .body(requestBody)
                .contentType(ContentType.JSON)
                .when()
                .request("POST", "/register")
                .then()
                .assertThat()
                .statusCode(201);

        String secondRequestBody = """
        {
            "firstName": "Rasa",
            "lastName": "Rasiene",
            "displayName": "rasos Lasas",
            "password": "RasaRasiene123!",
            "email": "rasa40@gmail.com",
            "roles": [
                {"id": 1}
            ],
            "dateOfBirth": "1980-09-25",
            "country": "Lithuania"
        }
        """;

        given()
                .body(secondRequestBody)
                .contentType(ContentType.JSON)
                .when()
                .request("POST", "/register")
                .then()
                .assertThat()
                .statusCode(400)
                .body(
                        "displayName", equalTo("Already exists"));
    }
}


