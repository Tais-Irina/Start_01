import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

import com.codeborne.selenide.Configuration;

public class BaseTests {
    @BeforeEach
    public void setup(){
        //Задаем браузер для Selenide
        Configuration.browser = "chrome";
        Configuration.browserSize = "1926x1060";
        //путь к драйверу
        Configuration.browserBinary = "D:\\Distr\\chrome-win64\\chrome.exe";
        open("http://localhost:8080");
    }

    @AfterEach
    public void upDown(){
        closeWebDriver();
    }
}
