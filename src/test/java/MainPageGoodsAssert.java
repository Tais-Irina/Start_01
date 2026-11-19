import com.codeborne.selenide.*;
import com.codeborne.selenide.impl.CollectionElement;
import com.codeborne.selenide.impl.ElementCommunicator;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.Alert;

import java.util.Objects;

import static com.codeborne.selenide.Condition.*;

public class MainPageGoodsAssert extends AbstractAssert<MainPageGoodsAssert, MainPageGoods> {

    public MainPageGoodsAssert(MainPageGoods actual){
        super(actual, MainPageGoodsAssert.class);
    }

    //1.Кнопка корзина
    //Базовые проверки: кнопку корзина видно
    public MainPageGoodsAssert cardButtonIsVisible(){
        actual.cartButton.should(visible);
        return this;
    }
    //2.Кнопка Администрирование
    //Базовые проверки: кнопку Администрирование видно
    public MainPageGoodsAssert adminButtonIsVisible(){
        actual.adminButton.should(visible);
        return this;
    }
    //3.Список с карточками товаров
    //Базовые проверки для списка карточек: каждую каротчку видно
    public MainPageGoodsAssert productListItemsIsVisible(){
        ElementsCollection elements = actual.productList;
        for (SelenideElement element:elements){
            element.should(visible);
        }
        return this;
    }
    //Проверки суммы товаров и общей суммы в корзине
    public MainPageGoodsAssert totalSumIsEqual(Double actualSum, Double expectedSum){
        //actual.cartPage.totalSum.shouldBe(Condition.attribute("data-price", expectedSum.toString()));
        Assertions.assertThat(actualSum)
                .as("Фактическая общая сумма в корзине не равна ожидаемой")
                .isEqualTo(expectedSum);
        return this;
    }
//    //Получить цену товара по index
//    public Double getGoodPrice(int index){
//        return Double.parseDouble(Objects.requireNonNull(productList.get(index).getAttribute("data-price")));
//    }


    //4. Кнопки добавить товар в корзину
    //Базовые проверки для кнопок добавить товар в корзину:на каждой кнопке текст "В корзину"
    public MainPageGoodsAssert addToCartButtonsContainsText(String expectedText) {
        ElementsCollection elements = actual.addtoCartButtonList;
        for (SelenideElement element : elements) {
            element.shouldHave(text(expectedText));
        }
        return this;
    }

    //5. Поля ввода для колличества товара
    //Базовые проверки для поля ввода: значение по умолчанию равно 1
    public MainPageGoodsAssert qtyInputListContainsText(String expectedValue){
        ElementsCollection elements = actual.qtyInputList;
        for(SelenideElement element:elements){
            element.shouldHave(attribute("value",expectedValue));
        }
        return this;
    }

    //6. Кнопки увеличения количества товара в карточке
    //Базовые проверки для Кнопок увеличения количества товара в карточке: все кнопки видно
    public MainPageGoodsAssert qtyAddButtonIsVisible() {
        ElementsCollection elements = actual.qtyAddButton;
        for (SelenideElement element : elements) {
            element.shouldHave(visible);
        }
        return this;
    }

    //Уведомления
    //Проверки текста уведомлений
    public MainPageGoodsAssert toastTextIsContain(String expectedText){
        actual.toasts.shouldHave(CollectionCondition.containExactTextsCaseSensitive(expectedText));
        return this;
    }

    //Проверки наименования первого товара на соответствие ожидаемым значениям
    public  MainPageGoodsAssert nameFirstGoodsEqual(String expectedName){
        Assertions.assertThat(actual.getNameFirstGood())
                .as("Наименование товара в первой карточке не соответствует ожидаемому")
                .isEqualTo(expectedName);
        return this;
    }

    //Проверки цены первого товара на соответствие ожидаемым значениям
    public  MainPageGoodsAssert priceFirstGoodsEqual(String expectedPrice){
        Assertions.assertThat(actual.getPriceFirstGood())
                .as("Цена товара в первой карточке не соответствует ожидаемой")
                .isEqualTo(expectedPrice);
        return this;
    }
}
