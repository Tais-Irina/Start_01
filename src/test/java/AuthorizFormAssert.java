import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Condition.*;
import org.assertj.core.api.AbstractAssert;

import static com.codeborne.selenide.Condition.*;


public class AuthorizFormAssert extends AbstractAssert<AuthorizFormAssert, AuthorizFormPage> {
    public AuthorizFormAssert(AuthorizFormPage actual){
        super(actual, AuthorizFormAssert.class);
    }
    //1.Поле ввода логина
    //Проверить что поле ввода логина содержит Username
    public AuthorizFormAssert userNameInputContainsText(String expectedText){
        actual.userNameInput.shouldHave(attribute("placeholder",expectedText));
        return this;
    }
    //2.Поле ввода пароля
    //Проверить, что поле ввода пароля содержит Password
    public AuthorizFormAssert passwordInputContainsText(String expectedText){
        actual.passwordInput.shouldHave(attribute("placeholder",expectedText));
        return this;
    }
    //3.Кнопка залогиниться
    //Провериить, что кнопка залогиниться видна на странице
    public AuthorizFormAssert signInButtonIsVisible()
    {
        actual.signInButton.should(visible);
        return this;
    }

}
