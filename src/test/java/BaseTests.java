import com.codeborne.selenide.Selenide;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.*;

import com.codeborne.selenide.Configuration;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Slf4j
public class BaseTests {

    Properties prop = new Properties();
    String baseUrl;
    //String browserBinary;
    String browser;
    String browserSize;

    @BeforeEach
    public void setup(){
        try {
            prop.load(new FileInputStream("src/test/resources/config.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("Запуск автотестов");
        baseUrl = prop.getProperty("base.url");
        Configuration.timeout = Long.parseLong(prop.getProperty("timeout.global"));
        Selenide.open(baseUrl);
    }

    @AfterEach
    public void upDown(){
        Selenide.closeWebDriver();
        log.info("Браузер закрыт!");
    }
}
