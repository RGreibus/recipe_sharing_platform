import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;

public class RegistrationTests {

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

