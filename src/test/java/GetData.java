import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GetData {
    String apiLink = "https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22";

    @Test
    public void testResponseCode() throws IOException {
        Response response = RestAssured.get(apiLink);

        //This part for checking status code
        int statusCode = response.getStatusCode();
        System.out.println("Status code is: "+statusCode);
        Assert.assertEquals(statusCode, 200);

        //For getting raw data as text
        String responseBody = response.asString();
        System.out.println("Response body in text: "+responseBody);

        //For getting response time
        System.out.println("Response time: " +response.getTime()+ " MS");
        System.out.println("Content type: " +response.contentType());

        File fileObj = new File("response_json\\response.json");

        if(fileObj.createNewFile()) {
            FileWriter myWriter = new FileWriter("response_json\\response.json");
            myWriter.write(responseBody);
            myWriter.close();
        }else {
            System.out.println("Failed");
        }

        //Comparison between 2 json files
        String expectedJson = FileUtils.readFileToString(new File("reference_json/reference.json"));
        String actualJson = FileUtils.readFileToString(new File("response_json/response.json"));

        Assert.assertEquals(actualJson, expectedJson);

        System.out.println("Expected: "+expectedJson);
        System.out.println("Actual: "+actualJson);

    }
}
