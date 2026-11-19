//Домашнее задание №9
//Задача 2: переписать (или написать заново) следующие кейсы в проекте с использованием архитектуры PageObject:
//2.1. Добавить три единицы товара в корзину и оплатить их
// (общая стоимость не должна превышать 300 рублей). Проверить уведомление об обработке заказа.
//
//2.2. Добавить в корзину несколько разных товаров и проверить,
// что общая цена в корзине считается корректно.
//
//2.3. Войти в админку и добавить товар. Проверить уведомление после добавления товара.
//2.4. Войти в админку и отредактировать товар. Выйти на список товаров и проверить, что изменения применились.
//
//Все проверки в кейсах должны быть организованы в классах с ассертами (PageAssert).

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Selenide;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static com.codeborne.selenide.Selenide.*;


@Slf4j
public class PagesObjectGoodsTests {

    String configPath = "src/test/resources/config.properties";
    Properties prop = new Properties();

    String userName;
    String userPassword;
    String baseUrl;
    String goodName;
    String goodPrice;
    String goodsAdd;
    //создадим билдер запросов к api Goods
    RestAPIGoodBuilder api = new RestAPIGoodBuilder();
    public record  RequestBody(String name, Double price){}

    private int idGood;

    @BeforeEach
    void setup(){
        //считаем данные из конфигурационного файла
        try {
            prop.load(new FileInputStream(configPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("Запуск автотестов");
        userName = prop.getProperty("user.name");
        userPassword = prop.getProperty("user.password");
        baseUrl = prop.getProperty("base.url");
        goodsAdd = prop.getProperty("good.add");
        log.info("Адрес тестового стенда: " + baseUrl);

        goodName = prop.getProperty("good.name");
        log.info("Наименование тостового товара: " + goodName );
        goodPrice = prop.getProperty("good.price");
        log.info("Цена тостового товара: " + goodPrice );
        Selenide.open(baseUrl);
        sleep(1000);
        //Добавить тестовый товар с помощью api
        RequestBody reqBody = new RequestBody(goodName, Double.parseDouble(goodPrice));
        Response response = api.post(goodsAdd, reqBody);
        idGood = response.jsonPath().getInt("data.id");
        //Обновить страницу
        refresh();
        //теперь товар видно на странице
        sleep(1000);
    }

    //Проверки ManePage к Задаче 1 из Д.з.№9
    //Хорошо бы написать отдельные тесты для каждой проверки, но
    //сил уже нет, поэтому все в одном тесте, но + логи
    @Test
    void MainPageTests(){
        MainPageGoods mainPageGoods = new MainPageGoods();
        log.info("Запуск проверок Элементов на гланой странице");
        log.info("Запуск проверки видимости кнопки Корзина");
        mainPageGoods.check().cardButtonIsVisible();
        log.info("Запуск проверки видимости кнопки Администрирования");
        mainPageGoods.check().adminButtonIsVisible();
        log.info("Запуск проверки видимости всех карточек товаров на странице");
        mainPageGoods.check().productListItemsIsVisible();
        log.info("Запуск проверки текста кнопок добалвения товаров в корзину на соответствие тексту 'В корзину'");
        mainPageGoods.check().addToCartButtonsContainsText("В корзину");
        log.info("Запуск проверки поля ввода количества товара, базово заполенно 1");
        mainPageGoods.check().qtyInputListContainsText("1");
        log.info("Кнопки '+' видны в каждой карточке товара");
        mainPageGoods.check().qtyAddButtonIsVisible();
        sleep(1000);
    }

    //Проверки AuthForm к Задаче 1 из Д.з.№9
    @Test
    void AuthFormTests(){
        //основная страцина
        MainPageGoods mainPageGoods = new MainPageGoods();
        //нажать кнопку Администрирование
        mainPageGoods.clickAdminButton();
        AuthorizFormPage authPage = new AuthorizFormPage();
        sleep(3000);
        //Сделаем все проверки в одном тесте( что не желательно и добавим логи)
        //проверить видимость кнопки
        log.info("Запуск проверок Элементов на форме авторизации");
        log.info("Запуск проверки плейсхолдера поля Логин");
        authPage.check().userNameInputContainsText("Username");
        log.info("Запуск проверки плейсхолдера поля Пароль");
        authPage.check().passwordInputContainsText("Password");
        log.info("Запуск проверки видимости кнопки Sign In");
        authPage.check().signInButtonIsVisible();
        log.info("Проверки Элементов на форме авторизации закончены");

        //вернуться на главную страницу
        Selenide.back();
        sleep(3000);

    }

//Проверки к задаче №2 Д.З.№9
//2.1. Добавить три единицы товара в корзину и оплатить их
// (общая стоимость не должна превышать 300 рублей). Проверить уведомление об обработке заказа.
    @Test
    void TestsTask321(){
        //добавить 3 шт в корзину
        //Нажать на кнопку добавления
        //Нажать на кнопку корзина
        //В корзине нажать сделать заказ
        //хотя бы один элемент колекции уведомлений содержит текст
        MainPageGoods mainPageGoods = new MainPageGoods();
        int index = 1;
        String qty = "3";
        mainPageGoods.setQtyInput(index, qty)
                .addProductToCart(index)
                .clickCartButton().getCartPage()
                .makeOrderButtonClick()
                .check().toastTextIsContain("Заказ принят в обработку!");
        sleep(3000);

    }

    //2.2. Добавить в корзину несколько разных товаров и проверить,
    // что общая цена в корзине считается корректно.
    @Test
    void TestsTask322(){
        MainPageGoods mainPageGoods = new MainPageGoods();
        Double expectedSum = 0.0;
        expectedSum = expectedSum+mainPageGoods.getGoodPrice(1)
                +mainPageGoods.getGoodPrice(2)
                +mainPageGoods.getGoodPrice(3);
        mainPageGoods.addProductToCart(1)
                .addProductToCart(2)
                .addProductToCart(3)
                .clickCartButton().getCartPage().totalSumIsEqual(expectedSum.toString());
        Selenide.back();
        sleep(1000);
    }

    //2.3. Войти в админку и добавить товар. Проверить уведомление после добавления товара.
    @Test
    void TestsTask323(){

        MainPageGoods mainPageGoods = new MainPageGoods();
        //Нажать на кнопку администрирования
        mainPageGoods.clickAdminButton();

        //Создадим объек для авторизации в админку
        AuthorizFormPage authPage = new AuthorizFormPage();
        authPage
                .setUserNameInput(userName)
                .setPasswordInput(userPassword)
                .clickSignInButton();

        //Форма администрирования
        AdminPanal adminPanal = new AdminPanal();
        adminPanal.newNameInputFill("Bag")
                .newPriceInputFill("10.0")
                .addButtonClick();

        mainPageGoods.check().toastTextIsContain("Товар успешно добавлен!");
        sleep(3000);
        //Удалить добавленный товар, чтобы не оставлять тестовые данные на стенде
        adminPanal.deleteLastButtonClick();

        //найти Окно Alert и нажать ОК
        Alert activeAlert = Selenide.switchTo().alert();
        sleep(2000);
        activeAlert.accept();
        adminPanal.returnMainPageClick();
    }

    //2.4. Войти в админку и отредактировать товар. Выйти на список товаров и проверить, что изменения применились.
    @Test
    void TestsTask324(){
        MainPageGoods mainPageGoods = new MainPageGoods();
        mainPageGoods.clickAdminButton();

        //Создадим объек для авторизации в админку
        AuthorizFormPage authPage = new AuthorizFormPage();
        authPage
                .setUserNameInput(userName)
                .setPasswordInput(userPassword)
                .clickSignInButton();

        //Форма администрирования
        AdminPanal adminPanal = new AdminPanal();
        String name = "Bag";
        String price = "10.52";
        //найти первый товар

        adminPanal.nameFirstInputCorrect(name)
                .priceFirstInputCorrect(price)
                .updateFirstButtonClick();

        adminPanal.returnMainPageClick();
        mainPageGoods.check()
                .nameFirstGoodsEqual(name)
                        .priceFirstGoodsEqual(price);

        sleep(1000);

    }

    //после каждого теста удалим тестовый товар
    @AfterEach
    void tearDown() {
        // Удалить товар
       api.delete(idGood);
        sleep(1000);
        //Обновить страницу
        refresh();
        sleep(1000);
        Selenide.closeWebDriver();
        log.info("Браузер закрыт!");
    }
}
