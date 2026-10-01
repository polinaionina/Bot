package io.ionina.bot.service;

import org.junit.jupiter.api.Test;

import io.ionina.bot.models.MessageContent;
import io.ionina.bot.models.TextContent;

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
        MessageContent result = echoMessageService.buildResponse(new TextContent("Привет"));
        assertEquals(new TextContent("Привет"), result);
    }

    /**
     * Проверяет корректную обработку пустой строки.
     */
    @Test
    void shouldReturnEmptyStringWhenInputIsEmpty() {
        assertEquals(new TextContent(""), echoMessageService.buildResponse(new TextContent("")));
    }

    /**
     * Проверяет, что при передаче {@code null} выбрасывается исключение.
     */
    @Test
    void shouldThrowExceptionWhenInputIsNull() {
        assertThrows(IllegalArgumentException.class, () -> echoMessageService.buildResponse(null));
    }
}
