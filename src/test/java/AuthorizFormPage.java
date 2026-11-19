import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import org.openqa.selenium.devtools.latest.debugger.model.ScriptLanguage;

import static com.codeborne.selenide.Selenide.*;

//Домашнее задание №9
//Класс 2
// PageObject для формы авторизации в админке. Здесь должна быть полная функциональность
// (3 элемента: логин, пароль, кнопка «Войти»).
@Getter
public class AuthorizFormPage {
    //1.Поле ввода логина
    SelenideElement userNameInput = $x("//*[@id='username']");
    //2.Поле ввода пароля
    SelenideElement passwordInput = $x("//*[@id='password']");
    //3.Кнопка залогиниться
    SelenideElement signInButton = $x("//*[@type='submit']");

    public AuthorizFormPage setUserNameInput(String userName){
        userNameInput.click();
        userNameInput.clear();
        userNameInput.sendKeys(userName);
        return this;
    }
    public AuthorizFormPage setPasswordInput(String password){
        passwordInput.click();
        passwordInput.clear();
        passwordInput.sendKeys(password);
        return this;
    }

    public AuthorizFormPage clickSignInButton(){
        signInButton.click();
        return this;
    }
    public AuthorizFormAssert check(){
        return new AuthorizFormAssert(this);
    }

}
