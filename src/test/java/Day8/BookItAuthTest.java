package Day8;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import io.restassured.response.Response;

public class BookItAuthTest {
    String accessToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxOTkxIiwiYXVkIjoic3R1ZGVudC10ZWFtLWxlYWRlciJ9.PejlP7F8p8bA9BGqol3jfWESaPRP5lBkIy7q2huN-4s";

    @BeforeClass
    public void before(){
        RestAssured.baseURI = "https://cybertek-reservation-api-qa.herokuapp.com";
    }

    @Test
    public void getAllCampuses(){
        Response response = given().header("Authorization", accessToken)
                .when().get("/api/campuses");

        response.prettyPrint();

    }

}