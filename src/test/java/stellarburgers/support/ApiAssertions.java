package stellarburgers.support;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.experimental.UtilityClass;
import org.apache.http.HttpStatus;
import stellarburgers.model.UserData;

import static org.assertj.core.api.Assertions.assertThat;

@UtilityClass
public class ApiAssertions {
    @Step("Проверить HTTP-статус и признак успешного ответа")
    public static void assertSuccess(Response response, int expectedStatus) {
        assertThat(response.statusCode()).as("HTTP-статус ответа").isEqualTo(expectedStatus);
        assertThat(response.jsonPath().getBoolean("success")).as("Признак успешного ответа").isTrue();
    }

    @Step("Проверить данные пользователя и наличие токена для очистки")
    public static void assertAuthentication(Response response, UserData expectedUser) {
        assertSuccess(response, HttpStatus.SC_OK);
        assertThat(response.jsonPath().getString("user.email"))
                .as("Email подготовленного пользователя").isEqualTo(expectedUser.getEmail());
        assertThat(response.jsonPath().getString("user.name"))
                .as("Имя подготовленного пользователя").isEqualTo(expectedUser.getName());
        String accessToken = response.jsonPath().getString("accessToken");
        // Проверяем условие, чтобы токен не попал в сообщение AssertJ при ошибке.
        assertThat(accessToken != null && accessToken.startsWith("Bearer ") && accessToken.length() > 7)
                .as("Ответ содержит непустой accessToken с префиксом Bearer").isTrue();
    }
}
