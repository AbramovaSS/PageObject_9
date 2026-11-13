package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.SetValueOptions.withText;

public class AdminLoginPage {
    private final SelenideElement loginField = $("#username");
    private final SelenideElement pswField = $("#password");

    public AdminLoginPage setLogin(String value) {
        loginField.shouldBe(visible).setValue(value);
        return this;
    }

    public AdminLoginPage setPassword(String value) {
        pswField.setValue(withText(value).sensitive()).pressEnter();
        return this;
    }
}
