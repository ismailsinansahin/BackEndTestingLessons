package Day4;

import static io.restassured.RestAssured.*;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.List;

import static org.testng.Assert.*;

public class hrApiWithPath {

    @BeforeClass
    public void beforeClass(){
        baseURI = ConfigurationReader.getProperty("hrapi_url");
    }

    @Test
    public void getCountriesWithPath(){

        Response response = given().queryParam("q","{\"region_id\":\"2\"}").
                            when().get("/countries");

        assertEquals(response.statusCode(),200);

        //print the country names
        System.out.println("response.path(\"items.country_name\") = " + response.path("items.country_name"));

        //print count value
        System.out.println("response.path(\"count\") = " + response.path("count"));

        //print hasMore
        System.out.println(response.path("hasMore").toString());

        String firstCountryId = response.path("items.country_id[0]");
        System.out.println("firstCountryId = " + firstCountryId);

        //get the second country name
        String secondCountryName = response.path("items.country_name[1]");
        System.out.println("secondCountryName = " + secondCountryName);

        //get the second link
        String link2 = response.path("items.links[2].href[0]");
        System.out.println("link2 = " + link2);

        //assert that all regions id's equals to 2
        List<Integer> regionIDs = response.path("items.region_id");
        for (int each : regionIDs){
            System.out.println(each);
            assertEquals(each, 2);
        }

    }

    @Test
    public void test2(){

        Response response = given().accept(ContentType.JSON)
                .and().queryParam("q", "{\"job_id\": \"IT_PROG\"}")
                .when().get("/employees");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");

        List<String> jobIDs = response.path("items.job_id");
        for (String each : jobIDs){
            System.out.println(each);
            assertEquals(each,"IT_PROG");
        };

    }

}
