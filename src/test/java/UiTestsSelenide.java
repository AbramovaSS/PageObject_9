import com.codeborne.selenide.*;
import com.github.javafaker.Faker;
import config.ConfigReader;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.AdminLoginPage;
import pages.AdminPage;
import pages.MainPage;
import pages.MainPageAssert;

import java.util.List;

import static com.codeborne.selenide.Selenide.*;

import static io.restassured.RestAssured.given;
import static pages.AdminPageAssert.assertThat;
import static pages.MainPageAssert.assertThat;


public class UiTestsSelenide extends BaseTest {
    Faker faker = new Faker();

    String product = faker.commerce().productName();
    Double price = faker.number().randomDouble(2, 1, 100);
    String login = ConfigReader.CONFIG.userLogin();
    String password = ConfigReader.CONFIG.userPassword();
    String product1 = "Очки";
    String product2 = "Шляпа";
    String product3 = "Покрывало";
    Double price1 = 50.50;
    Double price2 = 60.10;
    Double price3 = 70.10;

    ProductRequest productRequest = new ProductRequest(product, price);
    MainPage mainPage = new MainPage();
    AdminLoginPage adminLoginPage = new AdminLoginPage();
    AdminPage adminPage = new AdminPage();


    @DisplayName("Покупка 3х единиц товара")
    @Test
    void shouldPurchaseThreeUnitsOfProduct() {
        Integer actualProductId = given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body(productRequest)
                .post("/goods/add")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .path("data.id");

        createdProductIds.add(actualProductId);

        Selenide.refresh();

        mainPage.addToCartThreeTimes(actualProductId);

        sleep(2000);

        mainPage.clickButtonOpenCart()
                .clickMakeOrderBtn();

        assertThat(mainPage)
                .hasNotificationOrderWithText("Заказ принят в обработку!");

    }

    @DisplayName("Проверка расчета общей цены")
    @Test
    void totalPriceCalculationTest() {
        Integer id1 = given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body(new ProductRequest(product1, price1))
                .post("/goods/add")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .path("data.id");

        Integer id2 = given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body(new ProductRequest(product2, price2))
                .post("/goods/add")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .path("data.id");

        Integer id3 = given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body(new ProductRequest(product3, price3))
                .post("/goods/add")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .path("data.id");

        createdProductIds.addAll(List.of(id1, id2, id3));

        Selenide.refresh();

        mainPage.addToCart(id1);
        mainPage.addToCart(id2);
        mainPage.addToCart(id3);

        double expectedTotalPrice = price1 + price2 + price3;

        mainPage.clickButtonOpenCart();

        assertThat(mainPage)
                .verifyTotalPrice(expectedTotalPrice);


    }

    @DisplayName("Проверка уведомления после добавления товара")
    @Test
    void productAddedNotificationTest() {

        mainPage.clickButtonAdminPanel();
        adminLoginPage.setLogin(login)
                .setPassword(password);
        adminPage.setNameField(product)
                .setPriceField(String.valueOf(price))
                .clickSaveButton();

        assertThat(adminPage)
                .hasNotificationWithText("Товар успешно добавлен!");

    }

    @DisplayName("Редактирование товара")
    @Test
    void shouldSuccessfullyEditProduct() {
        Integer actualProductId = given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body(productRequest)
                .post("/goods/add")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .path("data.id");

        createdProductIds.add(actualProductId);

        Selenide.refresh();

        mainPage.addToCartThreeTimes(actualProductId);

        sleep(2000);

        mainPage.clickButtonAdminPanel();
        adminLoginPage.setLogin(login)
                .setPassword(password);
        adminPage.setInputs(product)
                .clickUpdateButton()
                .clickExitAdminPanelBtn();

        MainPageAssert.assertThat(mainPage)
                .hasProductCardWithName(product);
    }
}
