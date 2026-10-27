import io.restassured.response.Response;
import org.assertj.core.api.Assertions;

public class GoodsAPIAssert  {

    private Response response;

    //конструктор
    public GoodsAPIAssert(Response response){
        this.response = response;
    }

    //статическая точка входа
    public static GoodsAPIAssert assertThat(Response actual){
        return new GoodsAPIAssert(actual);
    }

    //метод проверяющий код ответа
    public GoodsAPIAssert statusCodeIsEquals(int expectedStatusCode){
        Assertions.assertThat(expectedStatusCode)
                .withFailMessage(String.format("Код ответа [%s], не соответствует ожидаемому [%s]", response.statusCode(),expectedStatusCode))
                .isEqualTo(response.statusCode());
        return this;
    }

    //метод проверяющий, что ответ не пустой
    public GoodsAPIAssert bodyFieldIsExists(){
        Assertions.assertThat(response)
                .withFailMessage("Ответ пустой, ожидаем JSON")
                .isNotNull();
        return this;
    }
}
