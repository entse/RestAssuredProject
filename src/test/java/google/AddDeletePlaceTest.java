package google;

import files.PayLoad;
import files.Resources;
import files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class AddDeletePlaceTest {

    Properties properties = new Properties();

    @BeforeTest
    public void getData() throws IOException {

        FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\java\\files\\env.properties");
        properties.load(fis);
        System.out.println(properties.getProperty("HOST"));

    }

    @Test
    public void AddandDeletePlace(){

        RestAssured.baseURI = properties.getProperty("HOST");

        Response response = given().
                queryParam("key", properties.getProperty("KEY")).
                body(PayLoad.getPostData()).
                when().
                post(Resources.placePostData()).
                then().assertThat().statusCode(200).
                and().contentType(ContentType.JSON).and().
                body("status", equalTo("OK")).
                extract().response();

        String placeId = ReusableMethods.rawToJSON(response).get("place_id");
        System.out.println(ReusableMethods.rawToJSON(response).get("place_id"));



        given().
                queryParam("key", properties.getProperty("KEY")).
                body("{\n" +
                        "  \"place_id\": \""+placeId+"\"\n" +
                        "}").
                when().
                post("maps/api/place/delete/json").
                then().assertThat().statusCode(200).
                and().contentType(ContentType.JSON).and().
                body("status", equalTo("OK"));

    }
}
