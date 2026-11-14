import com.codeborne.selenide.*;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
//Домашняя работа №7
//Задача 1: добавить в проект конфигурационный файл,
// а также обработать его в программном коде,
// обеспечив считывание из него и использование в ходе запуска автотестов следующих параметров:
//1. URL стенда и API;
//2. тайм-аут для поиска элементов;
//3. режим логирования (можно без реализации);
//4. Credentials для входа в админку;
//5. имя и цена для стартового товара.

//Все параметры, которые считываются из конфига, должны выводиться в консоль перед запуском теста, кроме логина и пароля.


//Домашняя работа №6  часть 3
//Задача 3: написать автотесты для следующих кейсов:
//3.1. Добавить три единицы товара в корзину и оплатить их (общая стоимость не должна превышать 300 рублей).
// Проверить уведомление об обработке заказа.
//3.2. Добавить в корзину несколько разных товаров и проверить, что общая цена в корзине считается корректно.
//3.3. Войти в админку и добавить товар. Проверить уведомление после добавления товара.
//3.4. Войти в админку и отредактировать товар. Выйти на список товаров и проверить, что изменения применились.
//3.5. Хотя бы в одном кейсе должны быть обращение к API для генерации тестовых данных.
//3.6. Хотя бы в одном кейсе в блоке @AfterEach тестовые данные должны удаляться.
@Slf4j
public class AutoTestsWithApi {

    Properties prop = new Properties();

    String userName;
    String userPassword;
    String goodsAdd;
    String goodList;
    String baseUrl;
    String logLevel;
    String goodName;//используется не для стартового товара, а для редактирования товара
    String goodPrice;//используется не для стартового товара, а для редактирования товара
    //создадим билдер запросов к api Goods
    RestAPIGoodBuilder api = new RestAPIGoodBuilder();
    //тело запроса для тестового товара
    testRestAssured.RequestBody reqBody =  testRestAssured.randomBody();


