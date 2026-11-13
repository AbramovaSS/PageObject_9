package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MainPage {
    private final SelenideElement buttonAdminPanel = $("[href='/admin']");
    private final SelenideElement buttonOpenCart = $("#open-cart-btn");
    private final SelenideElement makeOrderButton= $("#makeOrder");
    private final SelenideElement notificationOrder = $(".toast");
    private final SelenideElement totalPrice = $("#total-price");

    private final ElementsCollection productCards = $$(".product-card");

    public MainPage clickButtonAdminPanel() {
        buttonAdminPanel.click();
        return this;
    }

    public MainPage clickButtonOpenCart() {
        buttonOpenCart.click();
        return this;
    }

    public MainPage clickMakeOrderBtn() {
        makeOrderButton.click();
        return this;
    }

    public SelenideElement getProductCardByText(String value) {
        return productCards.findBy(text(value));
    }

    public SelenideElement getNotificationOrder() {
        return notificationOrder;
    }

    public SelenideElement getTotalPrice() {
        return totalPrice;
    }

    public void addToCartThreeTimes(int id) {
        SelenideElement btn = $("#card-" + id + " [data-action='add-to-cart']");
        btn.click();
        btn.click();
        btn.click();
    }

    public void addToCart(int id) {
        SelenideElement btn = $("#card-" + id + " [data-action='add-to-cart']");
        btn.click();
    }
}
