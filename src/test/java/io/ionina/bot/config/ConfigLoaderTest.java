package io.ionina.bot.config;

import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Модульные тесты для {@link ConfigLoader}.
 */
class ConfigLoaderTest {

    /** Тестируемый экземпляр загрузчика конфигурации. */
    private final ConfigLoader configLoader = new ConfigLoader();

    /**
     * Проверяет успешную загрузку конфигурации если обе переменные заданы.
     */
    @Test
    void shouldLoadConfigWhenBothVariablesPresent() {
        Map<String, String> environment = Map.of(
                "BOT_TOKEN", "test-token",
                "BOT_USERNAME", "test-bot"
        );

        BotConfig botConfig = configLoader.loadConfig(environment);

        assertEquals("test-token", botConfig.getBotToken());
        assertEquals("test-bot", botConfig.getBotUsername());
    }

    /**
     * Проверяет, что при отсутствии токена выбрасывается исключение.
     */
    @Test
    void shouldThrowWhenTokenMissing() {
        Map<String, String> environment = Map.of("BOT_USERNAME", "test-bot");

        assertThrows(IllegalStateException.class, () -> configLoader.loadConfig(environment));
    }

    /**
     * Проверяет, что при отсутствии username выбрасывается исключение.
     */
    @Test
    void shouldThrowWhenUsernameMissing() {
        Map<String, String> environment = Map.of("BOT_TOKEN", "test-token");

        assertThrows(IllegalStateException.class, () -> configLoader.loadConfig(environment));
    }
}
