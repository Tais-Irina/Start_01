import com.codeborne.selenide.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.TimeoutException;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.CollectionCondition.*;

//Задача 2: переписать тесты из задачи 1 на Selenide и добавить ещё один автотест. В коде, связанном с этим заданием, не должно быть методов из Selenium — только Selenide. Также все проверки должны быть выполнены методами Selenide (shouldBe).
//Список кейсов:
//2.1. Добавить товар через админку, выйти на витрину и проверить, что товар отображается.
//2.2. Добавить товар в корзину и проверить, что он отображается.
//2.3. Попытаться войти в админку с неверным логином и паролем.
//2.4. Проверить сохранение товаров в корзине после обновления страницы.
//2.5. Добавить в корзину товаров более чем на 300 рублей и нажать на кнопку «Оформить заказ». Проверить, что отображается JS Alert.

public class SelenideTests {
    String userName = "admin";
    String password = "secret123";
    String goodName = "cheese";
    String price = "12.5";

    @BeforeEach
    void setup(){
        Selenide.open("http://localhost:8080");
       // Configuration.timeout = 10000;
    }

    @AfterEach
    void tearDown() {
        Selenide.closeWebDriver();
    }

    //2.1. Добавить товар через админку, выйти на витрину и проверить, что товар отображается.
    @Test
    void selenideTest21() {
        //нажать кнопку Админконсоль
        $x("//*[@class='btn-outline']").click();
        sleep(1000);
        //Ввести логин
        $x("//*[@name='username']").sendKeys(userName);
        sleep(1000);
        //Ввести пароль
        $x("//*[@id='password']").sendKeys(password);
        sleep(1000);
        //нажать sign in
        $x("//*[@type='submit']").click();
        sleep(1000);
        //ввести наименование товара
        $x("//*[@id='n-name']").sendKeys(goodName);
        sleep(1000);
        //ввести цену товара
        $x("//*[@id='n-price']").sendKeys(price);
        sleep(1000);
        //Нажать Создать
        $x("//*[@id='add-btn']").click();
        sleep(1000);
        //Нажать на "Вернуться на сайт
        $x("//*[@href='/']").click();
        sleep(1000);
        // Проверка наличия карточки товара на странице
        String str = String.format("//*[@data-name='%s'][@class='product-card']", goodName);
        $x(str).should(exist);
    }

    //2.2. Добавить товар в корзину и проверить, что он отображается.
    @Test
    void selenideTest22(){
        //Нажать на кнопку добавления товара  goodName в корзину
        String str = String.format("//*[@data-action='add-to-cart'][@data-name='%s']", goodName);
        $x(str).click();
        sleep(1000);
        //Нажать на кнопку корзина
        $x("//*[@id='open-cart-btn']").click();
        sleep(1000);
        //Найти в списке товаров в корзине последний добавленный и проверить, что это goodName
        $$x("//*[@class='cart-item']").last().shouldHave(Condition.text(goodName));
        sleep(1000);
    }

