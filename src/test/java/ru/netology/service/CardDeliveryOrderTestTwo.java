package ru.netology.service;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryOrderTestTwo {

    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }


    @Test
    void shouldBeAppointmentBookedNextMonth() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Ка");
        $$(".menu-item__control").findBy(text("Казань")).click();
        String bookedDate = generateDate(17, "dd.MM.yyyy");
        $("[class=input__icon]").click();
        if (generateDate(0, "MM").equals(generateDate(17, "MM"))) {
        } else {
            $("[data-step='1'][data-disabled=false]").click();
        }
        String day = generateDate(17, "d");
        $(byText(day)).click();
        $("[data-test-id=name] input").setValue("Сидорова Анна-Мария");
        $("[data-test-id=phone] input").setValue("+71234567890");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $(".notification__content").shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + bookedDate));
    }
}
