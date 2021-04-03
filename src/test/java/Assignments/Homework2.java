package Assignments;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import utilities.ConfigurationReader;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.List;
import static org.hamcrest.Matchers.*;

public class Homework2 {

    /*
    Given accept type is json
    And path param id is 20
    When user sends a get request to "/spartans/{id}"
    Then status code is 200
    And content-type is "application/json;char"
    And response header contains Date
    And Transfer-Encoding is chunked
    And response payload values match the following:
    id is 20,
    name is "Lothario",
    gender is "Male",
    phone is 7551551687
    */

    @BeforeClass
    public void beforeClass(){
        RestAssured.baseURI = ConfigurationReader.getProperty("spartanapi_url");
    }

    @Test
    public void q1WithPathMethod(){

        Response response = given().accept(ContentType.JSON)
                .and().auth().basic("admin", "admin")
                .and().pathParam("id", 20)
                .when().get("/api/spartans/{id}");

        response.prettyPrint();

        System.out.println("response.statusCode() = " + response.statusCode());
        assertEquals(response.statusCode(),200);

        System.out.println("response.contentType() = " + response.contentType());
        assertEquals(response.contentType(),"application/json;charset=UTF-8");

        System.out.println("response.getHeaders().toString() = " + response.getHeaders().toString());
        assertTrue(response.getHeaders().toString().contains("Date"));//hasheaderswithname

        System.out.println("response.header(\"Transfer-Encoding\") = " + response.header("Transfer-Encoding"));
        assertEquals(response.header("Transfer-Encoding"),"chunked");

    }

    @Test
    public void q1WithHamcrestMatchersMethod(){

        given().accept(ContentType.JSON)
                .and().auth().basic("admin","admin")
                .and().pathParam("id", 20)
                .when().get("/api/spartans/{id}")
                .then().statusCode(200)
                .and().contentType("application/json;charset=UTF-8")
                .and().assertThat().header("Date",notNullValue())
                .and().assertThat().header("Transfer-Encoding",equalTo("chunked"));

    }

    /*
    Given accept type is json
    And query param gender = Female
    And query param nameContains = r
    When user sends a get request to "/spartans/search"
    Then status code is 200
    And content-type is "application/json;char"
    And all genders are Female
    And all names contains r
    And size is 20
    And totalPages is 1
    And sorted is false
     */

    @Test
    public void q2WithPathMethod(){

        Response response = given().accept(ContentType.JSON)
                .and().auth().basic("admin","admin")
                .and().queryParam("gender", "Female")
                .and().queryParam("nameContains", "r")
                .when().get("api/spartans/search");

        System.out.println("response.statusCode() = " + response.statusCode());
        assertEquals(response.statusCode(),200);

        System.out.println("response.contentType() = " + response.contentType());
        assertEquals(response.contentType(),"application/json;charset=UTF-8");

        List<String> genders = response.path("content.gender");
        System.out.println("genders.toString() = " + genders.toString());
        for (String each : genders){
            assertEquals(each,"Female");
        }

        List<String> names = response.path("content.name");
        System.out.println("names.toString() = " + names.toString());
        for (String each : names){
            assertTrue(each.toLowerCase().contains("r"));
        }

        int size = response.path("size");
        System.out.println("size = " + size);
        assertEquals(size,20);

        int totalPages = response.path("totalPages");
        System.out.println("totalPages = " + totalPages);
        assertEquals(totalPages,1);

    }

    @Test
    public void q2WithHamcrestMatchersMethod(){

        given().accept(ContentType.JSON)
                .and().auth().basic("admin","admin")
                .and().queryParam("gender","female")
                .and().queryParam("nameContains","r")
                .when().get("api/spartans/search")
                .then().statusCode(200)
                .and().contentType("application/json;charset=UTF-8")
                .and().assertThat().body("content.gender",everyItem(containsString("Female")),
                         "size",equalTo(20),
                                              "totalPages",equalTo(1),
                                              "content.name",everyItem(containsStringIgnoringCase("r")));

    }

}
