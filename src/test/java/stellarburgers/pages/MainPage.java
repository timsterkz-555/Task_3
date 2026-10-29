package stellarburgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import stellarburgers.support.Config;

import static org.assertj.core.api.Assertions.assertThat;

public class MainPage extends BasePage {
    private final By title = By.xpath("//h1[text()='Соберите бургер']");
    private final By loginButton = By.xpath("//button[text()='Войти в аккаунт']");
    private final By orderButton = By.xpath("//button[text()='Оформить заказ']");
    private final By ingredientsMenu = By.cssSelector("div[class*='ingredients__menuContainer']");

    public MainPage(WebDriver driver) {
        super(driver);
    }

    @Step("Открыть главную страницу")
    public void open() {
        driver.get(Config.BASE_URL + "/");
        visible(title);
    }

    @Step("Перейти по кнопке «Войти в аккаунт»")
    public void openLogin() {
        click(loginButton);
    }

    @Step("Проверить открытие конструктора")
    public void checkOpened() {
        wait.until(ExpectedConditions.urlToBe(Config.BASE_URL + "/"));
        assertThat(visible(title).getText()).as("Заголовок конструктора").isEqualTo("Соберите бургер");
        assertThat(visible(ingredientsMenu).isDisplayed()).as("Список ингредиентов открыт").isTrue();
    }

    @Step("Проверить авторизованное состояние пользователя")
    public void checkAuthorized() {
        checkOpened();
        assertThat(visible(orderButton).isDisplayed()).as("Авторизованному пользователю доступно оформление заказа").isTrue();
        assertThat(driver.findElements(loginButton)).as("Кнопка входа отсутствует после авторизации").isEmpty();
    }

    @Step("Выбрать раздел «{section}»")
    public void selectSection(String section) {
        click(sectionTab(section));
    }

    @Step("Проверить активную вкладку «{section}» и содержимое раздела")
    public void checkSection(String section, String expectedIngredient) {
        By tab = sectionTab(section);
        By heading = By.xpath("//h2[text()='" + section + "']");
        By ingredient = By.xpath("//h2[text()='" + section
                + "']/following-sibling::ul[1]//p[text()='" + expectedIngredient + "']");

        wait.until(ExpectedConditions.attributeContains(tab, "class", "tab_tab_type_current"));
        wait.withMessage("Заголовок и ингредиент раздела «" + section + "» должны быть в видимой области списка")
                .until(webDriver -> isInsideMenu(heading) && isInsideMenu(ingredient));

        assertThat(visible(tab).getAttribute("class")).as("Активная вкладка раздела")
                .contains("tab_tab_type_current");
        assertThat(visible(heading).getText()).as("Заголовок выбранного раздела").isEqualTo(section);
        assertThat(visible(ingredient).getText()).as("Ингредиент выбранного раздела").isEqualTo(expectedIngredient);
    }

    private By sectionTab(String section) {
        return By.xpath("//div[contains(@class,'tab_tab__')][span[text()='" + section + "']]");
    }

    private boolean isInsideMenu(By locator) {
        // isDisplayed не гарантирует, что элемент находится внутри прокрученной области списка.
        Rectangle menu = driver.findElement(ingredientsMenu).getRect();
        Rectangle element = driver.findElement(locator).getRect();
        return element.getY() >= menu.getY()
                && element.getY() + element.getHeight() <= menu.getY() + menu.getHeight();
    }
}
