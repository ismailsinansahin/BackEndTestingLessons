package Assignments;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import utilities.ConfigurationReader;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;

public class Homework1 {

    @BeforeClass
    public void beforeClass(){
        RestAssured.baseURI = ConfigurationReader.getProperty("hrapi_url");
    }

    /*
    Q1:
    - Given accept type is Json
    - Path param value- US
    - When users sends request to /countries
    - Then status code is 200
    - And Content - Type is Json
    - And country_id is US
    - And Country_name is United States of America
    - And Region_id is 2
     */

    @Test
    public void q1WithPathMethod(){

        Response response = given().accept(ContentType.JSON)
                .queryParam("q", "{\"country_id\":\"US\"}")
                .when().get("/countries");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");

        String countryId = response.path("items.country_id[0]");
        String countryName = response.path("items.country_name[0]");
        int regionId = response.path("items.region_id[0]");

        System.out.println("countryId = " + countryId);
        System.out.println("countryName = " + countryName);
        System.out.println("regionId = " + regionId);

        assertEquals(countryId,"US");
        assertEquals(countryName,"United States of America");
        assertEquals(regionId,2);

    }

    @Test
    public void q1WithJsonPathMethod(){

        Response response = given().accept(ContentType.JSON)
                .queryParam("q", "{\"country_id\":\"US\"}")
                .when().get("/countries");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");

        JsonPath json = response.jsonPath();

        String countryId = json.getString("items.country_id[0]");
        String countryName = json.getString("items.country_name[0]");
        int regionId = json.getInt("items.region_id[0]");

        System.out.println("countryId = " + countryId);
        System.out.println("countryName = " + countryName);
        System.out.println("regionId = " + regionId);

        assertEquals(countryId,"US");
        assertEquals(countryName,"United States of America");
        assertEquals(regionId,2);

    }

    @Test
    public void q1WithHamcrestMatchersMethod(){

        given().accept(ContentType.JSON)
                .queryParam("q","{\"country_id\":\"US\"}")
                .when().get("/countries")
                .then().assertThat().statusCode(200)
                .and().assertThat().contentType("application/json")
                .and().assertThat().body("items.country_id[0]",equalTo("US"),
                         "items.country_name[0]",equalTo("United States of America"),
                                              "items.region_id[0]",equalTo(2));

    }

    @Test
    public void q1WithJavaCollections(){
        Response response = given().contentType(ContentType.JSON)
                .and().queryParam("q", "{\"country_id\":\"US\"}")
                .when().get("/countries");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");

        Map<String,Object> fullBody = response.body().as(Map.class);
        System.out.println("fullBody = " + fullBody);

        List<Map<String,Object>> itemsList = (List<Map<String, Object>>) fullBody.get("items");
        System.out.println("itemsList = " + itemsList);

        String countryID = (String)itemsList.get(0).get("country_id");
        System.out.println("countryID = " + countryID);

        String countryName = (String)itemsList.get(0).get("country_name");
        System.out.println("countryName = " + countryName);

        int regionID = (Integer)itemsList.get(0).get("region_id");
        System.out.println("regionID = " + regionID);

        assertEquals(countryID,"US");
        assertEquals(countryName,"United States of America");
        assertEquals(regionID,2);

    }

    /*
    Q2:
    - Given accept type is Json
    - Query param value - q={"department_id":80}
    - When users sends request to /employees
    - Then status code is 200
    - And Content - Type is Json
    - And all job_ids start with 'SA'
    - And all department_ids are 80
    - Count is 25
    */

    @Test
    public void q2WithPathMethod(){

        Response response = given().accept(ContentType.JSON)
                .queryParam("q","{\"department_id\":80}")
                .when().get("/employees");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");

        List<String> jobIds = response.path("items.job_id");
        System.out.println("jobIds.toString() = " + jobIds.toString());
        for (String each : jobIds){
            assertTrue(each.startsWith("SA"));
        }

        List<Integer> departmentIds = response.path("items.department_id");
        System.out.println("departmentIds.toString() = " + departmentIds.toString());
        for (int each : departmentIds){
            assertEquals(each,80);
        }

        int count = response.path("count");
        System.out.println("count = " + count);
        assertEquals(count,25);

    }

    @Test
    public void q2WithJsonPathMethod(){

        Response response = given().accept(ContentType.JSON)
                .queryParam("q","{\"department_id\":80}")
                .when().get("/employees");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");

        JsonPath json = response.jsonPath();

        List<String> jobIds = json.getList("items.job_id");
        System.out.println("jobIds.toString() = " + jobIds.toString());
        for (String each : jobIds){
            assertTrue(each.startsWith("SA"));
        }

        List<Integer> departmentIds = json.getList("items.department_id");
        System.out.println("departmentIds.toString() = " + departmentIds.toString());
        for (int each : departmentIds){
            assertEquals(each,80);
        }

        int count = json.getInt("count");
        System.out.println("count = " + count);
        assertEquals(count,25);

    }

    @Test
    public void q2WithHamcrestMatchersMethod(){

        given().accept(ContentType.JSON)
                .queryParam("q","{\"department_id\":80}")
                .when().get("/employees")
                .then().statusCode(200)
                .and().contentType("application/json")
                .and().assertThat().body("items.job_id",everyItem(startsWith("SA")),
                          "items.department_id",everyItem(equalTo(80)),
                                              "count",equalTo(25));

    }

