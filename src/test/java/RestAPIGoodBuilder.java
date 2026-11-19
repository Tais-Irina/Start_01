import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static io.restassured.filter.log.LogDetail.ALL;

public class RestAPIGoodBuilder {

    String configPath = "src/test/resources/config.properties";
    Properties prop;
    String userName;
    String pass;
    String baseUri;
    String basePath;
    //билдер запроса
    RequestSpecification basicRQ;

    //конструктор
    public RestAPIGoodBuilder(){
        prop = new Properties();
        try {
            prop.load(new FileInputStream(configPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        userName = prop.getProperty("user.name");
        pass = prop.getProperty("user.password");
        baseUri = prop.getProperty("base.url");
        basePath = prop.getProperty("base.path");
        basicRQ = new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setAuth(RestAssured.basic(userName,pass))
                .setBasePath(basePath)
                .setContentType(ContentType.JSON)
                .log(ALL)
                .build();
    }

    //шаблон POST add запроса
    public Response post(String endPointPath, Object body){
        Response response =
                given()
                        .spec(basicRQ)
                        .body(body)
                        .when()
                        .post(endPointPath)
                        .then().log().all().extract().response();
        return response;
    }

    //шаблон GET list запроса
    public Response get(String endPointPath){
        Response response =
                given()
                        .spec(basicRQ)
                        .when()
                        .get(endPointPath)
                        .then().log().all().extract().response();
        return response;
    }

    //шаблон DELETE id запроса
    public Response delete(Integer endPointPath){
        Response response =
                given()
                        .spec(basicRQ)
                        .when()
                        .delete("/"+endPointPath.toString())
                        .then().log().all().extract().response();
        return response;
    }

    //шаблон PATCH id запроса
    public Response patch(String endPointPath, Object body){
        Response response =
                given()
                        .spec(basicRQ)
                        .body(body)
                        .when()
                        .patch(endPointPath)
                        .then().log().all().extract().response();
        return response;
    }
}
