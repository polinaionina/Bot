package io.ionina.bot.models;

/**
 * Текстовое содержимое сообщения.
 * @param text - текст, присланный пользователем
 */
public record TextContent(String text) implements MessageContent {
}
