package stellarburgers.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import stellarburgers.model.UserData;

import static stellarburgers.support.ApiAssertions.assertAuthentication;
import static stellarburgers.support.ApiAssertions.assertSuccess;

public class UserClient {
    private String accessToken;

    @Step("Зарегистрировать пользователя через API")
    public Response register(UserData user) {
        Response response = ApiRequest.request().body(user).post("/api/auth/register");
        saveAccessToken(response);
        return response;
    }

    @Step("Получить токен пользователя через API-вход")
    public Response login(UserData user) {
        UserData credentials = new UserData(user.getEmail(), user.getPassword(), null);
        Response response = ApiRequest.request().body(credentials).post("/api/auth/login");
        saveAccessToken(response);
        return response;
    }

    @Step("Удалить созданного пользователя через API")
    public Response delete() {
        return ApiRequest.authorizedRequest(accessToken).delete("/api/auth/user");
    }

    public boolean hasCreatedUser() {
        return accessToken != null;
    }

    @Step("Очистить пользователя, в том числе после неудачной проверки")
    public void deleteIfCreated(UserData user) {
        try {
            if (!hasCreatedUser() && user != null) {
                // UI-регистрация могла завершиться до падения следующей проверки.
                Response response = login(user);
                if (response.statusCode() == HttpStatus.SC_UNAUTHORIZED) {
                    return;
                }
                assertAuthentication(response, user);
            }
        } finally {
            if (hasCreatedUser()) {
                assertSuccess(delete(), HttpStatus.SC_ACCEPTED);
                accessToken = null;
            }
        }
    }

    private void saveAccessToken(Response response) {
        // Сохраняем токен до проверок, чтобы очистка работала и при их падении.
        if (response.contentType().contains("json")) {
            String token = response.jsonPath().getString("accessToken");
            if (token != null && !token.isBlank()) {
                accessToken = token;
            }
        }
    }
}
