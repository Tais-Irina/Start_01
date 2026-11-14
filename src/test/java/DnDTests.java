import com.codeborne.selenide.*;
import com.codeborne.selenide.impl.CollectionElement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.interactions.Actions;

import javax.swing.*;
import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Condition.*;

//Домашняя работа №7
// Задача 1: написать 2 автотеста для кейсов:
//1.1 Перетащить элемент в корзину с помощью Drag-and-Drop.
//1.2 Удалить добавленный элемент из корзины и проверить, что он там больше не отображается.

public class DnDTests {
    SelenideElement firstCard = $x("//*[@class='product-card'][1]");
    SelenideElement cartBtn = $x("//*[@id='open-cart-btn']");

    @BeforeEach
    void setup() {
        open("http://localhost:8080");
    }

    //1.1 Перетащить элемент в корзину с помощью Drag-and-Drop.
    //1.2 Удалить добавленный элемент из корзины и проверить, что он там больше не отображается.
    //Простой драг-н-дроп
    @Test
    void simpleDND(){
        sleep(1000);
        //перетащить первый элемент в корзину
        firstCard.dragAndDrop(DragAndDropOptions.to(cartBtn));
        sleep(2000);
        //Определить наиментование первого элемента
        String name = $x("//*[@class='product-card'][1]").getAttribute("data-name");
        //Определить id первого элемента
        String idCard = $x("//*[@class='product-card'][1]").getAttribute("data-id");
        //Нажать на корзину
        $x("//*[@id='open-cart-btn']").click();
        sleep(1000);
        //Нажать на удалить товар
        $x(String.format("//*[@id='cart-item-%s']/button",idCard)).click();
        sleep(2000);
        //Проверить,  что отображается Пусто
        $x("//*[@id='empty-cart']").shouldHave(Condition.text("Пусто"));
    }

    //драг-н-дроп через action
    @Test
    void anotherDND(){
        actions().moveToElement(firstCard)
                .clickAndHold()
                .pause(1000)
                .moveToElement(cartBtn)
                .release()
                .pause(Duration.ofSeconds(2))
                .perform();
    }

    //плавный drag-and-drop
    @Test
    void slowDND(){
        int firstCardX = firstCard.getCoordinates().inViewPort().getX()+firstCard.getSize().getWidth()/2;
        int firstCardY = firstCard.getCoordinates().inViewPort().getY()+firstCard.getSize().getHeight()/2;
        int cartButtonX = cartBtn.getCoordinates().inViewPort().getX()+cartBtn.getSize().getWidth()/2;
        int cartButtonY = cartBtn.getCoordinates().inViewPort().getY()+cartBtn.getSize().getHeight()/2;

        int diffX = cartButtonX - firstCardX;
        int diffY = cartButtonY - firstCardY;

        sleep(1000);
        Actions dndActions =  actions().moveToElement(firstCard).clickAndHold();
        for (int i = 0; i < 50; i++) {
            dndActions =  dndActions.moveByOffset(diffX/50, diffY/50);
        }
        dndActions.release().perform();
        sleep(2000);

    }

    @AfterEach
    void tearDown() {
        Selenide.closeWebDriver();
    }
}
