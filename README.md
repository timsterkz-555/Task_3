# UI-тесты Stellar Burgers

Учебный проект на Java 11, Maven, JUnit 5, Selenium, RestAssured, AssertJ и Allure.
Сайт и API: https://qa-stellarburgers.education-services.ru.
Task_3 запускается самостоятельно: файлы из Task_2 при запуске не нужны.

## Что проверяется

В проекте ровно 13 сценариев:

| Класс | Сценарии | Количество |
| --- | --- | ---: |
| `RegistrationTest` | Регистрация с паролем из 6 символов, ошибка для 5 символов | 2 |
| `LoginTest` | Вход с главной, через личный кабинет, регистрацию и восстановление пароля | 4 |
| `AccountNavigationTest` | Данные профиля, возврат по кнопке «Конструктор» и по логотипу | 3 |
| `LogoutTest` | Выход и проверка отсутствия доступа к профилю | 1 |
| `ConstructorSectionsTest` | Булки, соусы и начинки: активная вкладка и содержимое | 3 |

## Где находится код

Весь Java-код находится в `src/test/java/stellarburgers`:

- `tests` — тесты и общий `BaseUiTest`: запуск браузера, подготовка пользователя и очистка.
- `pages` — отдельные Page Object для главной, входа, регистрации, восстановления пароля и личного кабинета; здесь находятся локаторы, действия и проверки интерфейса.
- `client` — API-запросы для создания, входа и удаления тестового пользователя.
- `model` — данные пользователя с уникальным синтетическим email.
- `support` — настройки сайта и браузера, общие проверки API.

`pom.xml` содержит зависимости и настройки запуска. `reports` предназначен для отчётов, которые можно включить в pull request. Временная папка `target` не добавляется в Git.

Каждый тест получает новый браузер. Для входа пользователь создаётся через API; успешная регистрация выполняется только через интерфейс. Очистка получает токен через API-вход, если пользователь зарегистрирован через UI, и удаляет пользователя даже после падения проверки. Браузер закрывается в `finally`, в том числе при ошибке очистки. Пароли и токены не выводятся в логи и Allure.

## Запуск на macOS

Откройте терминал в папке проекта и выберите Java 11:

```bash
cd /Users/kaspi/Desktop/Project/Task_3
export JAVA_HOME=$(/usr/libexec/java_home -v 11)
mvn -version
```

В выводе Maven должна быть Java 11. Проект останавливает сборку на другой версии JVM.

Для Chrome драйвер подбирает Selenium Manager. Google Chrome должен быть установлен в `/Applications/Google Chrome.app`.

Для Яндекс.Браузера используется его собственное приложение `/Applications/Yandex.app` и официальный [YandexDriver](https://github.com/yandex/YandexDriver/releases). Для установленной версии браузера подготовлен драйвер `v26.10.0-stable` по пути `~/.cache/selenium/yandexdriver/yandexdriver`. На другом компьютере скачайте подходящий драйвер для своей ОС и версии браузера; путь можно передать параметром `-Dyandex.driver=/путь/к/yandexdriver`.

Установка проверенного драйвера для текущего Яндекс.Браузера на macOS:

```bash
mkdir -p ~/.cache/selenium/yandexdriver
curl -fL https://github.com/yandex/YandexDriver/releases/download/v26.10.0-stable/yandexdriver-26.10.0.1855-mac.zip -o /tmp/yandexdriver.zip
unzip -o /tmp/yandexdriver.zip -d ~/.cache/selenium/yandexdriver
chmod +x ~/.cache/selenium/yandexdriver/yandexdriver
```

Запустите оба набора последовательно:

```bash
mvn clean test -Dbrowser=chrome
mvn test -Dbrowser=yandex
```

**Не добавляйте `clean` ко второму запуску:** он удалит результаты первого браузера. По умолчанию открывается обычное окно браузера. Для запуска без окна можно добавить `-Dheadless=true`.

Если приложения установлены в другом месте, передайте путь к исполняемому файлу: `-Dchrome.binary="/путь/к/Google Chrome"` или `-Dyandex.binary="/путь/к/Yandex"`.

## Allure-отчёты

Исходные результаты хранятся отдельно: `target/allure-results/chrome` и `target/allure-results/yandex`. В каждом результате указаны браузер и версия Java.

После тестов сформируйте отчёты:

```bash
mvn allure:report -Dbrowser=chrome
mvn allure:report -Dbrowser=yandex
open reports/allure-chrome/index.html
open reports/allure-yandex/index.html
```

Каждый `index.html` — самостоятельный файл: его можно скачать из pull request и открыть в браузере без отдельного сервера. Фактические результаты прогонов и ограничения описаны в [reports/README.md](reports/README.md).

Исходные результаты обоих завершённых прогонов сохранены также в `reports/allure-results.zip`. Для повторной генерации отчётов после клонирования проекта распакуйте архив в пустую папку результатов:

```bash
unzip reports/allure-results.zip -d target/allure-results
```

После этого выполните обе команды `mvn allure:report` выше. Вспомогательные файлы Maven Site исключены из Git: для просмотра достаточно `index.html`.

## Отправка на проверку

Проверьте, что активна ветка `develop3`, затем выполните:

```bash
git status --short --branch
git add .gitignore pom.xml README.md src reports
git commit -m "Add Stellar Burgers UI tests and Allure reports"
git push -u origin develop3
```

На GitHub создайте pull request из `develop3` в целевую ветку задания. В описании укажите результаты обоих браузеров и ссылки на `reports/allure-chrome/index.html`, `reports/allure-yandex/index.html` и `reports/README.md`. Папку `target` добавлять не нужно.
