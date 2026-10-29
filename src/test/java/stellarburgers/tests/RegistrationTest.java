package stellarburgers.tests;

import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import stellarburgers.pages.LoginPage;
import stellarburgers.pages.MainPage;
import stellarburgers.pages.RegistrationPage;

@Feature("Регистрация")
@DisplayName("Регистрация пользователя")
class RegistrationTest extends BaseUiTest {
    @Test
    @DisplayName("Регистрация с паролем из шести символов позволяет войти в аккаунт")
    void testRegisterWithSixCharacterPassword() {
        prepareRegistrationData(6);
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.open();
        registrationPage.register(user);

        LoginPage loginPage = new LoginPage(driver);
        loginPage.checkOpened();
        loginPage.login(user);
        new MainPage(driver).checkAuthorized();
    }

    @Test
    @DisplayName("Регистрация с паролем из пяти символов показывает ошибку")
    void testRegisterWithFiveCharacterPassword() {
        prepareRegistrationData(5);
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.open();
        registrationPage.register(user);
        registrationPage.checkPasswordError();
    }
}
