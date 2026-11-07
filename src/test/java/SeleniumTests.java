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
        this.driver.findElement(By.xpath("//*[@class='btn-outline']")).click();
        Thread.sleep(1000L);
        this.driver.findElement(By.xpath("//*[@name='username']")).sendKeys(new CharSequence[]{this.userName});
        Thread.sleep(1000L);
        this.driver.findElement(By.xpath("//*[@id='password']")).sendKeys(new CharSequence[]{this.password});
        Thread.sleep(1000L);
        this.driver.findElement(By.xpath("//*[@type='submit']")).click();
        Thread.sleep(1000L);
        this.driver.findElement(By.xpath("//*[@id='n-name']")).sendKeys(new CharSequence[]{this.goodName});
        Thread.sleep(1000L);
        this.driver.findElement(By.xpath("//*[@id='n-price']")).sendKeys(new CharSequence[]{this.price});
        Thread.sleep(1000L);
        this.driver.findElement(By.xpath("//*[@id='add-btn']")).click();
        Thread.sleep(1000L);
        this.driver.findElement(By.xpath("//*[@href='/']")).click();
        Thread.sleep(1000L);
        String str = String.format("//*[@data-name='%s'][@data-price='%s'][@class='product-card']", this.goodName, this.price);
        ((ObjectAssert)Assertions.assertThat(this.driver.findElement(By.xpath(str))).as(String.format("Карточка товара [%s] должна быть на странице", this.goodName), new Object[0])).isNotNull();
    }

    //1.2. Добавить товар в корзину и проверить, что он отображается.
    @Test
    void SeleniumTest12() throws InterruptedException {
        String str = String.format("//*[@data-action='add-to-cart'][@data-name='%s']", this.goodName);
        this.driver.findElement(By.xpath(str)).click();
        Thread.sleep(1000L);
        this.driver.findElement(By.xpath("//*[@id='open-cart-btn']")).click();
        Thread.sleep(1000L);
        String text = ((WebElement)this.driver.findElements(By.xpath("//*[@class='cart-item']")).getLast()).getText();
        Thread.sleep(1000L);
        ((AbstractStringAssert)Assertions.assertThat(text).as(String.format("В корзину должен быть добавлен товар [%s]", this.goodName), new Object[0])).contains(new CharSequence[]{this.goodName});
    }

    //1.3. Попытаться войти в админку с неверным логином и паролем.
    @Test
    void SeleniumTest13() throws InterruptedException {
        this.driver.findElement(By.xpath("//*[@class='btn-outline']")).click();
        Thread.sleep(1000L);
        this.driver.findElement(By.xpath("//*[@name='username']")).sendKeys(new CharSequence[]{"userName"});
        Thread.sleep(1000L);
        this.driver.findElement(By.xpath("//*[@id='password']")).sendKeys(new CharSequence[]{"password"});
        Thread.sleep(1000L);
        this.driver.findElement(By.xpath("//*[@type='submit']")).click();
        Thread.sleep(1000L);
        String text = this.driver.findElement(By.xpath("//*[@class='alert alert-danger']")).getText();
        ((AbstractStringAssert)Assertions.assertThat(text).as("Должно быть сообщение: 'Неверные учетные данные пользователя'", new Object[0])).isEqualTo("Неверные учетные данные пользователя");
    }

    //1.4. Проверить сохранение товаров в корзине после обновления страницы.
    @Test
    void SeleniumTest14() throws InterruptedException {
        this.driver.findElement(By.xpath("//*[@class='btn-outline']")).click();
        Thread.sleep(1000L);
        this.driver.findElement(By.xpath("//*[@name='username']")).sendKeys(new CharSequence[]{this.userName});
        Thread.sleep(1000L);
        this.driver.findElement(By.xpath("//*[@id='password']")).sendKeys(new CharSequence[]{this.password});
        Thread.sleep(1000L);
        this.driver.findElement(By.xpath("//*[@type='submit']")).click();
        Thread.sleep(1000L);
        this.driver.findElement(By.xpath("//*[@id='n-name']")).sendKeys(new CharSequence[]{this.goodName});
        Thread.sleep(1000L);
        this.driver.findElement(By.xpath("//*[@id='n-price']")).sendKeys(new CharSequence[]{this.price});
        Thread.sleep(1000L);
        this.driver.findElement(By.xpath("//*[@id='add-btn']")).click();
        Thread.sleep(1000L);
        this.driver.findElement(By.xpath("//*[@href='/']")).click();
        Thread.sleep(1000L);
        String str = String.format("//*[@data-action='add-to-cart'][@data-name='%s']", this.goodName);
        this.driver.findElement(By.xpath(str)).click();
        Thread.sleep(1000L);
        this.driver.findElement(By.xpath("//*[@id='open-cart-btn']")).click();
        Thread.sleep(1000L);
        String text = ((WebElement)this.driver.findElements(By.xpath("//*[@class='cart-item']")).getLast()).getText();
        ((AbstractStringAssert)Assertions.assertThat(text).as(String.format("В корзину должен быть добавлен товар [%s]", this.goodName), new Object[0])).contains(new CharSequence[]{this.goodName});
        this.driver.findElement(By.xpath("//*[@id='close-modal']")).click();
        Thread.sleep(1000L);
        this.driver.navigate().refresh();
        Thread.sleep(1000L);
        this.driver.findElement(By.xpath("//*[@id='open-cart-btn']")).click();
        Thread.sleep(1000L);
        new ArrayList();
        List<WebElement> element = this.driver.findElements(By.xpath("//*[@class='cart-item']"));
        ((AbstractIntegerAssert)Assertions.assertThat(element.size()).as(String.format("В корзине не должно быть 0 товаров после обновления страницы", this.goodName), new Object[0])).isNotEqualTo(0);
    }

    @AfterEach
    void tearDown() {
        this.driver.quit();
    }
}
