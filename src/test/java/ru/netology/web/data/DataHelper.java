package ru.netology.web.data;

import lombok.Value;

public class DataHelper {
    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    public static String getCardNumber(int cardIndex) {
        if (cardIndex == 0) return "5559 0000 0000 0001";
        else return "5559 0000 0000 0002";
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    @Value
    public static class VerificationCode {
        String code;
    }
}
