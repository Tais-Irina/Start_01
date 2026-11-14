import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class HiddenTest {
    @BeforeEach
    void setup(){
        open();
        sleep(30000);
        open("http://localhost:8080");
    }

    @Test
    void hiddenTest(){
        SelenideElement addToCartButton = $x("//*[@data-action='add-to-cart']");
        addToCartButton.click();
        addToCartButton.click();
        addToCartButton.click();

        SelenideElement notificationContainer = $x("(//*[@datatest='notification-container'])[1]");
        notificationContainer.shouldNot(visible);
        notificationContainer.$x("./..").should(visible);//родитель видимый
        notificationContainer.should(exist);
    }
}
