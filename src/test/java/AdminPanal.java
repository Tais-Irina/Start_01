import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class AdminPanal {
    //Поле для ввода наименования нового товара
    SelenideElement newNameInput = $x("//*[@id='n-name']");
    //Заполнить новое имя товара
    public AdminPanal newNameInputFill(String newName){
        newPriceInput.click();
        newNameInput.clear();
        newNameInput.sendKeys(newName);
        return this;
    }

    //Поле для ввода цены нового товара
    SelenideElement newPriceInput = $x("//*[@id='n-price']");
    //Заполнить нову цену
    public AdminPanal newPriceInputFill(String newPrice){
        newPriceInput.click();
        newPriceInput.clear();
        newPriceInput.sendKeys(newPrice);
        return this;
    }

    //Кнопка создать новый товар
    SelenideElement addButton = $x("//*[@id='add-btn']");
    //Нажать создать товар
    public AdminPanal addButtonClick(){
        addButton.click();
        return this;
    }

    //Кнопки удалить товар
    ElementsCollection deleteButtons = $$x("//*[@data-action='delete']");

    //Кнопки сохранить изменения товара
    ElementsCollection updateButtons = $$x("//*[@data-action='update']");

    //Нажать на кнопку удалить для последнего товара
    public AdminPanal deleteLastButtonClick(){
        SelenideElement elemet = deleteButtons.last();
        elemet.click();
        return this;
    }

    //Нажать на кнопку обновить первый товар
    public AdminPanal updateFirstButtonClick(){
        SelenideElement element = updateButtons.first();
        element.click();
        return this;
    }

    //Поля ввода наименований созданных товаров
    ElementsCollection nameInputs = $$x("//*[@id='tbody']/tr/td/input[@type='text']");

    //Поля ввода цен созданных товаров
    ElementsCollection priceInputs = $$x("//*[@id='tbody']/tr/td/input[@type='number']");

    //Отредактированть наименование первого товара
    public AdminPanal nameFirstInputCorrect(String name){
        SelenideElement element = nameInputs.first();
        element.click();
        element.clear();
        element.sendKeys(name);
        return this;
    }

    //Отредактированть цену первого товара
    public AdminPanal priceFirstInputCorrect(String price){
        SelenideElement element = priceInputs.first();
        element.click();
        element.clear();
        element.sendKeys(price);
        return this;
    }

    //Ссылка Вернуться на главную страницу
    SelenideElement returnMainPage = $x("//*[@href='/']");

    //Нажать на Вернуться на главную страницу
    public AdminPanal returnMainPageClick(){
        returnMainPage.click();
        return this;
    }



}
