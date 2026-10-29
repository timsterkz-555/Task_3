package stellarburgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import stellarburgers.model.UserData;
import stellarburgers.support.Config;

import static org.assertj.core.api.Assertions.assertThat;

public class RegistrationPage extends BasePage {
    private final By title = By.xpath("//h2[text()='Регистрация']");
    private final By nameInput = By.xpath("//label[text()='Имя']/following-sibling::input");
    private final By emailInput = By.xpath("//label[text()='Email']/following-sibling::input");
    private final By passwordInput = By.cssSelector("input[type='password']");
    private final By registerButton = By.xpath("//button[text()='Зарегистрироваться']");
    private final By loginLink = By.cssSelector("a[href='/login']");
    private final By passwordError = By.cssSelector("p.input__error");

    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    @Step("Открыть страницу регистрации")
    public void open() {
        driver.get(Config.BASE_URL + "/register");
        visible(title);
    }

    @Step("Зарегистрировать нового пользователя через интерфейс")
    public void register(UserData user) {
        fill(nameInput, user.getName());
        fill(emailInput, user.getEmail());
        fill(passwordInput, user.getPassword());
        click(registerButton);
    }

    @Step("Перейти ко входу со страницы регистрации")
    public void openLogin() {
        click(loginLink);
    }

    @Step("Проверить ошибку регистрации с коротким паролем")
    public void checkPasswordError() {
        assertThat(visible(passwordError).getText()).as("Сообщение о коротком пароле")
                .isEqualTo("Некорректный пароль");
        assertThat(driver.getCurrentUrl()).as("При ошибке остаёмся на странице регистрации")
                .isEqualTo(Config.BASE_URL + "/register");
    }
}
