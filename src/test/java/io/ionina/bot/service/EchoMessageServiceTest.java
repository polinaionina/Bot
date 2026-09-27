package io.ionina.bot.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Модульные тесты для {@link EchoMessageService}.
 */
class EchoMessageServiceTest {

    /** Тестируемый экземпляр сервиса. */
    private final EchoMessageService echoMessageService = new EchoMessageService();

    /**
     * Проверяет, что сервис возвращает тот же текст, который был передан.
     */
    @Test
    void shouldReturnSameTextAsReceived() {
        String result = echoMessageService.buildEchoResponse("Привет");
        assertEquals("Привет", result);
    }

    /**
     * Проверяет корректную обработку пустой строки.
     */
    @Test
    void shouldReturnEmptyStringWhenInputIsEmpty() {
        assertEquals("", echoMessageService.buildEchoResponse(""));
    }

    /**
     * Проверяет, что при передаче {@code null} выбрасывается исключение.
     */
    @Test
    void shouldThrowExceptionWhenInputIsNull() {
        assertThrows(IllegalArgumentException.class, () -> echoMessageService.buildEchoResponse(null));
    }
}
