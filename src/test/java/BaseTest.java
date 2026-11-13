import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SimpleReport;
import config.ConfigReader;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class BaseTest {

    private static final Logger log = LoggerFactory.getLogger(BaseTest.class);
    protected RequestSpecification requestSpec;

    protected List<Integer> createdProductIds = new ArrayList<>();

    private final SimpleReport report = new SimpleReport();

    @BeforeAll
    static void configureSelenide() {
        log.info("Браузер: {}", ConfigReader.CONFIG.browserName());
        log.info("Размер окна: {}", ConfigReader.CONFIG.browserSize());
        log.info("Базовый URL стенда: {}", ConfigReader.CONFIG.baseUrl());
        log.info("Таймаут: {}", ConfigReader.CONFIG.timeout());
        log.info("Подробное логирование: {}", ConfigReader.CONFIG.enableDetailedLogging());

        Configuration.baseUrl = ConfigReader.CONFIG.baseUrl();
        Configuration.browser = ConfigReader.CONFIG.browserName();
        Configuration.browserSize = ConfigReader.CONFIG.browserSize();
        Configuration.timeout = ConfigReader.CONFIG.timeout();

    }

    @BeforeEach
    void setup() {

        if (ConfigReader.CONFIG.enableDetailedLogging()) {
            report.start();
        }

        this.requestSpec = RestApiBuilder.getRequestSpec();

        Selenide.open("/");
    }


    @AfterEach
    void tearDown() {
        try {
            for (Integer id : createdProductIds) {
                given()
                        .spec(requestSpec)
                        .pathParam("id", id)
                        .delete("/goods/{id}")
                        .then()
                        .log().all()
                        .statusCode(200);
            }

            if (ConfigReader.CONFIG.enableDetailedLogging()) {
                report.finish("Пошаговый отчет");
            }
        } finally {
            createdProductIds.clear();
            Selenide.closeWebDriver();
        }
    }
}
