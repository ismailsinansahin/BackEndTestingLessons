package Day3;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utilities.ConfigurationReader;

import java.util.List;

public class SpartanTestWithPath {


    @BeforeClass
    public void beforeClass(){
        RestAssured.baseURI = ConfigurationReader.getProperty("spartanapi_url");
    }

    /*
     Given accept type is json
     And path param id is 10
     When user sends a get request to "api/spartans/{id}"
     Then status code is 200
     And content-type is "application/json;char"
     And response payload values match the following:
         id is 10,
         name is "Lorenza",
         gender is "Female",
         phone is 3312820936
    */
    @Test
    public void getOneSpartan_path(){
        Response response = given().accept(ContentType.JSON)
                .and().auth().basic("admin", "admin")
                .and().pathParam("id", 10)
                .when().get("/api/spartans/{id}");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");

        //print each key value from json body
        System.out.println(response.body().path("id").toString());
        System.out.println(response.path("name").toString());
        System.out.println(response.body().path("gender").toString());
        System.out.println(response.path("phone").toString());

        //save json values
        int id = response.path("id");
        String name = response.path("name");
        String gender = response.path("gender");
        long phone = response.path("phone");

        System.out.println("id = " + id);
        System.out.println("name = " + name);
        System.out.println("gender = " + gender);
        System.out.println("phone = " + phone);

        //assert one by one
        assertEquals(id,10);
        assertEquals(name,"Lorenza");
        assertEquals(gender,"Male");
        assertEquals(phone, 3312821907L);

    }

    @Test
    public void getAllSpartansWithPath(){

        Response response = given().auth().basic("admin", "admin").
                when().get("/api/spartans");

        assertEquals(response.statusCode(),200);

        //verify content type
        assertEquals(response.getHeader("Content-Type"),"application/json");

        //verify a header exists or not
        assertTrue(response.headers().hasHeaderWithName("Date"));

        //taking first element id by using path
        int firstElementid = response.path("id[0]");
        System.out.println("firstElementid = " + firstElementid);

        //taking elements attributes by using path
        int fourthElementid = response.path("id[4]");
        System.out.println("fourthElementid = " + fourthElementid);

        //taking the last element first name
        String lastFirstName = response.path("name[-1]");
        System.out.println("lastFirstName = " + lastFirstName);

        //print all first names from Spartans
        List<String> names = response.path("name");
        System.out.println("names = " + names);

        //print all phone numbers from Spartans
        List<String> phones = response.path("phone");
        System.out.println("phones = " + phones);

    }

}