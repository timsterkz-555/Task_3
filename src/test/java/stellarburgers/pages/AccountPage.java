package stellarburgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import stellarburgers.model.UserData;
import stellarburgers.support.Config;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountPage extends BasePage {
    private final By profileLink = By.cssSelector("a[href='/account/profile']");
    private final By nameInput = By.cssSelector("input[name='Name']");
    private final By emailInput = By.cssSelector("input[name='name'][type='text']");
    private final By logoutButton = By.xpath("//button[text()='Выход']");

    public AccountPage(WebDriver driver) {
        super(driver);
    }

    @Step("Запросить защищённую страницу личного кабинета")
    public void open() {
        driver.get(Config.BASE_URL + "/account");
    }

    @Step("Проверить открытие профиля и данные подготовленного пользователя")
    public void checkProfile(UserData user) {
        wait.until(ExpectedConditions.urlToBe(Config.BASE_URL + "/account/profile"));
        wait.until(ExpectedConditions.textToBePresentInElementValue(emailInput, user.getEmail()));
        assertThat(visible(profileLink).getAttribute("class")).as("Активная страница профиля")
                .contains("Account_link_active");
        assertThat(visible(nameInput).getDomProperty("value")).as("Имя пользователя в профиле")
                .isEqualTo(user.getName());
        assertThat(visible(emailInput).getDomProperty("value")).as("Email пользователя в профиле")
                .isEqualTo(user.getEmail());
    }

    @Step("Выйти из аккаунта кнопкой «Выход»")
    public void logout() {
        click(logoutButton);
    }
}
