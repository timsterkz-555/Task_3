package stellarburgers.tests;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import stellarburgers.client.UserClient;
import stellarburgers.model.UserData;
import stellarburgers.pages.AccountPage;
import stellarburgers.pages.LoginPage;
import stellarburgers.pages.MainPage;
import stellarburgers.support.BrowserFactory;
import stellarburgers.support.Config;

import java.util.UUID;

import static stellarburgers.support.ApiAssertions.assertAuthentication;

public abstract class BaseUiTest {
    protected WebDriver driver;
    protected UserData user;
    protected final UserClient userClient = new UserClient();

    @BeforeEach
    @Step("Открыть браузер для независимого теста")
    void startBrowser() {
        String browser = Config.browser();
        Allure.parameter("Браузер", browser);
        Allure.parameter("Java", System.getProperty("java.version"));
        Allure.label("parentSuite", browser);
        Allure.getLifecycle().updateTestCase(result ->
                result.setHistoryId(result.getFullName() + ":" + browser));
        driver = BrowserFactory.createDriver();
    }

    @Step("Подготовить уникального пользователя через API")
    protected void createUser() {
        user = UserData.unique();
        Response response = userClient.register(user);
        assertAuthentication(response, user);
    }

    @Step("Подготовить новые данные для регистрации с паролем длиной {passwordLength}")
    protected void prepareRegistrationData(int passwordLength) {
        user = UserData.unique();
        user.setPassword(UUID.randomUUID().toString().substring(0, passwordLength));
    }

    @Step("Войти через интерфейс подготовленным пользователем")
    protected void loginExistingUser() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.login(user);
        new MainPage(driver).checkAuthorized();
    }

    @Step("Подготовить пользователя и открыть его личный кабинет")
    protected void openProfile() {
        createUser();
        loginExistingUser();
        new MainPage(driver).openAccount();
        new AccountPage(driver).checkProfile(user);
    }

    @AfterEach
    @Step("Очистить тестовые данные и закрыть браузер")
    void cleanUp() {
        try {
            userClient.deleteIfCreated(user);
        } finally {
            closeBrowser();
        }
    }

    @Step("Закрыть браузер")
    private void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }
}
