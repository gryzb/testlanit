package ru.Test;

import io.qameta.allure.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;

import java.io.IOException;

import static base.TestSupport.*;


@Epic("Allure examples")
@Feature("svyaznoy.ru")
public class FirstTest {

    @BeforeClass
    public static void beforeSite() throws IOException { openWebDriver(); setUp(15000); }

    @Test
    @Story("Login")
    @DisplayName("Authorization")
    @Description("Authorization positive test")
    public void startPage() {
        OpenSite();
        maximazeWindow();
        Authorization();
    }

    @Test
    @Story("Checkout")
    @DisplayName("Phone select and buy")
    public void goShop() {
        shopSelect();
        addToBasket();
    }

    @Test
    @Story("Negative validation for order")
    @DisplayName("Order fields negative test")
    public void negativeAttributes() {
        OpenSite();
        shopSelect();
        addToBasketNotValid();
    }

    @AfterClass
    public static void close() {
         deleteBasket();
         closeWebDriver();
     }
}
