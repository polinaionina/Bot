package io.ionina.bot.models;

/**
 * Содержимое сообщения в виде фотографии.
 * @param fileId - идентификатор файла наибольшего доступного размера гифки в Telegram
 */
public record GIFContent(String fileId) implements MessageContent {
}
