
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Test;
import org.testng.Assert;


public class TestAPI
{
    @Test
    public void WeatherDetails(){

        String city = "Buxar";

        //Specify Base URI
        RestAssured.baseURI="http://api.openweathermap.org";

        //Request Object
        RequestSpecification httprequest = RestAssured.given();

        //Response Object
        Response response = httprequest.request(Method.GET,"/data/2.5/weather?q="+city+"&APPID=f3c42f78fb643fa84d48f27b3d31a166");

        //Print the responseBody
        String ResponseBody = response.getBody().asString();
        System.out.println(ResponseBody);
        JsonPath jsonPath = response.jsonPath();
        String cityname = jsonPath.get("name");


        //BodyData Validation
        Assert.assertEquals(cityname,city);
        Assert.assertTrue(ResponseBody.contains("IN"));

        //status code validation
        int statusCode=response.getStatusCode();
        System.out.println("Status code is: "+statusCode);
        Assert.assertEquals(statusCode, 200);

        //status line verification
        String statusLine=response.getStatusLine();
        System.out.println("Status line is: "+statusLine);
        Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");


        //Print all the header Information
        Headers allHeader = response.headers();
        for (Header header: allHeader)
        {
            System.out.println(header.getName()+"  "+header.getValue());
        }

        //Header Validation
        String Date = response.getHeader("Date");
        System.out.println(Date);

        String ServerName = response.getHeader("Server");
        Assert.assertEquals(ServerName,"openresty");

    }
}
