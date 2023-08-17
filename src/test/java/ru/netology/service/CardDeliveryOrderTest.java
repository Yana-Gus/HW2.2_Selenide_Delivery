package ru.netology.service;


import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;


import java.time.Duration;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryOrderTest {

    @Test
    public void shouldAllFieldsFilledInCorrectly() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Казань");
        $("[data-test-id=date] input").doubleClick();
        $("[data-test-id=date] input").sendKeys("21.08.2023");
        $("[data-test-id=name] input").setValue("Сидорова Мария");
        $("[data-test-id=phone] input").setValue("+71234567890");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));

    }
}
