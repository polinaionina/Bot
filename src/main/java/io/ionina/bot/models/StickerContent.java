package io.ionina.bot.models;

/**
 * Содержимое сообщения в виде стикера.
 * @param fileId - идентификатор файла стикера в Telegram
 */
public record StickerContent(String fileId) implements MessageContent {
}
