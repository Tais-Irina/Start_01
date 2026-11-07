import Page.MainPage;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.impl.CollectionElement;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;
import static java.lang.Thread.sleep;

public class AddProductBasketTest extends BaseTests{

    //Проверяем заголовок страницы
    @Test
    public void checkTextleText()
    {
        MainPage mainPage = page(MainPage.class);

        Assertions.assertThat(mainPage.getTitleText())
                .withFailMessage("Текст заголовка не соответствует ожидаемому")
                .isEqualTo("\uD83D\uDECD SmartShop");
    }

    //Тест 1 Проверить:
    //Кликнуть по кнопке "В корзину"
    //Пользователь видит уведомление, что товар добавлен в корзину.
    //Пользователь видит, что счетчик на кнопке "Корзина" увеличился на 1.
    @Test
    public void addProductToBasket() throws InterruptedException{
        MainPage mainPage = page(MainPage.class);

        //нажимаем на кнопку добавить coffee
        mainPage.clickToProduct();
        //получаем всплывающее сообщение
        SelenideElement popUp = mainPage.getPopUp();
        popUp.shouldBe(visible);
        popUp.shouldHave(text("coffee (1 шт.) добавлен в корзину"));
        Thread.sleep(6000);
        popUp.shouldBe(hidden);

        Assertions.assertThat(mainPage.getCartCount())
                .withFailMessage("Количество элементов в корзине не соответствует ожидаемому")
                .isEqualTo("1");
    }

    //Тест 2 Проверить:
    //Кликнуть по кнопке "Корзина"
    //Пользователь видит popUp корзины
    //Пользователь видит товар, добавленный на ш1.
    //Цена товара соответствует цене товара из списка
    //Общая сумма в корзине соответствует цене товара
   @Test
    public void checkBasketContainer(){
        MainPage mainPage = page(MainPage.class);
       //нажимаем на кнопку добавить coffee
       mainPage.clickToProduct();
       //нажимаем на кнопку Корзина
        mainPage.clickToBasket();

        Assertions.assertThat(mainPage.getBasketWidget().isDisplayed())
                .withFailMessage("Корзина не отображается")
                .isTrue();

        Assertions.assertThat(mainPage.getCartItem1Text())
                .withFailMessage(String.format("Элемент в корзине [%s] не coffee", mainPage.getCartItem1Text()))
                .contains("coffee");

        Assertions.assertThat(mainPage.getTotalPriceText()).
                withFailMessage("Цена не 100")
                .isEqualTo("100");
   }

   @Test
   public void checkElements(){
       MainPage mainPage = page(MainPage.class);
       ElementsCollection goodNames = mainPage.getListGoodNames();
       ElementsCollection goodPrices = mainPage.getListGoodPrices();
       printElements(goodNames);
       printElements(goodPrices);
       System.out.println("Наиментование товара с ценой 25: "+mainPage.getnameGoodPrice25());
       System.out.println("Цена товара Стакан: " + mainPage.priceGoodStakan());
   }

   public void printElements(ElementsCollection elements){
       List<String> names = new ArrayList<>();
       for(SelenideElement element : elements) {
           names.add(element.getText());
       }
       System.out.println("Элементы и текст на странице: "+ names );
   }
}
