package ru.netology;
import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class SelenideTest {


    private WebDriver driver;

    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        Configuration.headless = true;
        open("http://localhost:9999");
    }

    String generateDate(int daysToAdd, String pattern) {
        return LocalDate.now().plusDays(daysToAdd).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void shouldTestV1() {

        $("[data-test-id='city'] .input__control").setValue("Пермь");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] .input__control").setValue(generateDate(3, "dd.MM.yyyy"));
        $("[data-test-id='name'] [name='name']").setValue("Портников Сергей");
        $("[data-test-id='phone'] [name='phone']").setValue("+79870000000");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(withText("Успешно!")).shouldBe(visible, Duration.ofMillis(15000));
        $(withText("Встреча успешно забронирована на " + generateDate(3, "dd.MM.yyyy")));
    }
}
