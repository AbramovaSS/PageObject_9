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

    public MainPageAssert hasProductCardWithName(String expectedText) {
        actual.getProductCardByText(expectedText)
                .shouldBe(visible);

        return this;
    }

    public MainPageAssert hasNotificationOrderWithText(String expectedText) {
        actual.getNotificationOrder()
                .shouldBe(visible)
                .shouldHave(text(expectedText));

        return this;
    }

    public MainPageAssert verifyTotalPrice(double expectedTotalPrice) {
        actual.getTotalPrice()
                .shouldHave(exactText(String.valueOf(expectedTotalPrice)));
        return this;
    }

}
