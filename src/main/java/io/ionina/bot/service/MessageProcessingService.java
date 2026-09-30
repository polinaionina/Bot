package io.ionina.bot.service;

import io.ionina.bot.models.MessageContent;

/**
 * Контракт сервиса, который обрабатывает входящее сообщение пользователя
 * и формирует ответ.
 * @param incomingContent содержимое сообщения (текст, стикер или гифка)
 * Вынесла в интерфейс, чтобы позже можно было подменить логику, не трогая
 * код общения с Telegram.
 */
public interface MessageProcessingService {
    MessageContent buildEchoResponse(MessageContent incomingContent);
}