package Page;


import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.impl.CollectionElement;
import org.openqa.selenium.support.FindBy;

import javax.swing.*;
import java.util.List;

//Домашнее задание №5
//Задача 1: написать локаторы (допускаются и CSS-, и XPath-локаторы) для следующих элементов:
//1.1. Список названий товаров.
//1.2. Список цен товаров.
//1.3. Название товара с ценой 25 (добавьте через админку).
//1.4. Цена товара с названием «Стакан» (добавьте через админку).
//1.5. Список карточек товаров в корзине.
//1.6. Кнопка корзины.

public class MainPage {

    //1.1. Список названий товаров
    // xpath
    @FindBy(xpath = "//div/h4")
    private ElementsCollection listGoodNames;

    //----------------------
    //css
    //    @FindBy(css = "h4")
    //    private ElementsCollection listGoodNames;
    //===========================
    public ElementsCollection getListGoodNames(){return listGoodNames; }

//1.2. Список цен товаров.
    //xpath
//    @FindBy(xpath ="//div[@id='products-list']/div/div[1]")
//    private ElementsCollection listGoodPrices;

    //css
    @FindBy(css = "h4+div")
    private ElementsCollection listGoodPrices;

    public ElementsCollection getListGoodPrices(){ return listGoodPrices;}

    //1.3. Название товара с ценой 25 (добавьте через админку).
    //xpath
//    @FindBy(xpath = "//div[@id='products-list']/div/div[1][text()='25 ₽']/../h4")
//    private SelenideElement nameGoodPrice25;

    //css
    @FindBy(css=".product-card[data-price='25']>h4")
    private SelenideElement nameGoodPrice25;

    public String getnameGoodPrice25(){ return nameGoodPrice25.getText(); }

    //1.4. Цена товара с названием «Стакан» (добавьте через админку).

    //xpath
    @FindBy(xpath="//div[@class='product-card' and @data-name = 'Стакан']/div[1]")
    private SelenideElement priceGoodStakan;

//    //css
//    @FindBy(css=".product-card[data-name='Стакан'] div[style^='color']")
//    private SelenideElement priceGoodStakan;

    public String priceGoodStakan(){
        return priceGoodStakan.getText();
    }

    //xpath
    @FindBy(xpath = "//div[@class='cart-item']")
    private ElementsCollection goodsInBasket;

    //css
//    @FindBy(css="#cart-items .cart-item")
//    private ElementsCollection goodsInBasket;


    //описать элемент заголовок главной страницы
    @FindBy(xpath ="//h1[@id = 'main-title']")
    private SelenideElement mainTitle;

    //кнопка добавить в корзину для продукта cofee (id=67)
    @FindBy (xpath = "//button[@data-id = \"67\" and @data-action = \"add-to-cart\" ]")
    private SelenideElement addToCart;

    //всплывающее сообщение
    @FindBy (xpath = "//div[@id= 'toast-container']")
    private SelenideElement popUp;

    //копка Корзина xpath
    //@FindBy(xpath="//button[@id='open-cart-btn']")
    //private SelenideElement btnBusket;

    //копка Корзина css
    @FindBy(css = "#open-cart-btn")
    private SelenideElement btnBusket;


    //часть надписи на кнопке Корзина с количеством добавленных товаров
    @FindBy(xpath = "//span[@id= 'cart-count']")
    private SelenideElement cartCount;

    //popUp корзины, появляющийся при нажатии на кнопку Корзина
    @FindBy(xpath = "//div[@class='modal-content']" )
    private SelenideElement basketWidget;

    //товары в виджте корзины
    @FindBy(xpath = "//div[@class='cart-item' and @id='cart-item-67']")
    private SelenideElement cartItem1;

    //общая сумма товаров в корзине
    @FindBy(xpath="//span[@id='total-price']")
    private SelenideElement totalPrice;

    public String getTitleText(){
        return mainTitle.getText();
    }

    public void clickToProduct(){
        addToCart.click();
    }

    public void clickToBasket(){ btnBusket.click(); }

    public String getCartCount(){
        return cartCount.getText();
    }

    public SelenideElement getPopUp() {
        return popUp;
    }

    public SelenideElement getBasketWidget() {
        return basketWidget;
    }

    public String getBasketWidgetText() {
        return basketWidget.getText();
    }

    public SelenideElement getCartItem1() {
        return cartItem1;
    }

    public String getCartItem1Text() {
        return cartItem1.getText();
    }

    public SelenideElement getTotalPrice() {
        return totalPrice;
    }

    public String getTotalPriceText() {
        return totalPrice.getText();
    }
}
