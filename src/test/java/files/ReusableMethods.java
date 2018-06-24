package files;

import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class ReusableMethods {

    public static XmlPath rawToXML(Response response){

        XmlPath xmlPath = new XmlPath(response.asString());
        return xmlPath;

    }


    public static JsonPath rawToJSON(Response response){

        JsonPath js = new JsonPath(response.asString());
        return js;

    }
}
