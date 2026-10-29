package stellarburgers.support;

import lombok.experimental.UtilityClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;

@UtilityClass
public class BrowserFactory {
    public static WebDriver createDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1440,1100");
        if (Boolean.getBoolean("headless")) {
            options.addArguments("--headless=new");
        }

        switch (Config.browser()) {
            case "chrome":
                options.setBinary(System.getProperty("chrome.binary",
                        "/Applications/Google Chrome.app/Contents/MacOS/Google Chrome"));
                return new ChromeDriver(options);
            case "yandex":
                options.setBinary(System.getProperty("yandex.binary",
                        "/Applications/Yandex.app/Contents/MacOS/Yandex"));
                File driverFile = new File(System.getProperty("yandex.driver",
                        System.getProperty("user.home") + "/.cache/selenium/yandexdriver/yandexdriver"));
                if (!driverFile.canExecute()) {
                    throw new IllegalStateException("Установите YandexDriver и укажите -Dyandex.driver=/путь/к/yandexdriver");
                }
                ChromeDriverService service = new ChromeDriverService.Builder()
                        .usingDriverExecutable(driverFile)
                        .build();
                return new ChromeDriver(service, options);
            default:
                throw new IllegalArgumentException("Неизвестный браузер: " + Config.browser()
                        + ". Используйте chrome или yandex.");
        }
    }
}
