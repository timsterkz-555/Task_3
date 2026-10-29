package stellarburgers.tests;

import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import stellarburgers.pages.AccountPage;
import stellarburgers.pages.LoginPage;

@Feature("Выход")
@DisplayName("Выход пользователя")
class LogoutTest extends BaseUiTest {
    @Test
    @DisplayName("Выход из личного кабинета открывает вход и прекращает доступ к профилю")
    void testLogoutFromAccount() {
        openProfile();
        AccountPage accountPage = new AccountPage(driver);
        accountPage.logout();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.checkOpened();

        accountPage.open();
        loginPage.checkOpened();
    }
}
