package stellarburgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import stellarburgers.model.UserData;
import stellarburgers.support.Config;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginPage extends BasePage {
    private final By title = By.xpath("//h2[text()='Вход']");
    private final By emailInput = By.xpath("//label[text()='Email']/following-sibling::input");
    private final By passwordInput = By.cssSelector("input[type='password']");
    private final By loginButton = By.xpath("//button[text()='Войти']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Открыть страницу входа")
    public void open() {
        driver.get(Config.BASE_URL + "/login");
        visible(title);
    }

    @Step("Войти через интерфейс с данными подготовленного пользователя")
    public void login(UserData user) {
        visible(title);
        fill(emailInput, user.getEmail());
        fill(passwordInput, user.getPassword());
        click(loginButton);
    }

    @Step("Проверить открытие страницы входа")
    public void checkOpened() {
        wait.until(ExpectedConditions.urlToBe(Config.BASE_URL + "/login"));
        assertThat(visible(title).getText()).as("Заголовок страницы входа").isEqualTo("Вход");
        assertThat(visible(loginButton).isDisplayed()).as("Кнопка входа отображается").isTrue();
    }
}