    //перед каждым тестом создатим тестовый товар с ценой до 100р
    //3.5. Хотя бы в одном кейсе должны быть обращение к API для генерации тестовых данных.
    @BeforeEach
    void setup(){
        try {
            prop.load(new FileInputStream("src/test/resources/config.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("Запуск автотестов");
        userName = prop.getProperty("user.name");
        log.info("userName: нельзя выводить");
        userPassword = prop.getProperty("user.password");
        log.info("userPassword: нельзя выводить");
        baseUrl = prop.getProperty("base.url");
        log.info("Адрес тестового стенда: " + baseUrl);
        goodsAdd = prop.getProperty("good.add");
        log.info("Энд-поинт для добавления товаров: " + goodsAdd);
        goodList = prop.getProperty("good.list");
        log.info("Энд-поинт для получения списка товаров: " + goodList);
        logLevel = prop.getProperty("log.level");
        log.info("Уровень логирования: " + logLevel);

        goodName = prop.getProperty("good.name");
        log.info("Наименование тостового товара, на который будут редактироваться товары: " + goodName );
        goodPrice = prop.getProperty("good.price");
        log.info("Цена тостового товара: " + goodPrice );
        //задаем таймаут ожидания загрузки элементов
        Configuration.timeout = Long.parseLong(prop.getProperty("timeout.global"));
        Selenide.open(baseUrl);
        sleep(1000);
        //Добавить тестовый рандомный товар с помощью api
        Response response = api.post(goodsAdd, reqBody);
        //Обновить страницу
        refresh();
        //теперь товар видно на странице
        sleep(1000);
    }

    //3.1. Добавить три единицы товара в корзину и оплатить их (общая стоимость не должна превышать 300 рублей).
    // Проверить уведомление об обработке заказа.
    @Test
    void AddGoodInBasketTest31(){
        //добавить 3 шт в корзину
        //Нажать на кнопку добавления товара reqBody.name() в корзину
        String str = String.format("//*[@data-action='add-to-cart'][@data-name='%s']", reqBody.name());
        $x(str).click();
        sleep(1000);
        $x(str).click();
        sleep(1000);
        $x(str).click();
        sleep(1000);
        //Нажать на кнопку корзина
        $x("//*[@id='open-cart-btn']").click();
        sleep(3000);
        //оплатить
        $x("//*[@id='makeOrder']").click();
        // Проверить уведомление об обработке заказа.
        // Т.к. в этот момент может быть много уведомлений, проверяем, что хотябы одно
        //содержит желаемый текст
        //вариант проверки через assertJ
//        Assertions.assertThat($$x("//*[@class='toast']").texts())
//                .as("Текст уведомления не совпадает с 'Заказ принят в обработку'")
//                .contains("Заказ принят в обработку!");

        //вариант проверки через Selenide
        $$x("//*[@class='toast']")
                .shouldHave(CollectionCondition.textsInAnyOrder("Заказ принят в обработку!"));
    }

    //3.2. Добавить в корзину несколько разных товаров и проверить, что общая цена в корзине считается корректно.
    @Test
    void AddGoodInBasketTest32(){
        //количество добавляемых рандомных товаров через api
        int N = 2;
        //Добавить на страницу N тестовых рандомных товара через api (первый добавляется в блоке  BeforeEach)
        for(int i=0; i<N; i++) {
            api.post(goodsAdd, testRestAssured.randomBody());
        }
        //Обновить страницу
        refresh();
        //теперь товар видно на странице
        sleep(3000);
        //добавить N+1 товара в корзину, находим последние добавленные товары на странице
        ElementsCollection addElements = $$x("//*[@data-action='add-to-cart']").last(N+1);
        Double ExpectedSumm = 0.0;
        for (SelenideElement element: addElements){
            String dataPrice = element.getAttribute("data-price");
            ExpectedSumm = ExpectedSumm + Double.parseDouble(dataPrice) ;
            element.click();
            sleep(1000);
        }
        sleep(3000);
        //нажать копку корзина,  проверить, что общая цена в корзине считается корректно.
        $x("//*[@id='open-cart-btn']").click();
        sleep(1000);
        //Найти общую стоимость
        String ActualSum = $x("//*[@id='total-price']").getText();
        //Вывожу по старинке себе сообщения :))))
        System.out.println("==========================");
        System.out.println("Фактическая общая сумма "+ActualSum + " Ожидамая сумма: " + ActualSum.toString());
        System.out.println("==========================");
        //Проверка assertJ
//        Assertions.assertThat(Double.parseDouble(ActualSum))
//                .as(String.format("Фактическая сумма товаров [%s] не равна ожидаемой сумме товаров [%s]", ActualSum, ExpectedSumm.toString()))
//                .isEqualTo(ExpectedSumm);

        //Проверка Selenide - выглядит лаконичнее
        $x("//*[@id='total-price']").shouldHave(Condition.text(ActualSum.toString()));


        //Удалить тестовые товары через api
        //определить id добавленных товаров, чтобы их удалить
        ElementsCollection elements = $$x("//*[@class='product-card']").last(N);
        for(SelenideElement element:elements){
            String id = element.getAttribute("data-id").toString();
            System.out.println("id добавленного товара: " + id);
            api.delete(id);
        }
        sleep(1000);
        //Обновить страницу
        refresh();
        sleep(3000);
    }

    //3.3. Войти в админку и добавить товар. Проверить уведомление после добавления товара.
    @Test
    void AddGoodInBasketTest33(){
        $x("//*[@class='btn-outline']").click();
        sleep(1000);
        //Ввести логин
        $x("//*[@name='username']").sendKeys(userName);
        sleep(1000);
        //Ввести пароль
        $x("//*[@id='password']").sendKeys(userPassword);
        sleep(1000);
        //нажать sign in
        $x("//*[@type='submit']").click();
        sleep(1000);
        //сгенерить данные для рандомного товара
        testRestAssured.RequestBody reqBody =  testRestAssured.randomBody();
        //ввести наименование товара
        $x("//*[@id='n-name']").sendKeys(reqBody.name());
        sleep(1000);
        //ввести цену товара
        $x("//*[@id='n-price']").sendKeys(reqBody.price().toString());
        sleep(1000);
        //Нажать Создать
        $x("//*[@id='add-btn']").click();
        //Найти уведомление и проверить его!!!!!!!!!!!!
        $x("//*[@class='toast']").shouldHave(text("Товар успешно добавлен!"));
        sleep(1000);
        //удалить добавленный товар
        $$x("//*[@data-action='delete']").last().click();
        sleep(1000);
        //найти уведомление и нажать ОК
        Alert activeAlert = Selenide.switchTo().alert();
        sleep(2000);
        activeAlert.accept();
        sleep(1000);
        //Нажать на "Вернуться на сайт
        $x("//*[@href='/']").click();
        sleep(1000);
    }

    //3.4. Войти в админку и отредактировать товар. Выйти на список товаров и проверить, что изменения применились.
    @Test
    void AddGoodInBasketTest34(){
        $x("//*[@class='btn-outline']").click();
        sleep(1000);
        //Ввести логин
        $x("//*[@name='username']").sendKeys(userName);
        sleep(1000);
        //Ввести пароль
        $x("//*[@id='password']").sendKeys(userPassword);
        sleep(1000);
        //нажать sign in
        $x("//*[@type='submit']").click();
        sleep(1000);
        //найти товар reqBody.name() отредактировать его наименование


        $x(String.format("//*[@value='%s']",reqBody.name())).clear();
        $x(String.format("//*[@value='%s']",reqBody.name())).sendKeys(goodName);
        sleep(1000);
        //отредактировать цену
        $x(String.format("//*[@value='%s']/../../td[3]/input",reqBody.name())).click();
        $x(String.format("//*[@value='%s']/../../td[3]/input",reqBody.name())).clear();
        $x(String.format("//*[@value='%s']/../../td[3]/input",reqBody.name())).sendKeys(goodPrice);
        sleep(1000);
        //найти id изменяемого товара
        String id = $x(String.format("//*[@value='%s']/../../td[1]",reqBody.name())).text();
        //Нажать сохранить
        $x(String.format("//*[@data-id='%s'][@data-action='update']",id)).click();
        reqBody = new testRestAssured.RequestBody(goodName, Double.parseDouble(goodPrice));
        //Нажать на "Вернуться на сайт
        $x("//*[@href='/']").click();
        sleep(1000);
        //проверяем, что карточка товара с Name есть на странице
        $x(String.format("//*[@data-name='%s'][@class='product-card']",goodName)).should(exist);
        //проверяем цену
        $x(String.format("//*[@data-name='%s'][@class='product-card']",goodName)).shouldHave(Condition.attribute("data-price",goodPrice));
    }


    //3.6. Хотя бы в одном кейсе в блоке @AfterEach тестовые данные должны удаляться.
    //после каждого теста удалим тестовый товар
    @AfterEach
    void tearDown() {
        // Удалить товар
        String str = String.format("//*[@data-name='%s'][@class='product-card']",reqBody.name());
        String idDeleted = $x(str).getAttribute("data-id").toString();
        api.delete(idDeleted);
        sleep(1000);
        //Обновить страницу
        refresh();
        sleep(1000);
        Selenide.closeWebDriver();
        log.info("Браузер закрыт!");
    }
}
