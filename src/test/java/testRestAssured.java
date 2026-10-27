import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apiguardian.api.API;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static io.restassured.filter.log.LogDetail.ALL;
import static org.hamcrest.Matchers.*;

//Домашнее задание №4 Тема 4. REST Assured
//Часть первая
public class testRestAssured {



    //Метод 1, который делает запрос к эндпоинту GET /goods/list учебного API:
    //Метод с использованием инструкций given(), when(), then() — проверить:
    //код ответа;
    //что тело ответа пустое;
    @Test
    void raGoodsListTestFirst(){
        given()
                .baseUri("http://localhost:8080")
                .queryParam("page", 0)
                .queryParam("size", 100)
                .log().all()
                .when()
                .get("/goods/list")
                .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("goods.isEmpty()", is(true));
    }

    //Метод 2, который делает запрос к эндпоинту GET /goods/list учебного API:
    //Метод с использованием RequestSpecification — проверить:
    //код ответа;
    //что тело ответа пустое;

    private RequestSpecification basicRQ = new RequestSpecBuilder()
            .setBaseUri("http://localhost:8080")
            .setBasePath("/goods")
            .setContentType(ContentType.JSON)
            .log(ALL)
            .build();

    @Test
    void raGoodsListTestSecond() {
        given()
                .spec(basicRQ)
                .queryParam("page", 0)
                .queryParam("size", 100)
                .get("/list")
                .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("goods.isEmpty()", is(true));
    }

    //Метод 3, который будет создавать один товар через эндпоинт POST /goods/add и проверять,
    // что GET /goods/list вернул его в списке через встроенные проверки REST Assured.

    public record  RequestBody(String name, Double price){}

    //метод, который создает рандомное тело запроса для создания товара
    public static RequestBody randomBody(){
        //создаем рандомное имя товара
        Random random = new Random();
        Integer nameRandom = random.nextInt(1000,9999);
        String name = "butter"+nameRandom.toString();

        //Рандомная цена
        Double price = random.nextInt(1, 10000)/100.0;
        //тело-запроса для post
        RequestBody reqBody = new RequestBody(name, price);
        return reqBody;
    }

    @Test
    void raGoodsAddTestFirst(){
        RequestBody reqBody = randomBody();
        //добавляем товар рандомный товар
        given()
                .spec(basicRQ)
                .auth()
                .basic("admin", "secret123")
                .body(reqBody)//добавляем рандомный товар
                .when()
                .post("/add")
                .then()
                .log().all();

        //проверяем, что в возвращаемом списке есть рандомный товар
        given()
                .spec(basicRQ)
                .queryParam("page", 0)
                .queryParam("size", 1000)
                .when()
                .get("/list")
                .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("goods.name", hasItem(reqBody.name));
    }


    //Метод 4, который будет создавать один товар через эндпоинт POST /goods/add и проверять,
    // что GET /goods/list вернул его в списке через AssertJ.
@Test
void raGoodsAddTestSecond(){
    RequestBody reqBody = randomBody();
    //добавляем рандомный товар

    given()
            .spec(basicRQ)
            .auth()
            .basic("admin", "secret123")
            .body(reqBody)
            .when()
            .post("/add")
            .then()
            .log().all();

    //проверяем, что в возвращаемом списке есть рандомный товар
    Response response =
            given()
                    .spec(basicRQ)
                    .queryParam("page", 0)
                    .queryParam("size", 1000)
                    .when()
                    .get("/list")
                    .then().log().all().extract().response();

    Assertions.assertThat(response.jsonPath().getList("goods.name", String.class))
            .as("Список товаров должен содержать Jam")
            .contains(reqBody.name);
    }
}
