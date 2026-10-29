package stellarburgers.tests;

import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import stellarburgers.pages.MainPage;

@Feature("Разделы конструктора")
@DisplayName("Разделы ингредиентов конструктора")
class ConstructorSectionsTest extends BaseUiTest {
    @Test
    @DisplayName("Переход из соусов к булкам активирует вкладку и показывает булки")
    void testSwitchToBuns() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.selectSection("Соусы");
        mainPage.checkSection("Соусы", "Соус фирменный Space Sauce");
        mainPage.selectSection("Булки");
        mainPage.checkSection("Булки", "Краторная булка N-200i");
    }

    @Test
    @DisplayName("Переход к соусам активирует вкладку и показывает соусы")
    void testSwitchToSauces() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.selectSection("Соусы");
        mainPage.checkSection("Соусы", "Соус фирменный Space Sauce");
    }

    @Test
    @DisplayName("Переход к начинкам активирует вкладку и показывает начинки")
    void testSwitchToFillings() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.selectSection("Начинки");
        mainPage.checkSection("Начинки", "Мясо бессмертных моллюсков Protostomia");
    }
}
