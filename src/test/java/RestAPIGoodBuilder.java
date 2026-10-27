import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static io.restassured.filter.log.LogDetail.ALL;

public class RestAPIGoodBuilder {
    private final String userName = "admin";
    private final String pass = "secret123";
    private final String baseUri = "http://localhost:8080";
    private final String basePath = "/goods";

//билдер запроса
    RequestSpecification basicRQ = new RequestSpecBuilder()
            .setBaseUri(baseUri)
            .setAuth(RestAssured.basic(userName,pass))
            .setBasePath(basePath)
            .setContentType(ContentType.JSON)
            .log(ALL)
            .build();

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
    public Response delete(String endPointPath){
        Response response =
                given()
                        .spec(basicRQ)
                        .when()
                        .delete(endPointPath)
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
