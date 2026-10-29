package stellarburgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import stellarburgers.support.Config;

public class PasswordRecoveryPage extends BasePage {
    private final By title = By.xpath("//h2[text()='Восстановление пароля']");
    private final By loginLink = By.cssSelector("a[href='/login']");

    public PasswordRecoveryPage(WebDriver driver) {
        super(driver);
    }

    @Step("Открыть страницу восстановления пароля")
    public void open() {
        driver.get(Config.BASE_URL + "/forgot-password");
        visible(title);
    }

    @Step("Перейти ко входу со страницы восстановления пароля")
    public void openLogin() {
        visible(title);
        click(loginLink);
    }
}
