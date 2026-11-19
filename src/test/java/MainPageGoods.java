//Домашнее задание №9
//Задача 1: написать 2 класса, реализующих PageObject-архитектуру:
//Класс1
//PageObject для главной страницы с товарами.
// Можно добавить на неё любые объекты и коллекции, главное, чтобы их было не меньше 5.
// Дополнительно необходимо написать методы взаимодействия с каждым из них в зависимости от типа
// (click(), sendKeys());
//Класс 2
// PageObject для формы авторизации в админке. Здесь должна быть полная функциональность
// (3 элемента: логин, пароль, кнопка «Войти»).

//Для всех описанных элементов должны быть базовые проверки (элемент видно, текстовое поле содержит текст),
// организованные в PageAssert-классе или внутри самого Page.

import Page.MainPage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.support.FindBy;

import java.util.Objects;

import static com.codeborne.selenide.Selenide.*;

@Getter
public class MainPageGoods {
    //1.Кнопка корзина
    SelenideElement cartButton = $x("//*[@id='open-cart-btn']");

    //Нажатие на кнопку корзина
    public MainPageGoods clickCartButton() {
        cartButton.click();
        return this;
    }

    //2.Кнопка Администрирование
    SelenideElement adminButton = $x("//*[@class='btn-outline']");

    //Нажатие на кнопку Администрирования
    public MainPageGoods clickAdminButton() {
        adminButton.click();
        return this;
    }

    //3.Список с карточками товаров
    ElementsCollection productList = $$x("//*[@class='product-card']");

    //Получить количество карточек с товарами
    public Integer productCardNumber() {
        return productList.size();
    }

    //Получить наименование первого товара
    public String getNameFirstGood(){
        SelenideElement element = productList.first();
        return element.getAttribute("data-name");
    }

    //Получить цену первого товара
    public String getPriceFirstGood(){
        SelenideElement element = productList.first();
        return element.getAttribute("data-price");
    }

    //Получить цену товара по index
    public Double getGoodPrice(int index){
       return Double.parseDouble(Objects.requireNonNull(productList.get(index).getAttribute("data-price")));
    }

    //4. Кнопки добавить товар в корзину
    ElementsCollection addtoCartButtonList = $$x("//*[@data-action='add-to-cart']");

    //Нажать на добавить товар в корзину. Товар выбирается по индексу
    public MainPageGoods addProductToCart(int index) {
        addtoCartButtonList.get(index).click();
        return this;
    }

    //5. Поля ввода для колличества товара
    ElementsCollection qtyInputList = $$x("//*[@class='qty-input']");

    //Ввести количество товара value в поле ввода под номером index
    public MainPageGoods setQtyInput(int index, String value) {
        //встать в поле вводя в карточке товара с индексом index
        SelenideElement element = qtyInputList.get(index);
        element.click();
        element.clear();
        element.sendKeys(value);
        return this;
    }

    //6. Кнопки увеличения количества товара в карточке
    ElementsCollection qtyAddButton = $$x("//*[@data-step='1']");

    //Нажать на кнопку увеличения количества товаров в карточки index
    public MainPageGoods qtyAddButtonClick(int index) {
        qtyAddButton.get(index).click();

        return this;
    }

    //корзина
    @Getter
    public class CartPage{
        //Кнопка сделать заказ
        SelenideElement makeOrderButton = $x("//*[@id='makeOrder']");
        //Общая сумма товаров в корзине - поле
        SelenideElement totalSumField = $x("//*[@id='total-price']");
        //Общая сумма товаров - значение

        //Нажать на кнопку сделать заказ
        public  MainPageGoods makeOrderButtonClick(){
            makeOrderButton.click();
            return MainPageGoods.this;
        }

        public MainPageGoods totalSumIsEqual(String expectedTotalSum){
            totalSumField.shouldHave(Condition.text(expectedTotalSum));
            return MainPageGoods.this;
        }

    }

    //объект класса корзина
    @Getter
    CartPage cartPage = new CartPage();


    //уведомления
    ElementsCollection toasts = $$x("//*[@class='toast']");

    public MainPageGoodsAssert check(){
        return new MainPageGoodsAssert(this);
    }
}


