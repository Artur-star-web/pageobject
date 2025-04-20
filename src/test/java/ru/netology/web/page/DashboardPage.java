package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private SelenideElement heading = $("h1");
    private ElementsCollection cards = $$("li.list__item");

    public DashboardPage() {
        heading.shouldHave(Condition.text("Ваши карты"));
    }

    public int getCardBalance(int index) {
        String text = cards.get(index).getText();
        return extractBalance(text);
    }

    public TransferPage chooseCardToReplenish(int index) {
        cards.get(index).$("[data-test-id=action-deposit]").click();
        return new TransferPage();
    }

    private int extractBalance(String text) {
        return Integer.parseInt(text.split("баланс: ")[1].split(" ")[0]);
    }
}
