package stellarburgers.support;

import lombok.experimental.UtilityClass;

import java.util.Locale;

@UtilityClass
public class Config {
    public static final String BASE_URL = "https://qa-stellarburgers.education-services.ru";

    public static String browser() {
        return System.getProperty("browser", "chrome").toLowerCase(Locale.ROOT);
    }
}
