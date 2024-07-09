import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;

public class RestAssuredTest {

    @Test
    void testShouldWork(){
        String id = "6949869";

        given().pathParam("userId", id)

                .when().get("https://gorest.co.in/public/v2/users/{userId}")
                .then().log().body()
                .statusCode(404)
//                .body("status", Predicates.equalTo("active"))
                .log().body();
    }
//    @Test
//    void testCreation(){
//        String user = "6949869";
//
//        given()
//                .body(user)
//                .header("Autorization", "Bearer (iklijuoti token), ""Content-type", "aplication/json")
//
//                .log().all()
//                .when().post("https://gorest.co.in/public/v2/users/")
//                .then().log().body()
//                .log().all()
//                .statusCode(201)
//                .body("name", equalTo("Jonass"), "email", equalTo("jonass@stark.test"))
//                ;
//    }
}