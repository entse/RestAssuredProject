package google;

import files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GetDataTest {

    @Test
    public void getDataTest() {

        RestAssured.baseURI = "https://maps.googleapis.com";

        Response response = given().
                param("location", "-33.8670,151.1957").
                param("radius", "500").
                param("key", "AIzaSyByuXXUkAZturGVnqaat91mAGjKbZ3fN3U").
                log().all().
                when().
                get("/maps/api/place/nearbysearch/json").
                then().assertThat().statusCode(200).
                and().contentType(ContentType.JSON).and().
                body("results[0].name", equalTo("Sydney")).and().
                body("results[0].place_id", equalTo("ChIJP3Sa8ziYEmsRUKgyFmh9AQM")).and().
                header("Server", "scaffolding on HTTPServer2").
                extract().response();

        JsonPath js = ReusableMethods.rawToJSON(response);
        System.out.println(js.get("results.size()"));
        System.out.println(Integer.parseInt(js.get("results.size()").toString()));

        for (int i = 0; i < Integer.parseInt(js.get("results.size()").toString());i++){
            //System.out.println("result.name " + i);
            System.out.println("results["+i+"].name: " + js.get("results["+i+"].name"));
        }
    }
}

