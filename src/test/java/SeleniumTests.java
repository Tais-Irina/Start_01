//Домашняя работа №6
//Задача 1: написать 4 автотеста с использованием фреймворка Selenium по следующим кейсам:
//1.1. Добавить товар через админку, выйти на витрину и проверить, что товар отображается.
//1.2. Добавить товар в корзину и проверить, что он отображается.
//1.3. Попытаться войти в админку с неверным логином и паролем.
//1.4. Проверить сохранение товаров в корзине после обновления страницы.
//1.5. Инициализацию браузера и его закрытие необходимо вынести в отдельные методы для выполнения перед и после тестов.

import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.AbstractIntegerAssert;
import org.assertj.core.api.AbstractStringAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ObjectAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumTests {
    WebDriver driver;
    String userName = "admin";
    String password = "secret123";
    String goodName = "cheese";
    String price = "12.5";

    public SeleniumTests() {
    }

    @BeforeEach
    void setup() {
        this.driver = new ChromeDriver();
        this.driver.get("http://localhost:8080");
    }

    //1.1. Добавить товар через админку, выйти на витрину и проверить, что товар отображается.
    @Test
    void SeleniumTest11() throws InterruptedException {
        //нажать кнопку Админконсоль
        this.driver.findElement(By.xpath("//*[@class='btn-outline']")).click();
        Thread.sleep(1000L);
        //Ввести логин
        this.driver.findElement(By.xpath("//*[@name='username']")).sendKeys(userName);
        Thread.sleep(1000L);
        //Ввести пароль
        this.driver.findElement(By.xpath("//*[@id='password']")).sendKeys(password);
        Thread.sleep(1000L);
        //нажать sign in
        this.driver.findElement(By.xpath("//*[@type='submit']")).click();
        Thread.sleep(1000L);
        //ввести наименование товара
        this.driver.findElement(By.xpath("//*[@id='n-name']")).sendKeys(goodName);
        Thread.sleep(1000L);
        //ввести цену товара
        this.driver.findElement(By.xpath("//*[@id='n-price']")).sendKeys(price);
        Thread.sleep(1000L);
        //Нажать Создать
        this.driver.findElement(By.xpath("//*[@id='add-btn']")).click();
        Thread.sleep(1000L);
        //Нажать на "Вернуться на сайт
        this.driver.findElement(By.xpath("//*[@href='/']")).click();
        Thread.sleep(1000L);
        // Проверка наличия карточки товара на странице
        String str = String.format("//*[@data-name='%s'][@data-price='%s'][@class='product-card']", goodName, price);
        Assertions
                .assertThat(driver.findElement(By.xpath(str)))
                .as(String.format("Карточка товара [%s] должна быть на странице", goodName))
                .isNotNull();
    }

    //1.2. Добавить товар в корзину и проверить, что он отображается.
    @Test
    void SeleniumTest12() throws InterruptedException {
        //Нажать на кнопку добавления товара  goodName в корзину
        String str = String.format("//*[@data-action='add-to-cart'][@data-name='%s']", goodName);
        driver.findElement(By.xpath(str)).click();
        Thread.sleep(1000L);
        //Нажать на кнопку корзина
        driver.findElement(By.xpath("//*[@id='open-cart-btn']")).click();
        Thread.sleep(1000L);
        //Найти в списке товаров в корзине последний добавленный и проверить, что это goodName
        String text = driver.findElements(By.xpath("//*[@class='cart-item']")).getLast().getText();
        Thread.sleep(1000L);
        Assertions
                .assertThat(text)
                .as(String.format("В корзину должен быть добавлен товар [%s]", goodName))
                .contains(goodName);
    }

    //1.3. Попытаться войти в админку с неверным логином и паролем.
    @Test
    void SeleniumTest13() throws InterruptedException {
        //Нажать кнопку Администрирование
        driver.findElement(By.xpath("//*[@class='btn-outline']")).click();
        Thread.sleep(1000L);
        //заполнить логин не верным логином
        driver.findElement(By.xpath("//*[@name='username']")).sendKeys("userName");
        Thread.sleep(1000L);
        //запольнить парль не верным паролем
        driver.findElement(By.xpath("//*[@id='password']")).sendKeys("password");
        Thread.sleep(1000L);
        //Нажать кнопку залогиниться
        driver.findElement(By.xpath("//*[@type='submit']")).click();
        Thread.sleep(1000L);
        //Найти сообщение о неверных учетных данных
        String text = driver.findElement(By.xpath("//*[@class='alert alert-danger']")).getText();
        Assertions.assertThat(text)
                .as("Должно быть сообщение: 'Неверные учетные данные пользователя'")
                .isEqualTo("Неверные учетные данные пользователя");
    }

    //1.4. Проверить сохранение товаров в корзине после обновления страницы.
    @Test
    void SeleniumTest14() throws InterruptedException {
        //добавление товара через админку
        driver.findElement(By.xpath("//*[@class='btn-outline']")).click();
        Thread.sleep(1000L);
        driver.findElement(By.xpath("//*[@name='username']")).sendKeys(userName);
        Thread.sleep(1000L);
        driver.findElement(By.xpath("//*[@id='password']")).sendKeys(password);
        Thread.sleep(1000L);
        driver.findElement(By.xpath("//*[@type='submit']")).click();
        Thread.sleep(1000L);
        driver.findElement(By.xpath("//*[@id='n-name']")).sendKeys(goodName);
        Thread.sleep(1000L);
        driver.findElement(By.xpath("//*[@id='n-price']")).sendKeys(price);
        Thread.sleep(1000L);
        driver.findElement(By.xpath("//*[@id='add-btn']")).click();
        Thread.sleep(1000L);
        driver.findElement(By.xpath("//*[@href='/']")).click();
        Thread.sleep(1000L);
        //добавить в корзину товар goodName
        String str = String.format("//*[@data-action='add-to-cart'][@data-name='%s']", goodName);
        driver.findElement(By.xpath(str)).click();
        Thread.sleep(1000L);
        //открыть корзину
        driver.findElement(By.xpath("//*[@id='open-cart-btn']")).click();
        Thread.sleep(1000L);
        //проверить, что товар есть в корзине
        String text = driver.findElements(By.xpath("//*[@class='cart-item']")).getLast().getText();
        Assertions
                .assertThat(text)
                .as(String.format("В корзину должен быть добавлен товар [%s]", goodName))
                .contains(goodName);
        //Закрыть корзину
        driver.findElement(By.xpath("//*[@id='close-modal']")).click();
        Thread.sleep(1000L);
        //Обновить страницу
        driver.navigate().refresh();
        Thread.sleep(1000L);
        //открыть корзину
        driver.findElement(By.xpath("//*[@id='open-cart-btn']")).click();
        Thread.sleep(1000L);
        new ArrayList();
        //Проверить, что корзина не пустая
        List<WebElement> element = driver.findElements(By.xpath("//*[@class='cart-item']"));
        Assertions
                .assertThat(element.size())
                .as(String.format("В корзине не должно быть 0 товаров после обновления страницы", goodName))
                        .isNotEqualTo(0);
    }
    @AfterEach
    void tearDown() {
        this.driver.quit();
    }
}
