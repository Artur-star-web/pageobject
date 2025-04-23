package ru.netology.web.test;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldTransferMoneyBetweenOwnCards() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo(); // логин и пароль
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo); // всегда "12345"
        var dashboardPage = verificationPage.validVerify(verificationCode);


        var firstCardBalance = dashboardPage.getCardBalance(0);
        var secondCardBalance = dashboardPage.getCardBalance(1);


        int transferAmount = 10;


        var transferPage = dashboardPage.chooseCardToReplenish(0);


        dashboardPage = transferPage.makeTransfer(transferAmount, DataHelper.getCardNumber(1));


        var expectedFirstCardBalance = firstCardBalance + transferAmount;
        var expectedSecondCardBalance = secondCardBalance - transferAmount;

    
        assertEquals(expectedFirstCardBalance, dashboardPage.getCardBalance(0));
        assertEquals(expectedSecondCardBalance, dashboardPage.getCardBalance(1));
    }

    @Test
    void shouldNotAllowTransferMoreThanAvailable() {
        open("http://localhost:9999");

        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);

        var balanceFromCard = dashboardPage.getCardBalance(1);

        int transferAmount = balanceFromCard + 1;

        var transferPage = dashboardPage.chooseCardToReplenish(0);

        transferPage.makeTransfer(transferAmount, DataHelper.getCardNumber(1));
        transferPage.shouldShowError("Ошибка! Недостаточно средств на карте.");

    }

}