    @Test
    public void q2WithJavaCollections(){

        Response response = given().accept(ContentType.JSON)
                .queryParam("q","{\"department_id\":80}")
                .when().get("/employees");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");

        Map<String,Object> fullBody = response.body().as(Map.class);
        System.out.println("fullBody = " + fullBody);

        List<Map<String,Object>> itemsList = (List<Map<String, Object>>) fullBody.get("items");
        System.out.println("itemsList = " + itemsList);

        for (Map<String,Object> each : itemsList){
            String jobID = (String)each.get("job_id");
            System.out.println(jobID);
            assertTrue(jobID.startsWith("SA"));
        }

        for (Map<String,Object> each : itemsList){
            int departmentID = (Integer)each.get("department_id");
            System.out.println(departmentID);
            assertEquals(departmentID,80);
        }

        int count = (Integer)fullBody.get("count");
        System.out.println("count = " + count);
        assertEquals(count,25);

    }

    /*
    Q3:
    - Given accept type is Json
    - Query param value q= region_id 3
    - When users sends request to /countries
    - Then status code is 200
    - And all regions_id is 3
    - And count is 6
    - And hasMore is false
    - And Country_name are;
    Australia,China,India,Japan,Malaysia,Singapore
    */

    @Test
    public void q3WithPathMethod(){

        Response response = given().accept(ContentType.JSON)
                .and().queryParam("q", "{\"region_id\":3}")
                .when().get("/countries");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");

        List<Integer> regionIds = response.path("items.region_id");
        System.out.println("regionIds.toString() = " + regionIds.toString());
        for (int each : regionIds){
            assertEquals(each,3);
        }

        int count = response.path("count");
        System.out.println("count = " + count);
        assertEquals(count,6);

        boolean hasMore = response.path("hasMore");
        System.out.println("hasMore = " + hasMore);
        assertFalse(hasMore);

        List<String> expectedCountryNames = Arrays.asList("Australia", "China", "India", "Japan", "Malaysia", "Singapore");

        List<String> actualCountryNames = response.path("items.country_name");
        System.out.println("expectedCountryNames = " + expectedCountryNames);
        System.out.println("actualCountryNames = " + actualCountryNames);
        assertEquals(expectedCountryNames, actualCountryNames);

    }

    @Test
    public void q3WithJsonPathMethod(){

        Response response = given().accept(ContentType.JSON)
                .and().queryParam("q", "{\"region_id\":3}")
                .when().get("/countries");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");

        JsonPath json = response.jsonPath();

        List<Integer> regionIds = json.getList("items.region_id");
        System.out.println("regionIds.toString() = " + regionIds.toString());
        for (int each : regionIds){
            assertEquals(each,3);
        }

        int count = json.getInt("count");
        System.out.println("count = " + count);
        assertEquals(count,6);

        boolean hasMore = json.getBoolean("hasMore");
        System.out.println("hasMore = " + hasMore);
        assertFalse(hasMore);

        List<String> expectedCountryNames = Arrays.asList("Australia", "China", "India", "Japan", "Malaysia", "Singapore");
        List<String> actualCountryNames = json.getList("items.country_name");
        System.out.println("expectedCountryNames = " + expectedCountryNames);
        System.out.println("actualCountryNames = " + actualCountryNames);
        assertEquals(expectedCountryNames, actualCountryNames);

    }

    @Test
    public void q3WithHamcrestMatchersMethod(){

        List<String> expectedCountryNames = Arrays.asList("Australia", "China", "India", "Japan", "Malaysia", "Singapore");

        given().accept(ContentType.JSON)
                .and().queryParam("q", "{\"region_id\":3}")
                .when().get("/countries")
                .then().statusCode(200)
                .and().contentType("application/json")
                .and().assertThat().body("items.region_id",everyItem(equalTo(3)),
                         "count",equalTo(6),
                                              "hasMore",equalTo(false),
                                              "items.country_name",equalTo(expectedCountryNames));

    }

    @Test
    public void q3WithJavaCollections(){

        List<String> expectedCountryNames = Arrays.asList("Australia", "China", "India", "Japan", "Malaysia", "Singapore");
        List<String> actualCountryNames = new ArrayList<>();

        Response response = given().accept(ContentType.JSON)
                .and().queryParam("q", "{\"region_id\":3}")
                .when().get("/countries");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");

        Map<String,Object> fullBody = response.body().as(Map.class);
        System.out.println("fullBody = " + fullBody);

        List<Map<String,Object>> itemsList = (List<Map<String, Object>>) fullBody.get("items");
        System.out.println("itemsList = " + itemsList);

        for (Map<String,Object> each : itemsList){
            int regionID = (Integer)each.get("region_id");
            System.out.println(regionID);
            assertEquals(regionID,3);
        }

        int count = (Integer)fullBody.get("count");
        System.out.println("count = " + count);
        assertEquals(count,6);

        boolean hasMore = (Boolean)fullBody.get("hasMore");
        System.out.println("hasMore = " + hasMore);
        assertFalse(hasMore);

        for (Map<String,Object> each : itemsList){
            String countryName = (String)each.get("country_name");
            actualCountryNames.add(countryName);
        }
        System.out.println(actualCountryNames);

        assertEquals(expectedCountryNames,actualCountryNames);

    }

}
