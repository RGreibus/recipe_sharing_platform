import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static io.restassured.RestAssured.given;

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
                    "lastName": "Testas",
                    "firstName": "Testas",
                    "country": "Lithuania",
                    "password": "Testas1*",
                    "displayName": "Testas5",
                    "roles": [
                        {
                            "id": 1
                        }
                    ],
                    "dateOfBirth": "1900-01-01",
                    "email": "testas3@testas.lt"
                }
                """)
                .contentType(ContentType.JSON)
                .when()
                .request("POST", "/register")
                .then()
                .assertThat()
                .statusCode(201);

    }
}


