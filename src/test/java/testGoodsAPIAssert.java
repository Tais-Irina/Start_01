//Домашнее задание №4 Тема 4. REST Assured
//ЧАСТЬ 2

import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;


//покрыть автотестами все эндпоинты из тестового API, содержащиеся в Swagger. Требования к автотестам:
//•	автотесты должны проверить все возможные коды ответа (500 не является штатным кодом ответа и проверке не подлежит);
//•	для каждого автотеста должен быть ассерт;
//•	должна быть разработана задача для Gradle, которая запускает все API-тесты;
//•	код должен быть запушен на GitHub в отдельной ветке.
public class testGoodsAPIAssert {
    String goodsAddEndPoint = "/add";
    String goodsList = "/list";

    RestAPIGoodBuilder api = new RestAPIGoodBuilder();

    //Тест 1 //POST goods/add //200 Success
    @Tag("APIGoods")
    @Test
    void testAPIPOSTGoodsAdd200()
    {
        //рандомное тело-запроса для post
        testRestAssured.RequestBody reqBody = testRestAssured.randomBody();

        Response response = api.post(goodsAddEndPoint, reqBody);
        GoodsAPIAssert.assertThat(response)
                        .statusCodeIsEquals(200)
                                .bodyFieldIsExists();
    }

    //Тест 2 //POST goods/add //400 Bad request
    @Tag("APIGoods")
    @Test
    void testAPIPOSTGoodsAdd400()
    {
        //отправить пустое тело запроса, получить 400
        testRestAssured.RequestBody reqBody = testRestAssured.randomBody();
        Response response = api.post(goodsAddEndPoint, """
                {}""");
        GoodsAPIAssert.assertThat(response)
                .statusCodeIsEquals(400);
    }

    //Тест 3 //GET goods/{id} //200 Success
    @Tag("APIGoods")
    @Test
    void testAPIGETGoodsID200()
    {
        //получить список товаров и поместить в список все из id
        //выбрать один id и проверить, что для него ответ 200
        Response  responceGetList = api.get(goodsList);
        List<Integer> listId = responceGetList.jsonPath().getList("goods.id", Integer.class);

        if ( listId.size() !=0){
            Integer id = listId.get(listId.size()-1);
            String endPoint = "/"+id.toString();
            Response responseGetId = api.get(endPoint);
            GoodsAPIAssert.assertThat(responceGetList)
                    .statusCodeIsEquals(200);
        } else System.out.println("Нет элементов в списке товаров!");
    }

    //Тест 4 //GET goods/{id}  //404 Not found
    @Tag("APIGoods")
    @Test
    void testAPIGETGoodsID404()
    {
        //получить список товаров и поместить в список все их id
        //проверить, что рандомный id не входит в полученный список, получить для него ответ 404 Not fount
        Response  responceGetList = api.get(goodsList);
        List<Integer> listId = responceGetList.jsonPath().getList("goods.id", Integer.class);

        if ( listId.size() !=0){
            Random random = new Random();
            Integer id = 73;
            while (listId.contains(id)){
                id = random.nextInt(0,100);
            }

            String endPoint = "/"+id.toString();
            Response responseGetId = api.get(endPoint);
            GoodsAPIAssert.assertThat(responseGetId)
                    .statusCodeIsEquals(404);
        } else System.out.println("Нет элементов в списке товаров!");
    }

    //Тест 5 //DELETE goods/{id}  //200 Success
    @Tag("APIGoods")
    @Test
    void testAPIDELETEGoodsID200()
    {
        //получить список товаров и поместить в список все их id
        //выбрать один id и проверить, что для него ответ 200
        Response  responceGetList = api.get(goodsList);
        List<Integer> listId = responceGetList.jsonPath().getList("goods.id", Integer.class);

        if ( listId.size() !=0){
            Integer id = listId.get(listId.size()-1);
            String endPoint = "/"+id.toString();
            Response responseDELELEId = api.delete(endPoint);
            GoodsAPIAssert.assertThat(responseDELELEId)
                    .statusCodeIsEquals(200);
        } else System.out.println("Нет элементов в списке товаров!");
    }

