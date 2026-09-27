package io.ionina.bot.service;

/**
 * Контракт сервиса, который обрабатывает текст входящего сообщения пользователя
 * и формирует текст ответа.
 * Вынесла в интерфейс, чтобы позже можно было подменить логику, не трогая
 * код общения с Telegram.
 */
public interface MessageProcessingService {
    String buildEchoResponse(String incomingText);
}