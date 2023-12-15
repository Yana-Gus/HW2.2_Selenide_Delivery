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
    void shouldBeNotCityOnTheList() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Троицк");
        String bookedDate = generateDate(5, "dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        $("[data-test-id=date] input").setValue(bookedDate);
        $("[data-test-id=name] input").setValue("Сидорова Анна-Мария");
        $("[data-test-id=phone] input").setValue("+71234567890");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $(byText("Доставка в выбранный город недоступна")).shouldBe(visible);
    }

    @Test
    void shouldBeNotEmptyCityFields() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("");
        String bookedDate = generateDate(5, "dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        $("[data-test-id=date] input").setValue(bookedDate);
        $("[data-test-id=name] input").setValue("Сидорова Анна-Мария");
        $("[data-test-id=phone] input").setValue("+71234567890");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $(byText("Поле обязательно для заполнения")).shouldBe(visible);
    }

    @Test
    void shouldBeNameFieldEnteredIncorrectly() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Казань");
        String bookedDate = generateDate(5, "dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        $("[data-test-id=date] input").setValue(bookedDate);
        $("[data-test-id=name] input").setValue("Sidorova Anna-Mariya");
        $("[data-test-id=phone] input").setValue("+71234567890");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $(byText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы"))
                .shouldBe(visible);
    }

    @Test
    void shouldBePhoneFieldEnteredIncorrectly() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Казань");
        String bookedDate = generateDate(5, "dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        $("[data-test-id=date] input").setValue(bookedDate);
        $("[data-test-id=name] input").setValue("Сидорова Анна-Мария");
        $("[data-test-id=phone] input").setValue("8796");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $(byText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."))
                .shouldBe(visible);
    }
}
