package stellarburgers.tests;

import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import stellarburgers.pages.LoginPage;
import stellarburgers.pages.MainPage;
import stellarburgers.pages.PasswordRecoveryPage;
import stellarburgers.pages.RegistrationPage;

@Feature("Вход")
@DisplayName("Вход пользователя")
class LoginTest extends BaseUiTest {
    @BeforeEach
    void prepareUser() {
        createUser();
    }

    @Test
    @DisplayName("Вход по кнопке «Войти в аккаунт» на главной странице авторизует пользователя")
    void testLoginFromMainPage() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.openLogin();
        new LoginPage(driver).login(user);
        mainPage.checkAuthorized();
    }

    @Test
    @DisplayName("Вход по кнопке «Личный кабинет» авторизует пользователя")
    void testLoginFromAccountButton() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.openAccount();
        new LoginPage(driver).login(user);
        mainPage.checkAuthorized();
    }

    @Test
    @DisplayName("Вход по ссылке на странице регистрации авторизует пользователя")
    void testLoginFromRegistrationPage() {
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.open();
        registrationPage.openLogin();
        new LoginPage(driver).login(user);
        new MainPage(driver).checkAuthorized();
    }

    @Test
    @DisplayName("Вход по ссылке на странице восстановления пароля авторизует пользователя")
    void testLoginFromPasswordRecoveryPage() {
        PasswordRecoveryPage passwordRecoveryPage = new PasswordRecoveryPage(driver);
        passwordRecoveryPage.open();
        passwordRecoveryPage.openLogin();
        new LoginPage(driver).login(user);
        new MainPage(driver).checkAuthorized();
    }
}
