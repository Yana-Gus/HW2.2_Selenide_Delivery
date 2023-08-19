package ru.netology.service;


import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;


import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryOrderTest {
    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void shouldAllFieldsFilledInCorrectly() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Казань");
        String bookedDate = generateDate(5, "dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        $("[data-test-id=date] input").sendKeys(bookedDate);
        $("[data-test-id=name] input").setValue("Сидорова Анна-Мария");
        $("[data-test-id=phone] input").setValue("+71234567890");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $(".notification__content").shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + bookedDate));
    }

    @Test
    public void shouldEnteredTwoCharactersInTheCityField() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Ка");
        $$(".menu-item__control").find(exactText("Казань")).click();
        String bookedDate = generateDate(5, "dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        $("[data-test-id=date] input").sendKeys(bookedDate);
        $("[data-test-id=name] input").setValue("Сидорова Анна-Мария");
        $("[data-test-id=phone] input").setValue("+71234567890");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $(".notification__content").shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Встреча успешно забронирована на " + bookedDate));
    }

    @Test
    public void shouldEntered() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Ка");
        $$(".menu-item__control").findBy(text("Казань")).click();
        String bookedDate = generateDate(7, "dd.MM.yyyy");
        //$("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        $("[data-test-id=date] input").click();
        $$(".calendar__day").findBy(text("26")).click();
        $("[data-test-id=name] input").setValue("Сидорова Анна-Мария");
        $("[data-test-id=phone] input").setValue("+71234567890");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $(".notification__content").shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Встреча успешно забронирована на " + bookedDate));
    }
}
