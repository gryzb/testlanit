package base;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import java.io.IOException;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class TestSupport extends Throwable {

    public static void openWebDriver() throws IOException {
        System.setProperty("webdriver.chrome.driver", "C:/chromedriver_win32/chromedriver.exe");
        System.setProperty("selenide.browser", "Chrome");
        Configuration.startMaximized = true;
        setUp(6000);
    }

    //Развертывание на весь экран
    public static void maximazeWindow(){
        WebDriverRunner.getWebDriver().manage().window().maximize();
    }

    public static void setUp(int timeout) {
        Configuration.timeout = timeout;
    }

    public static void closeWebDriver() {
        clearBrowserCookies();
        clearBrowserLocalStorage();
        WebDriverRunner.getWebDriver().quit();
    }
    //Открываем сайт
    @Step("Open site")
    public static void OpenSite() {
        String url = "https://www.svyaznoy.ru";
        open(url);

        $(byXpath("/html/body/div[8]/div/div[2]/div[1]/a")).shouldBe(Condition.visible);
    }
    //Авторизация
    @Step("Authorization OK")
    public static void Authorization() {
        String login = "dekin1337@gmail.com";
        String password = "test1qaz@WSX";

        $(byClassName("b-header-account__inner")).shouldBe(Condition.visible).click();
        $(byXpath("/html/body/div[12]/div/div/div[1]/h1")).shouldHave(Condition.text("Войти на сайт"));
        $(byXpath("//*[@id=\"user\"]/div[1]/label/input")).sendKeys(login);
        $(byXpath("//*[@id=\"user\"]/div[2]/label/input")).sendKeys(password);
        $(byXpath("//*[@id=\"user\"]/div[4]/div/label/input")).click();
        $(byText("Личный кабинет")).shouldBe(Condition.visible);
    }

    //Выбор 12 элемента со страницы Телефоны - Iphone, можно поставить любой номер в get() до 23
    @Step("Go to Shop and select Phone")
    public static void shopSelect() {
        $(by("class","b-nav-catalog__link s-main-menu-catalog-btn")).click();
        $(by("data-banner-id","8660")).click();
        $(byText("Телефоны и аксессуары")).shouldBe(Condition.visible);
        $(by("alt","Apple iPhone")).click();
        $(byText("Смартфоны Apple iPhone")).shouldBe(Condition.visible);
//        $$(by("class","b-product-block__content")).shouldHaveSize(29);
        ElementsCollection spisok = $$(by("class","b-product-block__content"));
        spisok.get(11).click();
    }
    //Оформляем заказ
    @Step("Checkout phone")
    public static void addToBasket() {
        String mobilenumber = "9617964033";
        String name = "Андрей";
        String email = "dekin1337@gmail.com";
        $(byClassName("b-main-btn__text")).click();
        $(byText("Товар добавлен в корзину")).shouldBe(Condition.visible);
        $(by("class","svz-btn b-popup__btn _orange _fill")).click();
        $(byText("Оформление заказа")).shouldBe(Condition.visible);
        $(byText("Продолжить оформление")).click();
        $(by("placeholder","Имя *")).sendKeys(name);
        $(by("placeholder","Мобильный телефон *")).sendKeys(mobilenumber);
//        $(by("placeholder","Электронная почта *")).sendKeys(email);
        $(byClassName("checkbox-icon")).click();
        $(byText("Продолжить")).click();
        $(byClassName("checkout-element__content-before")).shouldBe(Condition.visible).click();
        $(by("class","b-checkout__tabs-item _pickup s-tab _active")).click();
        $(byText("Продолжить")).click();
        $(byText("подтвердить заказ")).click();
        $(byText("Покажите штрих-код продавцу при получении")).shouldBe(Condition.visible);

    }
    //Возврат на главную страницу
    public static void goToMainpage() {
        $(by("class","b-header__cell b-header-logo")).click();
    }

    //Удаляем добавленный товар(1 шт)
    public static void deleteBasket() {
        goToMainpage();
        $(byClassName("status-cart")).click();
        $(byText("Удалить")).click();
    }
    //Негативная проверка на поля ввода данных при оформлении заказа
    @Step("Not valid check for fields of order")
    public static void addToBasketNotValid() {
        String mobilenumber = "4564567898";
        String name = "123321";
        String email = "12345";
        $(byClassName("b-main-btn__text")).click();
//        $(byText("Товар добавлен в корзину")).shouldBe(Condition.visible);
        $(by("class","svz-btn b-popup__btn _orange _fill")).click();
        $(byText("Оформление заказа")).shouldBe(Condition.visible);
        $(byText("Продолжить оформление")).click();
        $(by("placeholder","Имя *")).sendKeys(Keys.CONTROL+"a");
        $(by("placeholder","Имя *")).sendKeys(name);
        $(byText("Введите буквы.")).shouldBe(Condition.visible);
        $(by("placeholder","Мобильный телефон *")).sendKeys(mobilenumber);
        $(byText("Телефон должен начинаться с 9.")).shouldBe(Condition.visible);
        $(by("placeholder","Электронная почта *")).clear();
        $(by("placeholder","Электронная почта *")).sendKeys(Keys.CONTROL+"a");
        $(by("placeholder","Электронная почта *")).sendKeys(email);
        $(byText("Пожалуйста, введите корректный адрес электронной почты.")).shouldBe(Condition.visible);
    }




}

