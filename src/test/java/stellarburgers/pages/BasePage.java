package stellarburgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    private final By accountLink = By.cssSelector("header a[href='/account']");
    private final By constructorLink = By.xpath("//header//a[.//p[text()='Конструктор']]");
    private final By logoLink = By.cssSelector("header div[class*='header__logo'] a");

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    protected WebElement visible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void fill(By locator, String value) {
        WebElement input = visible(locator);
        input.clear();
        input.sendKeys(value);
    }

    @Step("Перейти по кнопке «Личный кабинет»")
    public void openAccount() {
        click(accountLink);
    }

    @Step("Перейти по кнопке «Конструктор»")
    public void openConstructor() {
        click(constructorLink);
    }

    @Step("Перейти по логотипу Stellar Burgers")
    public void clickLogo() {
        click(logoLink);
    }
}
