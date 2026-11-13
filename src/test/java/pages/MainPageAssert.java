package pages;

import org.assertj.core.api.AbstractAssert;

import static com.codeborne.selenide.Condition.*;

public class MainPageAssert extends AbstractAssert<MainPageAssert, MainPage> {

    protected MainPageAssert(MainPage actual) {
        super(actual, MainPageAssert.class);
    }

    public static MainPageAssert assertThat(MainPage actual) {
        return new MainPageAssert(actual);
    }

    public void hasProductCardWithName(String expectedText) {
        actual.getProductCardByText(expectedText)
                .shouldBe(visible);

    }

    public void hasNotificationOrderWithText(String expectedText) {
        actual.getNotificationOrder()
                .shouldBe(visible)
                .shouldHave(text(expectedText));

    }

    public void verifyTotalPrice(double expectedTotalPrice) {
        actual.getTotalPrice()
                .shouldHave(exactText(String.valueOf(expectedTotalPrice)));
    }

}
