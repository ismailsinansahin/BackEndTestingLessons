package Day3;

import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SpartanGetRequest {

    String spartanurl = "http://18.208.178.105:8000";

    @Test
    public void test1(){

        Response response = given().accept(ContentType.JSON).and()
                .auth().basic("admin","admin")
                .when().get(spartanurl + "/api/spartans");

        System.out.println(response.statusCode());

        response.prettyPrint();

    }

    /*TASK
    When user sends a get request to /api/spartans/3
    Then status code should be 200
    And content type should be application/json;charset=UTF-8
    and json body should contain Fidole
     */

    @Test
    public void test2(){

        Response response = given().auth().basic("admin","admin")
                .when().get(spartanurl + "/api/spartans/3");

        Assert.assertEquals(response.statusCode(),200);

        System.out.println(response.prettyPrint());

        Assert.assertEquals(response.contentType(),"application/json;charset=UTF-8");

        Assert.assertTrue(response.body().asString().contains("Fidole"));

    }

    /*
     Given no headers provided
     When Users sends GET request to /api/hello
     Then response status code should be 200
     And Content type header should be "text/plain;charset=UTF-8"
     And header should contain date
     And Content-Length should be 17
     And body should be "Hello from Sparta"
     */

    @Test
    public void helloTest(){

        //request
        Response response = when().get(spartanurl + "/api/hello");

        //verify status code
        Assert.assertEquals(response.statusCode(),200);
        //verify content-type
        Assert.assertEquals(response.contentType(),"text/plain;charset=UTF-8");
        //verify we have headers named Date
        Assert.assertTrue(response.headers().hasHeaderWithName("Date"));

        //------get any header with following methods
        System.out.println(response.header("Date"));
        System.out.println(response.getHeader("Content-Length"));

        //verify content length is 17
        Assert.assertEquals(response.getHeader("Content-Length"),"17");

        //verify body contains "Hello from Sparta"
        Assert.assertTrue(response.body().asString().contains("Hello from Sparta"));

    }

    /*TASK
    When user sends a get request to /api/spartans/3
    Then status code should be 200
    And content type should be application/json;charset=UTF-8
    and json body should contain Fidole
     */

    @Test
    public void test1Sinan(){

        Response response = RestAssured.given().auth().basic("admin","admin").
                                        get(spartanurl+"/api/spartans/3");

        System.out.println("response.statusCode() = " + response.statusCode());

        System.out.println("response.contentType() = " + response.contentType());

        System.out.println("response.body().asString() = " + response.body().asString());

        System.out.println("response.body().asString().contains(\"Fidole\") = " + response.body().asString().contains("Fidole"));

        System.out.println("response.header(\"Date\") = " + response.header("Date"));

        System.out.println("response.getHeader(\"Date\") = " + response.getHeader("Date"));

    }

}