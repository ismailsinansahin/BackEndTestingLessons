package Day2;

import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

public class simpleGetRequest {

    String url = "http://34.228.41.120:8000/api/spartans";

    @Test
    public void test1() {

        Response response = RestAssured.get(url);

        //print the status code
        System.out.println("response.statusCode() = " + response.statusCode());

        //print the json body
        System.out.println("response.prettyPrint() = " + response.prettyPrint());

    }

    /*
    Given accept type is json
    When user sends get request to regions endpoint
    Then response status code must be 200
    And body is json format
    */

    @Test
    public void test2(){

        Response response = RestAssured.given().accept(ContentType.JSON).when().get(url);

        //verify response status code is 200
        Assert.assertEquals(response.statusCode(),200);

        //verify content type is application/json
        System.out.println("response.contentType() = " + response.contentType());
        Assert.assertEquals(response.contentType(),"application/json");

    }

    @Test
    public void test3(){

        RestAssured.given().accept(ContentType.JSON)
                   .when().get(url)
                   .then().assertThat().statusCode(200)
                   .and().contentType("application/json");

    }

    @Test
    public void test4(){

        Response response = given().accept(ContentType.JSON)
                .when().get(url + "/100");

        //verify status code
        Assert.assertEquals(response.statusCode(),200);

        //verify content type
        Assert.assertEquals(response.contentType(),"application/json");

        //verify body includes Eda
        Assert.assertTrue(response.body().asString().contains("Eda"));

    }

    @Test
    public void test4Sinan(){

        RestAssured.given().accept(ContentType.JSON).
        when().get(url+"/200").
        then().assertThat().statusCode(200).
        and().assertThat().contentType("application/json");

    }

}