    //2.3. Попытаться войти в админку с неверным логином и паролем.
    @Test
    void selenideTest23(){
        //Нажать кнопку Администрирование
        $x("//*[@class='btn-outline']").click();
        sleep(1000);
        //заполнить логин не верным логином
        $x("//*[@name='username']").sendKeys("userName");
        sleep(1000);
        //запольнить парль не верным паролем
        $x("//*[@id='password']").sendKeys("password");
        sleep(1000);
        //Нажать кнопку залогиниться
        $x("//*[@type='submit']").click();
        sleep(1000);
        //Найти сообщение  о неверных учетных данных
        $x("//*[@class='alert alert-danger']").shouldHave(text("Неверные учетные данные пользователя"));
    }

//2.4. Проверить сохранение товаров в корзине после обновления страницы.
    @Test
    void selenideTest24(){
        //добавление товара через админку
        //нажать кнопку Админконсоль
        $x("//*[@class='btn-outline']").click();
        sleep(1000);
        //Ввести логин
        $x("//*[@name='username']").sendKeys(userName);
        sleep(1000);
        //Ввести пароль
        $x("//*[@id='password']").sendKeys(password);
        sleep(1000);
        //нажать sign in
        $x("//*[@type='submit']").click();
        sleep(1000);
        //ввести наименование товара
        $x("//*[@id='n-name']").sendKeys(goodName);
        sleep(1000);
        //ввести цену товара
        $x("//*[@id='n-price']").sendKeys(price);
        sleep(1000);
        //Нажать Создать
        $x("//*[@id='add-btn']").click();
        sleep(1000);
        //Нажать на "Вернуться на сайт
        $x("//*[@href='/']").click();
        sleep(1000);
        //Нажать на кнопку добавления товара  goodName в корзину
        String str = String.format("//*[@data-action='add-to-cart'][@data-name='%s']", goodName);
        $x(str).click();
        sleep(1000);
        //Нажать на кнопку корзина
        $x("//*[@id='open-cart-btn']").click();
        sleep(1000);
        //Найти в списке товаров в корзине последний добавленный и проверить, что это goodName
        $$x("//*[@class='cart-item']").last().shouldHave(Condition.text(goodName));
        sleep(1000);
        //Закрыть корзину
        $x("//*[@id='close-modal']").click();
        sleep(1000);
        //Обновить страницу
        refresh();
        sleep(1000);
        //открыть корзину
        $x("//*[@id='open-cart-btn']").click();
        sleep(1000);
        //проверить элементы корзины, что она не пустая
        $$x("//*[@class='cart-item']").should(CollectionCondition.sizeNotEqual(0));
    }

    //2.5. Добавить в корзину товаров более чем на 300 рублей и нажать на кнопку «Оформить заказ».
    // Проверить, что отображается JS Alert.
    @Test
    void selenideTest25() {
        //добавление товара через админку
        //нажать кнопку Админконсоль
        $x("//*[@class='btn-outline']").click();
        sleep(1000);
        //Ввести логин
        $x("//*[@name='username']").sendKeys(userName);
        sleep(1000);
        //Ввести пароль
        $x("//*[@id='password']").sendKeys(password);
        sleep(1000);
        //нажать sign in
        $x("//*[@type='submit']").click();
        sleep(1000);
        //ввести наименование товара
        String good = "richCoffee";
        String richprice = "400";
        $x("//*[@id='n-name']").sendKeys(good);
        sleep(1000);
        //ввести цену товара
        $x("//*[@id='n-price']").sendKeys(richprice);
        sleep(1000);
        //Нажать Создать
        $x("//*[@id='add-btn']").click();
        sleep(1000);
        //Нажать на "Вернуться на сайт
        $x("//*[@href='/']").click();
        sleep(1000);
        //Нажать на кнопку добавления товара  goodName в корзину
        String str = String.format("//*[@data-action='add-to-cart'][@data-name='%s']", good);
        $x(str).click();
        $("#open-cart-btn").click();
        $("#makeOrder").click();
        sleep(1000);
        Alert activeAlert = Selenide.switchTo().alert();
        sleep(2000);
        Assertions.assertThat(activeAlert.getText())
                .as("Текст уведомления не соответствует")
                .isEqualTo("[SmartShop]: Денег не хватает! Сумма 400 ₽ превышает лимит 300 ₽.");
        activeAlert.accept();
        sleep(1000);

    }


    @Test
    void selenideTest(){
        //SelenideElement cartButton = $("#open-cart-btn");
        SelenideElement cartButton = $x("//*[@id='open-cart-btn']");
        cartButton.should(visible, Duration.ofSeconds(10));
        cartButton.click();
        cartButton.should(not(visible), Duration.ofSeconds(1));
        //sleep(5000);
        ElementsCollection cardTitleList = $$x("//*[contains(@id, card)]/h4");
        cardTitleList.forEach(cardTitle -> {
            System.out.println(cardTitle.text());
        });
    }

    @Test
    void selenideTest1(){
        SelenideElement addToCartButton = $x("//*[@data-action='add-to-cart']");
        addToCartButton.click();
        addToCartButton.click();
        addToCartButton.click();
        addToCartButton.click();
        $("#open-cart-btn").click();
        $("#makeOrder").click();
        sleep(1000);
        Alert activeAlert = Selenide.switchTo().alert();
        sleep(2000);
        activeAlert.accept();
        sleep(1000);
    }
}
