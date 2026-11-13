package pages;

import org.assertj.core.api.AbstractAssert;

import static com.codeborne.selenide.Condition.*;

public class AdminPageAssert extends AbstractAssert<AdminPageAssert, AdminPage> {

    protected AdminPageAssert(AdminPage actual) {

        super(actual, AdminPageAssert.class);
    }

    public static AdminPageAssert assertThat(AdminPage actual) {

        return new AdminPageAssert(actual);
    }

    public void hasNotificationWithText(String expectedText) {
        actual.getNotification()
                .shouldBe(visible)
                .shouldHave(text(expectedText));

    }
}
