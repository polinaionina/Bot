package io.ionina.bot.config;

import java.util.Map;

/**
 * Загружает конфигурацию бота из набора переменных окружения.
 * Метод принимает {@link Map} с переменными окружения в качестве параметра, а не
 * обращается к {@code System.getenv()} напрямую. Это позволяет покрыть класс
 * модульными тестами, передавая в него произвольный набор значений.
 */
public class ConfigLoader {

    /**
     * Имя переменной окружения, содержащей токен бота.
     * Является примитивной константой
     */
    private static final String TOKEN_VARIABLE_NAME = "BOT_TOKEN";

    /**
     * Имя переменной окружения, содержащей username бота.
     * Является примитивной константой
     */
    private static final String USERNAME_VARIABLE_NAME = "BOT_USERNAME";

    /**
     * Загружает конфигурацию бота из переданного набора переменных окружения.
     *
     * @param environmentVariables отображение имён переменных окружения на их значения,
     *                             например результат вызова {@code System.getenv()}
     * @return конфигурация бота, собранная из значений {@code BOT_TOKEN} и {@code BOT_USERNAME}
     * @throws IllegalStateException если одна из необходимых переменных окружения не задана
     */
    public BotConfig loadConfig(Map<String, String> environmentVariables) {
        String token = environmentVariables.get(TOKEN_VARIABLE_NAME);
        String username = environmentVariables.get(USERNAME_VARIABLE_NAME);

        if (token == null || token.isBlank()) {
            throw new IllegalStateException("Переменная окружения " + TOKEN_VARIABLE_NAME + " не задана");
        }
        if (username == null || username.isBlank()) {
            throw new IllegalStateException("Переменная окружения " + USERNAME_VARIABLE_NAME + " не задана");
        }

        return new BotConfig(token, username);
    }
}
