package stellarburgers.tests;

import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import stellarburgers.pages.AccountPage;
import stellarburgers.pages.MainPage;

@Feature("Личный кабинет")
@DisplayName("Переходы личного кабинета")
class AccountNavigationTest extends BaseUiTest {
    @Test
    @DisplayName("Переход по кнопке «Личный кабинет» открывает профиль с данными пользователя")
    void testOpenAccountProfile() {
        createUser();
        loginExistingUser();
        new MainPage(driver).openAccount();
        new AccountPage(driver).checkProfile(user);
    }

    @Test
    @DisplayName("Кнопка «Конструктор» возвращает из личного кабинета к сборке бургера")
    void testReturnToConstructorByButton() {
        openProfile();
        new AccountPage(driver).openConstructor();
        new MainPage(driver).checkOpened();
    }

    @Test
    @DisplayName("Логотип Stellar Burgers возвращает из личного кабинета к сборке бургера")
    void testReturnToConstructorByLogo() {
        openProfile();
        new AccountPage(driver).clickLogo();
        new MainPage(driver).checkOpened();
    }
}
