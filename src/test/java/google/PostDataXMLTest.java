package google;

import files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PostDataXMLTest {

    @Test
    public void postData() throws IOException {

        String postData = generateStringFromRecources(System.getProperty("user.dir") + "\\src\\test\\java\\resources\\postData.xml");
        RestAssured.baseURI = "https://maps.googleapis.com";

        Response res = given().
                queryParam("key", "AIzaSyByuXXUkAZturGVnqaat91mAGjKbZ3fN3U").
                body(postData).
                when().
                post("/maps/api/place/add/xml").
                then().assertThat().statusCode(200).
                and().contentType(ContentType.XML).and().
                extract().response();


        System.out.println(ReusableMethods.rawToXML(res).get("PlaceAddResponse.place_id"));


    }

    public static String generateStringFromRecources(String path) throws IOException{
        return new String (Files.readAllBytes(Paths.get(path)));
    }
}