    //Тест 6 //DELETE goods/{id}  //404 Not found
    @Tag("APIGoods")
    @Test
    void testAPIDELETEGoodsID404()
    {
        //получить список товаров и поместить в список все их id
        //проверить, что рандомный id не входит в полученный список, получить для него ответ 404 Not fount
        Response  responceGetList = api.get(goodsList);
        List<Integer> listId = responceGetList.jsonPath().getList("goods.id", Integer.class);

        if ( listId.size() !=0){
            Random random = new Random();
            Integer id = 73;
            while (listId.contains(id)){
                id = random.nextInt(0,100);
            }

            String endPoint = "/"+id.toString();
            Response responseDELELEId = api.delete(endPoint);
            GoodsAPIAssert.assertThat(responseDELELEId)
                    .statusCodeIsEquals(404);
        } else System.out.println("Нет элементов в списке товаров!");
    }

    //Тест 7 //PATCH goods/{id}//200 Success
    @Tag("APIGoods")
    @Test
    void testAPIPATCHGoodsID200()
    {
        //получить список товаров и поместить в список все их id
        //выбрать один id и проверить, что для него + тело ответа  = ответ  200
        Response  responceGetList = api.get(goodsList);
        List<Integer> listId = responceGetList.jsonPath().getList("goods.id", Integer.class);

        if ( listId.size() !=0){
            Random random = new Random();
            //выбираем рандомный из имеющихся элементов
            Integer index = random.nextInt(0, listId.size()-1);
            Integer id = listId.get(index);
            String endPoint = "/"+id.toString();
            //рандомное тело-запроса для post
            testRestAssured.RequestBody reqBody = testRestAssured.randomBody();
            Response responsePATCHId = api.patch(endPoint, reqBody);

            GoodsAPIAssert.assertThat(responsePATCHId)
                    .statusCodeIsEquals(200);
        } else System.out.println("Нет элементов в списке товаров!");
    }

    //Тест 8 //PATCH goods/{id}//404 Not found
    @Tag("APIGoods")
    @Test
    void testAPIPATCHGoodsID404()
    {
        //получить список товаров и поместить в список все их id
        //проверить, что рандомный id не входит в полученный список, получить для него ответ 404 Not fount
        Response  responceGetList = api.get(goodsList);
        List<Integer> listId = responceGetList.jsonPath().getList("goods.id", Integer.class);

        if ( listId.size() !=0){
            Random random = new Random();
            Integer id = random.nextInt(0,100);
            while (listId.contains(id)){
                id = random.nextInt(0,100);
            }

            String endPoint = "/"+id.toString();
            //рандомное тело-запроса для post
            testRestAssured.RequestBody reqBody = testRestAssured.randomBody();
            Response responsePATCHId = api.patch(endPoint, reqBody);
            GoodsAPIAssert.assertThat(responsePATCHId)
                    .statusCodeIsEquals(404);
        } else System.out.println("Нет элементов в списке товаров!");
    }
    //Тест 9 //PATCH goods/{id}//400 Bad request
    @Tag("APIGoods")
    @Test
    void testAPIPATCHGoodsID400()
    {
        //получить список товаров и поместить в список все их id
        //выбрать один id и проверить, что для него + пустое ответа  = ответ  400
        Response  responceGetList = api.get(goodsList);
        List<Integer> listId = responceGetList.jsonPath().getList("goods.id", Integer.class);

        if ( listId.size() !=0){
            Random random = new Random();
            //выбираем рандомный из имеющихся элементов
            Integer index = random.nextInt(0, listId.size()-1);
            Integer id = listId.get(index);
            String endPoint = "/"+id.toString();
            //рандомное тело-запроса для post
            String reqBody = """
                    {}""";
            Response responsePATCHId = api.patch(endPoint, reqBody);

            GoodsAPIAssert.assertThat(responsePATCHId)
                    .statusCodeIsEquals(400);
        } else System.out.println("Нет элементов в списке товаров!");
    }

    //Тест 9 //GET goods/list page size  //200 Success
    @Tag("APIGoods")
    @Test
    void testAPIGETGoodsList200()
    {
        Response response = api.get(goodsList);
        GoodsAPIAssert.assertThat(response)
                .statusCodeIsEquals(200)
                .bodyFieldIsExists();
    }
}
